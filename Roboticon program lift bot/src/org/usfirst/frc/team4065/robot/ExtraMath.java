package org.usfirst.frc.team4065.robot;

public class ExtraMath {
	public ExtraMath(){
		
	}
	static int findSign(int input){
		return Math.abs(input) / input;
	}
	static int findSign(float input){
		return (int) (Math.abs(input) / input);
	}
	static int findSign(double input){
		return (int) (Math.abs(input) / input);
	}
	static double pidOutput(double kp, double ki, double kd, double proportionalError, double intergralError, double deltaError) {
		return (kp * proportionalError) + (ki * intergralError) + (kd * deltaError);
	}
}
