/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4065.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team4065.robot.ExtraMath;
import org.usfirst.frc.team4065.robot.Drivetrain;
import org.usfirst.frc.team4065.robot.Lift;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */



public class Robot extends TimedRobot {
	
	WPI_TalonSRX l1, l2, r1, r2; //drivetrain motors 
	WPI_TalonSRX liftM, liftM2;
	Joystick leftController, rightController;
	Compressor compressor;
	DoubleSolenoid motorGearShifter, claw;
	Drivetrain drivetrain = new Drivetrain();
	Lift lift = new Lift(10);
	
	public enum ControlStyle{
		DualStick, SingleStickLeft, SingleStickRight
	}
	
	ControlStyle controlStyle = ControlStyle.DualStick;
	
	@Override
	public void robotInit() {
		l1 = new WPI_TalonSRX(1);
		l2 = new WPI_TalonSRX(2);
		r1 = new WPI_TalonSRX(3);
		r2 = new WPI_TalonSRX(4);
		liftM = new WPI_TalonSRX(10);
		liftM2 = new WPI_TalonSRX(11);
		compressor = new Compressor(1);
		leftController = new Joystick(0);
		rightController = new Joystick(1);
		motorGearShifter = new DoubleSolenoid(1, 2);
		claw = new DoubleSolenoid(3, 4);
		
		
		r1.setInverted(true);
		r2.setInverted(true);
		liftM2.follow(liftM);
		compressor.start();
	}

	@Override
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// TEMP: added variables to only call the functions one to improve efficiency and readability
		int liftDirection = 0;
		double leftRawAxis0 = leftController.getRawAxis(0);
		double leftRawAxis1 = leftController.getRawAxis(1);
		double rightRawAxis0 = rightController.getRawAxis(0);
		double rightRawAxis1 = rightController.getRawAxis(1);
		
		switch(controlStyle){
		case DualStick :
			/*
			l1.set(Math.pow(leftRawAxis1, 2) * ExtraMath.findSign(leftRawAxis1)); 
			l2.set(Math.pow(leftRawAxis1, 2) * ExtraMath.findSign(leftRawAxis1));
			r1.set(Math.pow(rightRawAxis1, 2) * ExtraMath.findSign(rightRawAxis1));
			r2.set(Math.pow(rightRawAxis1, 2) * ExtraMath.findSign(rightRawAxis1));
			*/
			drivetrain.dualStickDrive(leftRawAxis1, rightRawAxis1);
			break;
		case SingleStickLeft :
			/*
			// Sets left motors to speed
			l1.set(Math.pow((leftRawAxis1 - leftRawAxis0), 2) * ExtraMath.findSign(leftRawAxis1 - leftRawAxis0));
			l2.set(Math.pow((leftRawAxis1 - leftRawAxis0), 2) * ExtraMath.findSign(leftRawAxis1 - leftRawAxis0)); 
			// Sets right motors to speed
			r1.set(Math.pow((leftRawAxis1 + leftRawAxis0), 2) * ExtraMath.findSign(leftRawAxis1 - leftRawAxis0));
			r2.set(Math.pow((leftRawAxis1 + leftRawAxis0), 2) * ExtraMath.findSign(leftRawAxis1 - leftRawAxis0));
			// End right motors
			*/
			
			drivetrain.leftStickDrive(leftRawAxis1, leftRawAxis0);
			break;
			
		default:
		}
		
		if(leftController.getRawButton(8)){
			controlStyle = ControlStyle.DualStick;
		}else if(leftController.getRawButton(9)){
			controlStyle = ControlStyle.SingleStickLeft;
		}
		
		/*
		if(rightController.getRawButton(3))
			liftDirection += 1;
		if(rightController.getRawButton(2))
			liftDirection += -1;
		*/
		
		lift.liftHeightControl(rightController.getRawButton(3), rightController.getRawButton(2));
		
		//liftM.set(0.5 * liftDirection);
		
		
		System.out.println(leftController.getRawButton(0));
		
		if(leftController.getRawButton(2) || leftController.getRawButton(3)) {
			if(leftController.getRawButton(2)) {
				motorGearShifter.set(Value.kForward);
			}
			if (leftController.getRawButton(3)){
				motorGearShifter.set(Value.kReverse);
			}
		}else {
			motorGearShifter.set(Value.kOff);
		}//controls the motor's gear ratio
		
		if(leftController.getRawButton(1) || rightController.getRawButton(1)) {
			if(leftController.getRawButton(1)) {
				claw.set(Value.kForward);
			}
			if (rightController.getRawButton(1)){
				claw.set(Value.kReverse);
			}
		}else {
			claw.set(Value.kOff);
		}//controls the claw

		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
