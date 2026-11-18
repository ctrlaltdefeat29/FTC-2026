package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "TeleOpNew", group = "StarterBot")
@Disabled
public class TeleOpNew extends OpMode {
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

    //    private boolean intake = false;
    @Override
    public void init() {
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

    }

    @Override
    public void loop() {
        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

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
//
//        leftFrontDrive.setPower(leftFrontPower);
//        rightFrontDrive.setPower(rightFrontPower);
//        leftBackDrive.setPower(leftBackPower);
//        rightBackDrive.setPower(rightBackPower);
//
//        telemetry.addData("forward", forward);
//        telemetry.addData("strafe", strafe);
//        telemetry.addData("rotate", rotate);
        // Smooth-start setting
//        double maxStep = 0.04;

// Calculate mecanum drive powers
//        double denominator = Math.max(
//                Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate),
//                1.0
//        );
//
//        double leftFrontTarget =
//                (forward + strafe + rotate) / denominator;
//        double rightFrontTarget =
//                (forward - strafe - rotate) / denominator;
//        double leftBackTarget =
//                (forward - strafe + rotate) / denominator;
//        double rightBackTarget =
//                (forward + strafe - rotate) / denominator;

// Ramp all four wheels
//        leftFrontPower = rampPower(leftFrontPower, leftFrontTarget, maxStep);
//        rightFrontPower = rampPower(rightFrontPower, rightFrontTarget, maxStep);
//        leftBackPower = rampPower(leftBackPower, leftBackTarget, maxStep);
//        rightBackPower = rampPower(rightBackPower, rightBackTarget, maxStep);

// Apply powers
        leftFrontDrive.setPower(leftFrontPower*0.5);
        rightFrontDrive.setPower(rightFrontPower*0.5);
        leftBackDrive.setPower(leftBackPower*0.5);
        rightBackDrive.setPower(rightBackPower*0.5);

// Telemetry
        telemetry.addData("Forward", forward);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Rotate", rotate);
        telemetry.addData("LF velocity", leftFrontDrive.getVelocity());
        telemetry.addData("RF velocity", rightFrontDrive.getVelocity());
        telemetry.addData("LB velocity", leftBackDrive.getVelocity());
        telemetry.addData("RB velocity", rightBackDrive.getVelocity());
        telemetry.update();
    }

    private double rampPower(double current, double target,
                             double maxStep) {
        double difference = target - current;

        difference = Math.max(-maxStep,
                Math.min(maxStep, difference));

        return current + difference;
    }
}
