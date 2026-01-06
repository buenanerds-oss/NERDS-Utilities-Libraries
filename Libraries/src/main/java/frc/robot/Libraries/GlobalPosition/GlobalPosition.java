package frc.robot.Libraries.GlobalPosition;

import java.util.HashMap;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;

public class GlobalPosition {

    private static Pose3d globalPose = new Pose3d();
    private static HashMap<String,Pose3d> poses = new HashMap<>();

    /**
     * adds and updates the pose to all of the poses relative to the global position
     * it then calculates a new global pose from the average of all the poses
     * 
     * @param name - name of the pose
     * @param pose - the pose to be added/updates
     */
    public static void UpdatePoseRelativeToRobot(String name, Pose3d pose) {
        
        //adds if it doesn't exist
        if (!poses.containsKey(name)) {
            poses.put(name, pose);
        }

        //updates the position with the new value
        poses.replace(name, pose);

        // Calculates global position
         var poseSums = new Object() {
            double xPos = 0;
            double yPos = 0;
            double zPos = 0;
         };
        

        poses.forEach( (names, Posers) -> {
            //posers = poses

            poseSums.xPos = Posers.getX();
            poseSums.yPos = Posers.getY();
            poseSums.zPos = Posers.getZ();
            
        });

        globalPose = new Pose3d(poseSums.xPos/poses.size(), poseSums.yPos/poses.size(), poseSums.zPos/poses.size(), new Rotation3d());

       /* *if (!poses.containsKey("GlobalPose")) {
            poses.put("GlobalPose", globalPose);
        }

        poses.replace("GlobalPose", globalPose); */
    }

    public static void changeGlobalPose(Pose3d newGlobalPose) {
        Transform3d fromHereToThere = new Transform3d(globalPose, newGlobalPose);
        globalPose = newGlobalPose;
        poses.forEach((name, Pose) -> {
            
        });
    }

    public static HashMap<String, Pose3d> getPoses() {
        return poses;
    }

    public static Pose3d getGlobalPose() {
        return globalPose;
    }

}
