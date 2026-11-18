package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "IntakeTest", group = "StarterBot")
@Disabled
public class Intake extends OpMode {
    private DcMotorEx intakeMotor = null;
    //    private boolean intake = false;
    @Override
    public void init() {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "Intake");

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotor.setZeroPowerBehavior(BRAKE);

    }

    @Override
    public void loop() {
        if(gamepad1.y){
            intakeMotor.setVelocity(1000);
        }
        if(gamepad1.x){
            intakeMotor.setVelocity(0);
        }

    }
}
