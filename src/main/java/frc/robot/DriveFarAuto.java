/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveFarAuto extends Command {
  double desireddistance;


  public DriveFarAuto(double distance) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    desireddistance = distance;
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {


    Robot.drivetrain.arcadeDrive( .2, 0);
    SmartDashboard.getNumber("Distance Traveled", Robot.drivetrain.getdistancetraveled());


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.drivetrain.getdistancetraveled() == desireddistance;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.arcadeDrive(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
