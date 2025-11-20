package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PopcornShooterConstants;

public class PopcornShooter extends SubsystemBase{

    SparkFlex PopcornShooterWheel;

    public PopcornShooter() {
        PopcornShooterWheel = new SparkFlex(PopcornShooterConstants.PopcornShooter_ID, MotorType.kBrushless);

        PopcornShooterWheel.setCANTimeout(250);

        SparkFlexConfig PopCornConfig = new SparkFlexConfig();
        PopCornConfig.idleMode(IdleMode.kBrake);
        PopCornConfig.smartCurrentLimit(PopcornShooterConstants.PopcornShooter_CURRENT_LIMIT);
        PopCornConfig.voltageCompensation(PopcornShooterConstants.PopcornShooter_MOTOR_VOLTAGE_COMP);

        PopcornShooterWheel.configure(PopCornConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public Command launchPopcorn() {
        return this.startEnd(
            () -> {
                setMotorSpeed(PopcornShooterConstants.PopcornShooter_SPEED, PopcornShooterWheel);
            },

            () -> {
                setMotorSpeed(0, PopcornShooterWheel);
            }
        );
    }


    private void setMotorSpeed(double speed, SparkFlex motor) {
        motor.set(speed);
    }
    
}
