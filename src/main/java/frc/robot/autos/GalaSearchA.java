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
import frc.robot.ShootAutoFront;
import frc.robot.ShootSpeedAT;
import frc.robot.TurnToHeading;
import frc.robot.LiftInAuto;
import frc.robot.ReverseReverse;
import frc.robot.StopShootAuto;
import frc.robot.StopLiftAuto;

public class GalaSearchA extends CommandGroup {
  /**
   * Add your docs here.
   * If this isn't done by March 24, just know that it is just a matter of messing with numbers
   */
  public GalaSearchA() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.
    addSequential(new ReverseReverse(-60));
    //addSequential(new LiftInAuto(), 5);
    //addSequential(new StopLiftAuto());
    addSequential(new TurnToHeading(-26), 1.5);
    addSequential(new ReverseReverse(-62));
    //addSequential(new LiftInAuto(), 5);
    //addSequential(new StopLiftAuto());
    addSequential(new TurnToHeading(-18), 1.5);
    addSequential(new ReverseReverse(-42));
    //addSequential(new LiftInAuto(), 5);
    //addSequential(new StopLiftAuto());
    addSequential(new TurnToHeading(105), 1.5);
    addSequential(new ReverseReverse(-98));
    //addSequential(new LiftInAuto(), 5);
    //addSequential(new StopLiftAuto());
    addSequential(new TurnToHeading(-125), 2.5);
    addSequential(new ReverseReverse(-62));
    //addSequential(new LiftInAuto(), 5);
    //addSequential(new StopLiftAuto());
    addSequential(new TurnToHeading(10), 1.5);
    addSequential(new ReverseReverse(-60));
    //addSequential(new LiftInAuto(), 5);
    //addSequential(new StopLiftAuto());
    addSequential(new TurnToHeading(26), 1.5);
    addSequential(new ReverseReverse(-60));
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
