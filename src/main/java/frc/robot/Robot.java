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
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.autos.*;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();
  private final SendableChooser<Command> m_ball = new SendableChooser<>();
  private Command autonomousCommand;
  public static Drivetrain drivetrain;
  public static Shooter shooter;
  private Joystick m_DriveControl;
  private Joystick m_OperateControl;
  public static BallPickUp ballpickup;
  PIDController VisionPIDController = new PIDController(0.02, 0.04, 0.004);
  double deadzone = .25;
  public static PigeonIMU pigeon;
  public static Rack rack;
  public static Lift lift;
  public static CTRL_Panel Wheel;
  public static Climb climb;
  public static ThreeToFive spinspin;

  @Override
  public void robotInit() {
    climb = new Climb();
   Wheel = new CTRL_Panel();
   spinspin = new ThreeToFive();
   lift = new Lift();
   rack = new Rack();
   ballpickup = new BallPickUp();
   pigeon = new PigeonIMU(0);
   drivetrain = new Drivetrain();
   m_DriveControl = new Joystick(0);
   m_OperateControl = new Joystick(1);
   m_DriveControl.setYChannel(1);
   m_DriveControl.setXChannel(4);
   shooter = new Shooter();
   m_chooser.setDefaultOption("CenBackShoot", new Wolf());
   m_chooser.addOption("CenForShoot", new Pitbull());
   m_chooser.addOption("LeftForwardRight", new Crow());
   m_chooser.addOption("LeftCenBackShoot", new Dove());
   m_chooser.addOption("LeftTurnForShoot", new Robin());
   m_chooser.addOption("LeftBackTurnShoot", new Pigeon());
   m_chooser.addOption("RightForTurnShoot", new Dorito());
   m_chooser.addOption("RightCenBackShoot", new Pretzel());
   m_chooser.addOption("RightTurnForShoot", new Cheeto());
   m_chooser.addOption("RightBackTurnShoot", new Puffs());
   m_chooser.addOption("Apath", new GalaSearchA());
   //m_chooser.addOption("C9path", new GalaSearchAC9());
   m_chooser.addOption("Bpath", new GalaSearchB());
   m_chooser.addOption("liftautotest", new liftautotest());

   SmartDashboard.putData("Autos", m_chooser);

   //m_ball.setDefaultOption("Firm", object);
   //m_ball.addOption("Squishy", object);
   //m_ball.addOption("Soft", object);

   SmartDashboard.putData("Ball Quality", m_ball);

   SmartDashboard.putNumber("ShootSpeedInAuto", SmartDashboard.getNumber("ShootSpeedInAuto", 1.0));
  }

 @Override
public void autonomousInit() {
  super.autonomousInit();
  if (autonomousCommand != null) {
    autonomousCommand.cancel();
  }
  
  autonomousCommand = m_chooser.getSelected();
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
  if(autonomousCommand != null) {
    autonomousCommand.cancel();
  }
}

  @Override
  public void teleopPeriodic() {
double Ydeadzonedrive;
double Xdeadzonedrive;
//double Ydeadzoneclimb;
//double InDeadzone;
//double OutDeadzone;

/*if(Math.abs(m_OperateControl.getY())>deadzone) {
  Ydeadzoneclimb = Math.pow(m_OperateControl.getY(), 3);
}else {
  Ydeadzoneclimb = 0;
}*/

if(Math.abs(m_DriveControl.getY())>deadzone) {
  Ydeadzonedrive = Math.pow(m_DriveControl.getY(), 3);
}else {
  Ydeadzonedrive = 0;
}

if(Math.abs(m_DriveControl.getX())>deadzone) {
  Xdeadzonedrive = Math.pow(m_DriveControl.getX(), 3);
}else {
  Xdeadzonedrive = 0;
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
    SmartDashboard.putNumber("Shooter RPM", shooter.getRpm());
  //tracks limelight target
    if(m_DriveControl.getRawButton(5)){
      double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(2);
      double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
      SmartDashboard.putNumber("LimelightTX", tx);
      SmartDashboard.putNumber("LimelightTY", ty);
      double ControlX = VisionPIDController.calculate(-tx, 0);
      drivetrain.arcadeDrive(-Ydeadzonedrive, ControlX);
      SmartDashboard.putNumber("ControlX", ControlX);
    } else{
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
      drivetrain.arcadeDrive(-Ydeadzonedrive, Xdeadzonedrive);
      VisionPIDController.reset();
    }

    if(m_DriveControl.getRawButton(1)){
      climb.SpeedOfClimb(1.0);
    }
    else if(m_DriveControl.getRawButton(2)){
      climb.SpeedOfClimb(-1.0);
    }
    else if(m_DriveControl.getRawButton(3)){
      climb.SpeedOfClimb(0.50);
    }
    else if(m_DriveControl.getRawButton(4)){
      climb.SpeedOfClimb(-0.50);
    }
    else{
      climb.SpeedOfClimb(0);
    }

// shoots ball
    if(m_OperateControl.getRawButton(6)){
      shooter.ShootMotorSelect();
     
    } else {
      shooter.ShootMotorSpeed(0);
    }
    //speeds up shoot motor
    if(m_OperateControl.getRawButtonPressed(2)){
      shooter.SpeedSelectUp();
    }
    //slows down shoot motor
    if(m_OperateControl.getRawButtonPressed(3)){
      shooter.SpeedSelectDown();
    }
    //picks up ball
    if(m_OperateControl.getRawButton(8)){
      ballpickup.PickUpControl(1.0);

    }
    else if(m_OperateControl.getRawButton(7)){
      ballpickup.PickUpControl(-1.0);

    } 
    else{
      ballpickup.PickUpControl(0);

    }

    if(m_OperateControl.getRawButton(1)){
      lift.Lifttheball(1.0);
    }
    else if(m_OperateControl.getRawButton(4)){
      lift.Lifttheball(-1.0);
    }
    else{
    lift.Lifttheball(0);
    }
    //pivets ballrack down
    if(m_OperateControl.getRawButton(9)){
      rack.Rackpivit(0.80);
    } else if(m_OperateControl.getRawButton(10)){
      rack.Rackpivit(-0.25);
    }
     else{
      rack.Rackpivit(0);
    }
    //spin control panel
    if(m_OperateControl.getRawButton(12)){
      if(!Robot.spinspin.isRunning())
      {
        Robot.spinspin.start();
      }
    }

    SmartDashboard.putNumber("Wheel position", Wheel.SpinTheWheel());
    Scheduler.getInstance().run();
  } 
}
