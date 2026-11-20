//package org.firstinspires.ftc.teamcode;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//import java.util.List;
//
//public class AprilTagWebcam {
//    private Limelight3A limelight;
//    private IMU imu;
//    private double CameraHeight = 15.132; //inches
//    private double GoalHeight = 54; //inches
//    private double CameraAngle = 32.2; //angle A
//
//    private Telemetry telemetry;
//
//    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
//        this.telemetry = telemetry;
//        limelight = hardwareMap.get(Limelight3A.class,"limelight");
//
//        limelight.pipelineSwitch(8); //april tag #11 pipeline
//        imu = hardwareMap.get(IMU.class, "imu");
//        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);// double check both directions...
//        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
//    }
//
//    public void start() {
//        limelight.start();
//    }
//
//    public void update() {
//        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//        limelight.updateRobotOrientation(orientation.getYaw());
//        LLResult llResult = limelight.getLatestResult();
//        if (llResult != null && llResult.isValid()){
//            Pose3D botpose = llResult.getBotpose_MT2();
//            telemetry.addData("Tx", llResult.getTx());
//            telemetry.addData("Ty", llResult.getTy());
//            telemetry.addData("Ta", llResult.getTa());
//            telemetry.addData("DirectDistance", getDirectDistance(llResult.getTy()));
//            telemetry.addData("HorizontalDistance", getHorizontalDistance(llResult.getTy()));
//
//        } else {
//            telemetry.addData("No valid target", "Nope");
//        }
//
//    }
//
//    public AprilTagDetection getTagBySpecificID(int id) {
//        List<AprilTagDetection> detections =
//                aprilTag.getDetections();
//
//        for (AprilTagDetection detection : detections) {
//
//            telemetry.addData("Tag ID", detection.id);
//
//            if (detection.metadata != null) {
//                telemetry.addData(
//                        "Tag Name",
//                        detection.metadata.name
//                );
//            }
//        }
//        return limelight.getDetections();;
//    }
//    public double getDirectDistance(double ty){
//        double heightDifference = GoalHeight - CameraHeight;
//        double angleInDegrees = CameraAngle + ty;
//
//        return heightDifference / Math.sin(Math.toRadians(angleInDegrees));
//    }
//    public double getHorizontalDistance(double ty){
//        double heightDifference = GoalHeight - CameraHeight;
//        double angleInDegrees = CameraAngle + ty;
//
//        return heightDifference / Math.tan(Math.toRadians(angleInDegrees));
//    }
//
//
//}
