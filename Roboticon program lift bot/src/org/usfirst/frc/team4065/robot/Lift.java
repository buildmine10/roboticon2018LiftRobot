package org.usfirst.frc.team4065.robot;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team4065.robot.ExtraMath;

public class Lift {
	
	WPI_TalonSRX lift;
	float kp = 0.001f;
	float ki = 0.000001f;
	float kd = 0.000001f;
	float proportionalError = 0;
	float intergralError = 0;
	float deltaError = 0;
	float pastError = 0;
	float target = 0;
	
	public Lift(int motorId) {
		lift = new WPI_TalonSRX(motorId);
		lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
	}
	
	void liftHeightControl(boolean up, boolean down) {
		if(up)
			target += 0.1;
		if(down)
			target -= 0.1;
		
		proportionalError = (float) (lift.getSelectedSensorPosition() - Math.floor(target));
		intergralError += proportionalError;
		deltaError = proportionalError - pastError;
		pastError = proportionalError;
		
		lift.set(ExtraMath.pidOutput(kp, ki, kd, proportionalError, intergralError, deltaError));
	}
}
