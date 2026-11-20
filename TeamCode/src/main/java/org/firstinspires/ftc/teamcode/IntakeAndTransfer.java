package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "IntakeTransferTest", group = "StarterBot")
@Disabled
public class IntakeAndTransfer extends OpMode {
    private DcMotorEx intakeMotor = null;
    private DcMotorEx transferMotor = null;
    //    private boolean intake = false;
    public final int INTAKE_VELOCITY = 2000;
    public final int BACKOUT_VELOCITY = -800;
    public final int TRANSFER_INIT_VELOCITY = 200;
    public final int TRANSFER_FINAl_VELOCITY = 2000;
    public final int FLYWHEEL_VELOCITY = 1400;
    public final int STOP_VELOCITY = 0;

    private DcMotorEx launchMotor = null;

    Servo gateServo = null;
    final double START_POSITION = 0.01;
    final double STOP_POSITION = 0.28;

    @Override
    public void init() {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "Intake");
        transferMotor = hardwareMap.get(DcMotorEx.class, "Transfer");
        launchMotor = hardwareMap.get(DcMotorEx.class, "Launcher");
        gateServo = hardwareMap.get(Servo.class,"Gate");


        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferMotor.setDirection(DcMotor.Direction.REVERSE);
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launchMotor.setDirection(DcMotor.Direction.FORWARD);

        intakeMotor.setZeroPowerBehavior(BRAKE);

        gateServo.setPosition(START_POSITION);

    }

    @Override

    public void loop() {

        launchMotor.setVelocity(FLYWHEEL_VELOCITY);
        telemetry.addData("LaunchMotor",launchMotor.getVelocity());
        if(gamepad1.y){
            gateServo.setPosition(START_POSITION);
            intakeMotor.setVelocity(INTAKE_VELOCITY);
            transferMotor.setPower(0.3);
        }
        if(gamepad1.x){
            intakeMotor.setVelocity(STOP_VELOCITY);
            transferMotor.setPower(STOP_VELOCITY);
        }
        if(gamepad1.b){
            intakeMotor.setVelocity(BACKOUT_VELOCITY);
        }
        if(gamepad1.right_bumper) {
            intakeMotor.setVelocity(STOP_VELOCITY);
            while(launchMotor.getVelocity() < FLYWHEEL_VELOCITY-50);
            gateServo.setPosition(STOP_POSITION);
            transferMotor.setPower(0.9);

        }

    }
}
