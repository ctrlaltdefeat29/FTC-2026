package org.firstinspires.ftc.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name = "Limelight Auto Align")
@Disabled
public class AprilTagAutoAlignmentMecanumDriveFixed extends OpMode {

    private final MecanumDrive drive = new MecanumDrive();
    private Limelight3A limelight;

    // ---- Settings you may need to change ----
    private static final int TARGET_TAG_ID = 20;      // AprilTag on the goal
    private static final int APRILTAG_PIPELINE = 0;   // pipeline number in the Limelight web page
    private static final String LIMELIGHT_NAME = "limelight"; // must match Robot Configuration

    // ---- PD controller (tx is in degrees) ----
    double kP = 0.015;
    double kD = 0.0005;
    double goalX = 0;            // 0 = tag centered. Change to offset your shot.
    double angleTolerance = 0.5; // degrees
    double error = 0;
    double lastError = 0;
    double lastTime = 0;

    double forward, strafe, rotate;

    // ---- Tuning with the gamepad ----
    double[] stepSizes = {0.01, 0.001, 0.0001};
    int stepIndex = 1;

    @Override
    public void init() {
        drive.init(hardwareMap, telemetry, false);

        limelight = hardwareMap.get(Limelight3A.class, LIMELIGHT_NAME);
        limelight.pipelineSwitch(APRILTAG_PIPELINE);

        telemetry.addLine("Initialized");
    }

    @Override
    public void start() {
        resetRuntime();
        limelight.start();
        lastTime = getRuntime();
    }

    @Override
    public void loop() {
        // 1. Driver inputs
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        // 2. Ask the Limelight if it sees our tag
        boolean seesTag = false;
        double tx = 0;
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            for (LLResultTypes.FiducialResult f : result.getFiducialResults()) {
                if (f.getFiducialId() == TARGET_TAG_ID) {
                    seesTag = true;
                    tx = f.getTargetXDegrees();
                }
            }
        }

        // 3. Auto-align while the left trigger is held
        boolean aligning = gamepad1.left_trigger > 0.3;
        if (aligning && seesTag) {
            error = goalX - tx;
            double now = getRuntime();
            double dT = now - lastTime;

            if (Math.abs(error) < angleTolerance) {
                rotate = 0;
            } else {
                double pTerm = error * kP;
                double dTerm = (dT > 0) ? ((error - lastError) / dT) * kD : 0;
                // If the robot turns AWAY from the tag, change this to -(pTerm + dTerm)
                rotate = Range.clip(pTerm + dTerm, -0.4, 0.4);
            }
            lastError = error;
            lastTime = now;
        } else {
            lastError = 0;
            lastTime = getRuntime();
        }

        // 4. Drive AFTER rotate has been decided (this was the original bug)
        drive.mecanumDrive(forward, strafe, rotate);

        // 5. Live tuning: B = step size, D-pad L/R = kP, D-pad U/D = kD
        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.dpadLeftWasPressed())  kP -= stepSizes[stepIndex];
        if (gamepad1.dpadRightWasPressed()) kP += stepSizes[stepIndex];
        if (gamepad1.dpadUpWasPressed())    kD += stepSizes[stepIndex];
        if (gamepad1.dpadDownWasPressed())  kD -= stepSizes[stepIndex];
        kP = Math.max(0, kP);
        kD = Math.max(0, kD);

        // 6. Telemetry
        if (aligning && seesTag) {
            telemetry.addLine("AUTO ALIGN");
        } else if (seesTag) {
            telemetry.addLine("Tag seen - hold left trigger to align");
        } else {
            telemetry.addLine("No tag - manual rotate");
        }
        telemetry.addData("tx (deg)", "%.2f", tx);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("-----------------------------");
        telemetry.addData("kP", "%.4f (D-pad L/R)", kP);
        telemetry.addData("kD", "%.4f (D-pad U/D)", kD);
        telemetry.addData("Step Size", "%.4f (B button)", stepSizes[stepIndex]);
        telemetry.update();
    }

    @Override
    public void stop() {
        limelight.stop();
    }
}