/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.DriveFarAuto;
import frc.robot.FaceTarget;
import frc.robot.ShootAutoBack;
import frc.robot.ShootAutoFront;
import frc.robot.TurnToHeading;
import frc.robot.LiftInAuto;
import frc.robot.StopShootAuto;
import frc.robot.StopLiftAuto;

public class Robin extends CommandGroup {
  /**
   * Add your docs here.
   * turn counter clock 45, forward 20, face target, shoot
   */
  public Robin() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.
    addSequential(new TurnToHeading(-45), 1);
    addSequential(new DriveFarAuto(20));
    addSequential(new FaceTarget(), 2);
    addParallel(new LiftInAuto());
    addSequential(new ShootAutoFront(), 7);
    addParallel(new StopLiftAuto());
    addSequential(new StopShootAuto());
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
