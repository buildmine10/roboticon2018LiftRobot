package org.usfirst.frc.team4065.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team4065.robot.ExtraMath;

public class Drivetrain {
	WPI_TalonSRX leftMotor1, leftMotor2, rightMotor1, rightMotor2;
	public Drivetrain() {
		leftMotor1 = new WPI_TalonSRX(1);
		leftMotor2 = new WPI_TalonSRX(2);
		rightMotor1 = new WPI_TalonSRX(3);
		rightMotor2 = new WPI_TalonSRX(4);
	}
	
	void dualStickDrive(double leftStick, double rightStick) {
		leftMotor1.set(Math.pow(leftStick, 2) * ExtraMath.findSign(leftStick)); 
		leftMotor2.set(Math.pow(leftStick, 2) * ExtraMath.findSign(leftStick));
		rightMotor1.set(Math.pow(rightStick, 2) * ExtraMath.findSign(rightStick));
		rightMotor2.set(Math.pow(rightStick, 2) * ExtraMath.findSign(rightStick));
	}
	
	void leftStickDrive(double leftY, double leftX) {
		// Sets left motors to speed
		leftMotor1.set(Math.pow((leftY - leftX), 2) * ExtraMath.findSign(leftY - leftX));
		leftMotor2.set(Math.pow((leftY - leftX), 2) * ExtraMath.findSign(leftY - leftX)); 
		// Sets right motors to speed
		rightMotor1.set(Math.pow((leftY + leftX), 2) * ExtraMath.findSign(leftY - leftX));
		rightMotor2.set(Math.pow((leftY + leftX), 2) * ExtraMath.findSign(leftY - leftX));
		// End right motors
	}
}
