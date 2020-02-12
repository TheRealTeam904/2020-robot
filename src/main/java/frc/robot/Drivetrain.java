/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  private WPI_TalonSRX m_left0 = new WPI_TalonSRX(2);
  private WPI_TalonSRX m_left1 = new WPI_TalonSRX(3);
  //private WPI_TalonSRX m_left2 = new WPI_TalonSRX(4);
  private SpeedControllerGroup m_left = new SpeedControllerGroup(m_left0, m_left1/*, m_left2*/);
  private WPI_TalonSRX m_right0 = new WPI_TalonSRX(5);
  private WPI_TalonSRX m_right1 = new WPI_TalonSRX(6);
  //private WPI_TalonSRX m_right2 = new WPI_TalonSRX(7);
  private SpeedControllerGroup m_right = new SpeedControllerGroup(m_right0, m_right1/*, m_right2*/);
  private DifferentialDrive m_myDrivetrain = new DifferentialDrive(m_left, m_right);
  private int offset = 0;

  public void arcadeDrive(double throttle, double turnrate){
    m_myDrivetrain.arcadeDrive(throttle, turnrate, false);
    SmartDashboard.putNumber("throttle", throttle);
    SmartDashboard.putNumber("turnrate", turnrate);
  }
  
  public void resetdistancetraveled(){
    offset = m_left0.getSensorCollection().getQuadraturePosition();
  }
  

  public double getdistancetraveled(){
    int realencoderticks = m_left0.getSensorCollection().getQuadraturePosition();
    int fakeencoderticks = realencoderticks - offset;
    double inches = fakeencoderticks * 0.004601; 
    SmartDashboard.putNumber("Traveled Distance", inches);
     return -inches;
  }


  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
