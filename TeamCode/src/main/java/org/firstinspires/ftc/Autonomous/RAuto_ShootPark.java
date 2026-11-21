package org.firstinspires.ftc.Autonomous;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class RAuto_ShootPark extends LinearOpMode {
    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightBackDrive = null;

    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;
    final double STOP_SPEED = 0;
    double maxStep = 0.02;
    private DcMotorEx intakeMotor = null;
    private DcMotorEx transferMotor = null;
    //    private boolean intake = false;
    public final int INTAKE_VELOCITY = 2000;
    public final int BACKOUT_VELOCITY = -800;
    public final int TRANSFER_INIT_VELOCITY = 200;
    public final int TRANSFER_FINAl_VELOCITY = 2000;
    public final int FLYWHEEL_VELOCITY = 1400;
    public final int MIN_FLYWHEEL_VELOCITY = 1350;
    private DcMotorEx launchMotor = null;

    Servo gateServo = null;
    final double CLOSED_POSITION = 0.01;
    final double OPENED_POSITION = 0.28;
    final double ticksPerInch = 2.17673879787;
    final int TRACK_WIDTH = 15;


    public void runOpMode() throws InterruptedException {
        leftFrontDrive = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "RightFront");
        leftBackDrive = hardwareMap.get(DcMotorEx.class, "LeftBack");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "RightBack");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);

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

        gateServo.setPosition(CLOSED_POSITION);
        telemetry.addLine("F### it, we ball");
        waitForStart();

        shootAll();
        sleep(250);
        moveForward(120);
        sleep(250);
        rotateRight(-90);
        sleep(250);
        moveForward(72);
    }
    public void moveForward(double inches) {

        int moveInches = (int) Math.round(ticksPerInch * inches);
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + moveInches);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + moveInches);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition()+ moveInches);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition()+moveInches);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setPower(0.5);
        leftBackDrive.setPower(0.5);
        rightFrontDrive.setPower(0.5);
        rightBackDrive.setPower(0.5);

        while (opModeIsActive() &&
                (leftFrontDrive.isBusy() || rightFrontDrive.isBusy()) || leftBackDrive.isBusy() ||rightBackDrive.isBusy()) {

            telemetry.addLine("MOVING...");
        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void rotateRight(int moveDegrees) {
        double turningDistance = Math.PI * TRACK_WIDTH * moveDegrees / 360.0;
        int degreesTurned = (int) Math.round(ticksPerInch * turningDistance);

        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + degreesTurned);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + degreesTurned);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() - degreesTurned);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() - degreesTurned);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setPower(0.5);
        leftBackDrive.setPower(0.5);
        rightFrontDrive.setPower(0.5);
        rightBackDrive.setPower(0.5);

        while (opModeIsActive() &&
                (leftFrontDrive.isBusy() || rightFrontDrive.isBusy()) || leftBackDrive.isBusy() ||rightBackDrive.isBusy()) {
        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void shootAll() {
        launchMotor.setVelocity(FLYWHEEL_VELOCITY);
        while(launchMotor.getVelocity() < MIN_FLYWHEEL_VELOCITY){
            telemetry.addData("Launcher Velocity:", launchMotor.getVelocity());
        }
        gateServo.setPosition(OPENED_POSITION);
        transferMotor.setVelocity(TRANSFER_FINAl_VELOCITY);
        sleep(2000);
        gateServo.setPosition(CLOSED_POSITION);
        transferMotor.setVelocity(STOP_SPEED);
    }
}
