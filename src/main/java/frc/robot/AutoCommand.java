/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoCommand extends Command {
  private CommandGroup TheFutureIsNow;
  private final SendableChooser<Command> m_BackOrFor = new SendableChooser<>();
  private final SendableChooser<Command> m_AngleOrNo1 = new SendableChooser<>();
  private final SendableChooser<Command> m_AngleOrNo2 = new SendableChooser<>();
  private final SendableChooser<Command> m_AllAuto = new SendableChooser<>();
  private final SendableChooser<Command> m_LeftOfShoot = new SendableChooser<>();
  public AutoCommand() {
    m_AngleOrNo1.addOption("Turn45", new Turn45());
    m_AngleOrNo1.setDefaultOption("DoNothing", new DoNothing());
    m_AngleOrNo1.addOption("TurnNegative", new TurnNega45());
    m_AngleOrNo2.addOption("Turn45", new Turn45());
    m_AngleOrNo2.setDefaultOption("DoNothing", new DoNothing());
    m_AngleOrNo2.addOption("TurnNegative", new TurnNega45());
    m_AllAuto.setDefaultOption("Back", new AllAutoBack());
    m_AllAuto.addOption("Front", new AllAutoFront());
    m_LeftOfShoot.setDefaultOption("Red", new LeftShootRed());
    m_LeftOfShoot.addOption("Blue", new LeftShootBlue());
    m_LeftOfShoot.addOption("DoNothing", new DoNothing());
    m_BackOrFor.setDefaultOption("For25", new DriveFarAuto(25));
    m_BackOrFor.addOption("Back25", new ReverseReverse(25));
    m_BackOrFor.addOption("Back40", new ReverseReverse(40));
    m_BackOrFor.addOption("DoNothing", new DoNothing());

    SmartDashboard.putData("BackOrFor", m_BackOrFor);
    SmartDashboard.putData("AngleOrNo1", m_AngleOrNo1);
    SmartDashboard.putData("AngleOrNo2", m_AngleOrNo2);
    SmartDashboard.putData("AllAuto", m_AllAuto);
    SmartDashboard.putData("LeftOfShoot", m_LeftOfShoot);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    TheFutureIsNow = new CommandGroup();
    TheFutureIsNow.addSequential(m_LeftOfShoot.getSelected());
    TheFutureIsNow.addSequential(m_AngleOrNo1.getSelected());
    TheFutureIsNow.addSequential(m_BackOrFor.getSelected());
    TheFutureIsNow.addSequential(m_AngleOrNo2.getSelected());
    TheFutureIsNow.addSequential(m_AllAuto.getSelected());
    TheFutureIsNow.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
