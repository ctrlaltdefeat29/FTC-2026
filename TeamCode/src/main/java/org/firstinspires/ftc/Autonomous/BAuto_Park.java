package org.firstinspires.ftc.Autonomous;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class BAuto_Park extends LinearOpMode {
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
    public final int STOP_VELOCITY = 0;

    private DcMotorEx launchMotor = null;

    Servo gateServo = null;
    final double START_POSITION = 0.01;
    final double STOP_POSITION = 0.28;


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

        gateServo.setPosition(START_POSITION);
        telemetry.addLine("F### it, we ball");
        waitForStart();

        //

    }
}
