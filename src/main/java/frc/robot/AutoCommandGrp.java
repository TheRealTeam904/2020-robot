/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;

public class AutoCommandGrp extends CommandGroup {
  private final SendableChooser<Command> m_BackOrFor = new SendableChooser<>();
  private final SendableChooser<Command> m_AngleOrNo1 = new SendableChooser<>();
  private final SendableChooser<Command> m_AngleOrNo2 = new SendableChooser<>();
  private final SendableChooser<Command> m_AllAuto = new SendableChooser<>();
  private final SendableChooser<Command> m_LeftOfShoot = new SendableChooser<>();

  /**
   * Add your docs here.
   */
  public AutoCommandGrp() {
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
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
