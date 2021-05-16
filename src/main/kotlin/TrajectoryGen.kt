import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.MarkerCallback
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints

object TrajectoryGen {
    // Remember to set these constraints to the same values as your DriveConstants.java file in the quickstart
    private val driveConstraints = DriveConstraints(60.0, 60.0, 0.0, 270.0.toRadians, 270.0.toRadians, 0.0)

    // Remember to set your track width to an estimate of your actual bot to get accurate trajectory profile duration!
    private const val trackWidth = 16.0

    private val combinedConstraints = MecanumConstraints(driveConstraints, trackWidth)

    private val startPose = Pose2d(-48.0, -48.0, 180.0.toRadians)
    private val wobbleGoalPose1 = Pose2d(36.0, -48.0, 0.0.toRadians)
    private val highGoalPose1 = Pose2d(-36.0, -48.0, 0.0.toRadians)
    private val highGoalPose2 = Pose2d(-36.0, -36.0, 0.0.toRadians)
    private val highGoalPose3 = Pose2d(-12.0, -36.0, 0.0.toRadians)

    fun createTrajectory(): ArrayList<Trajectory> {
        val list = ArrayList<Trajectory>()

        val wobbleGoalBuilder1 = TrajectoryBuilder(startPose, startPose.heading, combinedConstraints)
        wobbleGoalBuilder1.lineToLinearHeading(Pose2d(36.0, -48.0, 0.0.toRadians))
//            .addTemporalMarker(2.0, MarkerCallback{ println("Dropping wobble...")})
        list.add(wobbleGoalBuilder1.build())
        // Drop wobble

        val highGoal1 = TrajectoryBuilder(wobbleGoalPose1, wobbleGoalPose1.heading, combinedConstraints)
        highGoal1.back(72.0)
        list.add(highGoal1.build())
        val highGoal2 = TrajectoryBuilder(highGoalPose1, highGoalPose1.heading, combinedConstraints)
        highGoal2.strafeLeft(12.0)
        list.add(highGoal2.build())
        val intake = TrajectoryBuilder(highGoalPose2, highGoalPose2.heading, combinedConstraints)
        intake.forward(24.0)
        list.add(intake.build())
        //Shoot here

        val wobbleGoalBuilder2_1 = TrajectoryBuilder(highGoalPose3, highGoalPose3.heading, combinedConstraints)
        wobbleGoalBuilder2_1.back(36.0)
        list.add(wobbleGoalBuilder2_1.build())
        val wobbleGoalBuilder2_2 = TrajectoryBuilder(Pose2d(-48.0, -36.0, 0.0.toRadians), 0.0.toRadians, combinedConstraints)
        wobbleGoalBuilder2_2.strafeLeft(1.0)
        list.add(wobbleGoalBuilder2_2.build())
        // Grab Wobble Goal
        val wobbleGoalBuilder2_3 = TrajectoryBuilder(Pose2d(-48.0, -35.0, 0.0.toRadians), 0.0.toRadians, combinedConstraints)
        wobbleGoalBuilder2_3.lineToLinearHeading(Pose2d(36.0, -24.0, 180.0.toRadians))
        list.add(wobbleGoalBuilder2_3.build())

        val centerLineBuilder = TrajectoryBuilder(Pose2d(36.0, -24.0, 180.0.toRadians), 180.0.toRadians, combinedConstraints)
        centerLineBuilder.forward(24.0)
        list.add(centerLineBuilder.build())


        // Small Example Routine
//        builder1
//            .splineTo(Vector2d(10.0, 10.0), 0.0)
//            .splineTo(Vector2d(15.0, 15.0), 90.0);


        return list
    }

    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
}

val Double.toRadians get() = (Math.toRadians(this))
