// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Libraries.GlobalPosition.GlobalPosition;
import frc.robot.Libraries.Logging.NerdLog;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  Joystick joyboy = new Joystick(0);


  Pose3d pose = new Pose3d(0, 0, 0, new Rotation3d());

  public Robot() {
    m_robotContainer = new RobotContainer();

    GlobalPosition.UpdatePoseRelativeToRobot("examplePose1", new Pose3d(5, 0, 2, new Rotation3d()));
    GlobalPosition.UpdatePoseRelativeToRobot("examplePose2", new Pose3d(0, 5, 0, new Rotation3d()));

    NerdLog.startLog();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    GlobalPosition.getPoses().forEach((name,poses) -> {
      NerdLog.logStructvariable(name, poses, Pose3d.struct);
    });

    NerdLog.logStructvariable("globalPose", GlobalPosition.getGlobalPose(), Pose3d.struct);
    



  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {

    pose = new Pose3d(pose.getX(), pose.getY(), joyboy.getX() >= 0.01? pose.getZ() + 0.01: pose.getZ(), pose.getRotation());
    GlobalPosition.changeGlobalPose(pose);

  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
