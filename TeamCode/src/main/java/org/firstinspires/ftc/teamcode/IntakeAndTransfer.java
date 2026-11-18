package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "IntakeTransferTest", group = "StarterBot")
//@Disabled
public class IntakeAndTransfer extends OpMode {
    private DcMotorEx intakeMotor = null;
    private DcMotorEx transferMotor = null;
    private Servo pollenGate = null;
    //    private boolean intake = false;
    public final int INTAKE_VELOCITY = 2000;
    public final int TRANSFER_INIT_VELOCITY = 500;
    public final int TRANSFER_FINAl_VELOCITY = 2000;
    public final int TRANSFER_LAUNCH_VELOCITY = 1000;
    public final int FLYWHEEL_VELOCITY = 1600;
    public final int MIN_FLYWHEEL_VELOCITY = 1550;
    public final int STOP_VELOCITY = 0;
    public final double OPENPOS = 1;
    public final double CLOSEPOS = 0;

    private DcMotorEx launchMotor = null;
    @Override
    public void init() {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "Intake");
        transferMotor = hardwareMap.get(DcMotorEx.class, "Transfer");
        launchMotor = hardwareMap.get(DcMotorEx.class, "Launcher");
        pollenGate = hardwareMap.get(Servo.class, "Gate");

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferMotor.setDirection(DcMotor.Direction.REVERSE);
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launchMotor.setDirection(DcMotor.Direction.FORWARD);

        intakeMotor.setZeroPowerBehavior(BRAKE);
        pollenGate.setPosition(0.5);

    }

    @Override

    public void loop() {
        if(gamepad1.right_bumper){
            while(launchMotor.getVelocity() < MIN_FLYWHEEL_VELOCITY){launchMotor.setVelocity(FLYWHEEL_VELOCITY);
                telemetry.addData("LaunchMotor",launchMotor.getVelocity());}
            pollenGate.setPosition(OPENPOS);
            transferMotor.setVelocity(TRANSFER_LAUNCH_VELOCITY);
        }
//        launchMotor.setVelocity(FLYWHEEL_VELOCITY);
//        telemetry.addData("LaunchMotor",launchMotor.getVelocity());
        if(gamepad1.y){
            pollenGate.setPosition(CLOSEPOS);
            launchMotor.setVelocity(STOP_VELOCITY);
            intakeMotor.setVelocity(INTAKE_VELOCITY);
            transferMotor.setVelocity(TRANSFER_FINAl_VELOCITY);
        }
        if(gamepad1.x){
            intakeMotor.setVelocity(STOP_VELOCITY);
            transferMotor.setVelocity(STOP_VELOCITY);
            launchMotor.setVelocity(STOP_VELOCITY);
            launchMotor.setVelocity(STOP_VELOCITY);

        }

    }
}
