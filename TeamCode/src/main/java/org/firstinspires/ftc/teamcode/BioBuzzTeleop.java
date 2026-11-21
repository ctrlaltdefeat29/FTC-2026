package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "BioBuzzTeleop", group = "StarterBot")
//@Disabled
public class BioBuzzTeleop extends OpMode {
    private DcMotorEx intakeMotor = null;
    private DcMotorEx transferMotor = null;
    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightBackDrive = null;
    //    private boolean intake = false;
    public final int INTAKE_VELOCITY = 2000;
    public final int BACKOUT_VELOCITY = -800;
    public final int TRANSFER_INIT_VELOCITY = 200;
    public final int TRANSFER_FINAl_VELOCITY = 2000;
    public final int FLYWHEEL_VELOCITY = 1150;
    public final int STOP_VELOCITY = 0;

    private DcMotorEx launchMotor = null;

    Servo gateServo = null;
    final double CLOSED_POSITION = 0.01;
    final double OPENED_POSITION = 0.20;
    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;

    @Override
    public void init() {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "Intake");
        transferMotor = hardwareMap.get(DcMotorEx.class, "Transfer");
        launchMotor = hardwareMap.get(DcMotorEx.class, "Launcher");
        gateServo = hardwareMap.get(Servo.class,"Gate");

        leftFrontDrive = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "RightFront");
        leftBackDrive = hardwareMap.get(DcMotorEx.class, "LeftBack");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "RightBack");

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferMotor.setDirection(DcMotor.Direction.REVERSE);
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launchMotor.setDirection(DcMotor.Direction.FORWARD);

        intakeMotor.setZeroPowerBehavior(BRAKE);

        gateServo.setPosition(CLOSED_POSITION);
    }

    @Override

    public void loop() {

        launchMotor.setVelocity(FLYWHEEL_VELOCITY);
        telemetry.addData("LaunchMotor",launchMotor.getVelocity());

        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        if(gamepad1.y){
            gateServo.setPosition(CLOSED_POSITION);
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
        if(gamepad1.a) {
            gateServo.setPosition(CLOSED_POSITION);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gateServo.setPosition(OPENED_POSITION);
        }
        if(gamepad1.right_bumper) {
            intakeMotor.setVelocity(STOP_VELOCITY);
            while(launchMotor.getVelocity() < FLYWHEEL_VELOCITY-50);
            transferMotor.setPower(0.7);
            gateServo.setPosition(OPENED_POSITION);
        }
    }

    void mecanumDrive(double forward, double strafe, double rotate){

        /* the denominator is the largest motor power (absolute value) or 1
         * This ensures all the powers maintain the same ratio,
         * but only if at least one is out of the range [-1, 1]
         */

        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);
        leftFrontPower = (forward + strafe + rotate) / denominator;
        rightFrontPower = (forward - strafe - rotate) / denominator;
        leftBackPower = (forward - strafe + rotate) / denominator;
        rightBackPower = (forward + strafe - rotate) / denominator;
// Apply powers
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

// Telemetry
        telemetry.addData("Forward", forward);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Rotate", rotate);
        telemetry.addData("LF velocity", leftFrontDrive.getVelocity());
        telemetry.addData("RF velocity", rightFrontDrive.getVelocity());
        telemetry.addData("LB velocity", leftBackDrive.getVelocity());
        telemetry.addData("RB velocity", rightBackDrive.getVelocity());
    }
}
