import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.MarkerCallback
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints

object TrajectoryGenSquare {
    // Remember to set these constraints to the same values as your DriveConstants.java file in the quickstart
    private val driveConstraints = DriveConstraints(60.0, 60.0, 0.0, 270.0.toRadians, 270.0.toRadians, 0.0)

    // Remember to set your track width to an estimate of your actual bot to get accurate trajectory profile duration!
    private const val trackWidth = 16.0

    private val combinedConstraints = MecanumConstraints(driveConstraints, trackWidth)

    private val startPose = Pose2d(-48.0, -48.0, 180.0.toRadians1)

    fun createTrajectory(): ArrayList<Trajectory> {
        val list = ArrayList<Trajectory>()

        val builder1 = TrajectoryBuilder(startPose, startPose.heading, combinedConstraints)
        builder1.lineTo(Vector2d( -48.0, -24.0))

        val builder2 = TrajectoryBuilder(Pose2d(-48.0, -24.0, 180.0.toRadians1), 180.0.toRadians1, combinedConstraints)
        builder2.lineTo(Vector2d(-24.0, -24.0))

        val builder3 = TrajectoryBuilder(Pose2d(-24.0, -24.0, 180.0.toRadians1), 180.0.toRadians1, combinedConstraints)
        builder3.lineTo(Vector2d(-24.0, -48.0))

        val builder4 = TrajectoryBuilder(Pose2d(-24.0, -48.0, 180.0.toRadians1), 180.0.toRadians1, combinedConstraints)
        builder4.lineTo(Vector2d(-48.0, -48.0))


        // Small Example Routine
//        builder1
//            .splineTo(Vector2d(10.0, 10.0), 0.0)
//            .splineTo(Vector2d(15.0, 15.0), 90.0);

        list.add(builder1.build())
        list.add(builder2.build())
        list.add(builder3.build())
        list.add(builder4.build())

        return list
    }

    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
}

val Double.toRadians1 get() = (Math.toRadians(this))
