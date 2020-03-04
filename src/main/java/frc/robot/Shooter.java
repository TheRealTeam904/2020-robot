/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
 
 private WPI_TalonSRX ShootMotor1 = new WPI_TalonSRX(11);
 private WPI_TalonSRX ShootMotor2 = new WPI_TalonSRX(12);
 private SpeedControllerGroup ShootMotors = new SpeedControllerGroup(ShootMotor1, ShootMotor2);
 //double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
 public Shooter(){
   //ShootMotors.setInverted(true);
 }

 public double getRpm() {
   return ShootMotor2.getSensorCollection().getQuadratureVelocity();
 }

 public void SpeedSelectUp(){
  ItemTracker = (ItemTracker + 1) % ShootSpeedTable.length;
  SmartDashboard.putNumber("Speed Select", ShootSpeedTable[ItemTracker]);
 }
 public void SpeedSelectDown(){
   ItemTracker = (ItemTracker + ShootSpeedTable.length - 1) % ShootSpeedTable.length;
   SmartDashboard.putNumber("Speed Select", ShootSpeedTable[ItemTracker]);
 }

 
 public void ShootMotorSpeed(double speed) {
  ShootMotors.set(speed);
 }

double [] ShootSpeedTable = {0.50, 0.55, 0.60, 0.65, 0.70, 0.75, 0.80, 1.0};
int ItemTracker = 0;
public void ShootMotorSelect(){
  double speed = ShootSpeedTable[ItemTracker];
  ShootMotors.set(speed);
 }

 //public void SpeedSelectDistance(){
  //if(ty > 10){
  //ShootMotors.set(1.0);
  //}
 // if(ty < 10){
   // ShootMotors.set(1.0);
  //}
 //}

 


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
