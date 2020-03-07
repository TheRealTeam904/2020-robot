/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FaceTarget extends Command {

  PIDController TurnrateController = new PIDController(0.02, 0.04, 0.004);
  PIDController ThrottleController = new PIDController(.01 ,0.00003 ,0 );
  double h_target = 91;
  double h_camera = 41;
  double disireddistance;

  public FaceTarget() {
    //disireddistance = distance;
    requires(Robot.drivetrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putString("Current Command", "FaceTarget");
    TurnrateController.reset();
    ThrottleController.reset();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(2);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double ControlX = -TurnrateController.calculate(tx, 0);
    double [] ypr_deg = new double [3];
    Robot.pigeon.getYawPitchRoll(ypr_deg);
    SmartDashboard.putNumber("Pitch", ypr_deg[1]);

    SmartDashboard.putNumber("LimelightTX Auto", tx);
    SmartDashboard.putNumber("LimelightTY", ty);
    Robot.drivetrain.arcadeDrive(0, ControlX);
    }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false /*TurnrateController.atSetpoint() && ThrottleController.atSetpoint()*/;

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