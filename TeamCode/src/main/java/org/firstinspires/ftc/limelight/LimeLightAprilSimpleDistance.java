//package org.firstinspires.ftc.limelight;
//
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//
//@Disabled
//public class AprilTagLimelightTestBrogenPrattVersion extends OpMode {
//    private Limelight3A limelight;
//    private IMU imu;
//    private double distance;
//
//    @Override
//    public void init() {
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.pipelineSwitch(8); // april tag #11 pipeline
//        imu = hardwareMap.get(IMU.class, "imu");
//        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
//        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
//    }
//
//    @Override
//    public void start() {
//        limelight.start();
//    }
//
//    @Override
//    public void loop() {
//        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//        limelight.updateRobotOrientation(orientation.getYaw());
//
//        LLResult llResult = limelight.getLatestResult();
//        if (llResult != null && llResult.isValid()) {
//            Pose3D botPose = llResult.getBotpose_MT2();
//            distance = getDistanceFromTag(llResult.getTa());
//            telemetry.addData("Distance", distance);
//            telemetry.addData("Tx", llResult.getTx());
//            telemetry.addData("Ty", llResult.getTy());
//            telemetry.addData("Ta", llResult.getTa());
//        }
//    }
//
//    public double getDistanceFromTag(double ta) {
//        double scale = 30665.95; // scale is y value from curve that Brogen Pratt has done, we can change it per our scale information
//        double distance = (scale / ta);
//        return distance;
//    }
//}