/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_Faults;
import com.ctre.phoenix.ErrorCode;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private Command autonomousCommand;
  public static Drivetrain drivetrain;
  public static Shooter shooter;
  private Joystick m_DriveControl;
  private Joystick m_OperateControl;
  PIDController VisionPIDController = new PIDController(0.02, 0.04, 0.004);
  double deadzone = .25;
  public static PigeonIMU pigeon;

  @Override
  public void robotInit() {
    pigeon = new PigeonIMU(0);
    drivetrain = new Drivetrain();
    m_DriveControl = new Joystick(0);
    m_OperateControl = new Joystick(1);
    m_DriveControl.setYChannel(1);
    m_DriveControl.setXChannel(4);
    shooter = new Shooter();
  }

 @Override
public void autonomousInit() {
  super.autonomousInit();
  autonomousCommand = new SimpleAuto();
  autonomousCommand.start();
}

@Override
public void autonomousPeriodic() {
  super.autonomousPeriodic();
  Scheduler.getInstance().run();
}

@Override
public void teleopInit() {
  super.teleopInit();
  //autonomousCommand.cancel();
}

  @Override
  public void teleopPeriodic() {
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    SmartDashboard.putNumber("LimelightTX", tx);
    SmartDashboard.putNumber("LimelightTY", ty);
double Ydeadzone;
double Xdeadzone;
if(Math.abs(m_DriveControl.getY())>deadzone) {
  Ydeadzone = Math.pow(m_DriveControl.getY(), 3);
}else {
  Ydeadzone = 0;
}

if(Math.abs(m_DriveControl.getX())>deadzone) {
  Xdeadzone = Math.pow(m_DriveControl.getX(), 3);
}else {
  Xdeadzone = 0;
}
double ControlX = VisionPIDController.calculate(-tx, 0);
    if(m_DriveControl.getRawButton(5)){
      drivetrain.arcadeDrive(-Ydeadzone, ControlX);
    } else {
      drivetrain.arcadeDrive(-Ydeadzone, Xdeadzone);
      VisionPIDController.reset();
    }


    double [] ypr_deg = new double[3];
    ErrorCode pigeonResult = pigeon.getYawPitchRoll(ypr_deg);
    PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
    ErrorCode generalStatusResult = pigeon.getGeneralStatus(genStatus);
    SmartDashboard.putNumber("Yaw:", ypr_deg[0]);
    SmartDashboard.putNumber("Pitch:", ypr_deg[1]);
    SmartDashboard.putNumber("Roll:", ypr_deg[2]);
    SmartDashboard.putString("Pigeon Error Code", pigeonResult.toString());
    SmartDashboard.putString("Pigeon General Status Error Code", generalStatusResult.toString());
    SmartDashboard.putString("Pigeon General Status", genStatus.toString());
    SmartDashboard.putNumber("ControlX", ControlX);



    if(m_DriveControl.getRawButton(3)){
      shooter.ShootMotorSelect();
    } else {
      shooter.ShootMotorSpeed(0);
    }
    
    if(m_OperateControl.getRawButtonPressed(1)){
      shooter.SpeedSelectUp();
    }
    if(m_OperateControl.getRawButtonPressed(2)){
      shooter.SpeedSelectDown();
    }
  } 
}
