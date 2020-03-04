/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class BallPickUp extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX BallMotor1 = new WPI_TalonSRX(4);
  private WPI_TalonSRX BallMotor2 = new WPI_TalonSRX(6);

  public void PickUpControl(double speed){
    BallMotor1.set(speed);
    BallMotor2.set(-speed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
