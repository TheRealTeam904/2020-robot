/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToHeading extends Command {
  /** 
   * Creates a new TurnToHeading.
   */
  double disireddirection;
  PIDController turnRateController = new PIDController(0.01, 0, 0);
  public TurnToHeading(double direction) {
    disireddirection = direction;
    // Use addRequirements() here to declare subsystem dependencies.
    requires(Robot.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turnRateController.setTolerance(3);
    turnRateController.enableContinuousInput(0, 360);
    double FacingDirection;
    double [] ypr_deg = new double [3];
    Robot.pigeon.getYawPitchRoll(ypr_deg);
    FacingDirection = ((ypr_deg[0] % 360) + 360) % 360;
    turnRateController.setSetpoint((disireddirection + FacingDirection) % 360);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double [] ypr_deg = new double [3];
    Robot.pigeon.getYawPitchRoll(ypr_deg);
    double measurement = ((ypr_deg[0] % 360) + 360) % 360;
    SmartDashboard.putNumber("Yaw", measurement);
    double controlOutput = -turnRateController.calculate(measurement);
    SmartDashboard.putNumber("ControlOutput", controlOutput);
    Robot.drivetrain.arcadeDrive(0, controlOutput);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end() {
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return turnRateController.atSetpoint();
  }
}