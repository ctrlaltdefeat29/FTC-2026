package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "IntakeTransferTest", group = "StarterBot")
//@Disabled
public class IntakeAndTransfer extends OpMode {
    private DcMotorEx intakeMotor = null;
    private DcMotorEx transferMotor = null;
    //    private boolean intake = false;
    public final int INTAKE_VELOCITY = 2000;
    public final int TRANSFER_INIT_VELOCITY = 500;
    public final int TRANSFER_FINAl_VELOCITY = 2000;
    public final int FLYWHEEL_VELOCITY = 1600;
    public final int STOP_VELOCITY = 0;

    private DcMotorEx launchMotor = null;
    @Override
    public void init() {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "Intake");
        transferMotor = hardwareMap.get(DcMotorEx.class, "Transfer");
        launchMotor = hardwareMap.get(DcMotorEx.class, "Launcher");

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferMotor.setDirection(DcMotor.Direction.REVERSE);
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launchMotor.setDirection(DcMotor.Direction.FORWARD);

        intakeMotor.setZeroPowerBehavior(BRAKE);

    }

    @Override

    public void loop() {

        launchMotor.setVelocity(FLYWHEEL_VELOCITY);
        telemetry.addData("LaunchMotor",launchMotor.getVelocity());
        if(gamepad1.y){
            intakeMotor.setVelocity(INTAKE_VELOCITY);
            transferMotor.setVelocity(TRANSFER_FINAl_VELOCITY);
        }
        if(gamepad1.x){
            intakeMotor.setVelocity(STOP_VELOCITY);
            transferMotor.setVelocity(STOP_VELOCITY);
            launchMotor.setVelocity(STOP_VELOCITY);

        }

    }
}
