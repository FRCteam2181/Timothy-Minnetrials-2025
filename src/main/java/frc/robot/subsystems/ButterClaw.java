package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RollerConstants;
// import frc.robot.Constants.RollerConstants;
import frc.robot.Constants.butterClawConstants;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ButterClaw extends SubsystemBase{

    private final SparkMax ButterClawRotator;
    private final SparkMax ButterShooter;

    public ButterClaw() {
        ButterClawRotator = new SparkMax(butterClawConstants.ButterClawRotator_ID, MotorType.kBrushless);
        ButterShooter = new SparkMax(butterClawConstants.ButterClawWheel_ID, MotorType.kBrushless);

        // Set can timeout. Because this project only sets parameters once on
        // construction, the timeout can be long without blocking robot operation. Code
        // which sets or gets parameters during operation may need a shorter timeout.
        ButterClawRotator.setCANTimeout(250);
        ButterShooter.setCANTimeout(250);

        // Create and apply configuration for roller motor. Voltage compensation helps
        // the roller behave the same as the battery
        // voltage dips. The current limit helps prevent breaker trips or burning out
        // the motor in the event the roller stalls.
        SparkMaxConfig butterClawConfig = new SparkMaxConfig();
        butterClawConfig.voltageCompensation(RollerConstants.ROLLER_MOTOR_VOLTAGE_COMP);
        butterClawConfig.smartCurrentLimit(RollerConstants.ROLLER_MOTOR_CURRENT_LIMIT);
        butterClawConfig.idleMode(IdleMode.kBrake);

        ButterClawRotator.configure(butterClawConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        ButterShooter.configure(butterClawConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command ButterClawUp() {
        return this.startEnd(
            () -> {
                setMotorSpeed(butterClawConstants.ButterClawRotate_SPEED, ButterClawRotator);
            },

            () -> {
                setMotorSpeed(0, ButterClawRotator);
            }
        );
    }

    public Command ButterClawDown() {
        return this.startEnd(
            () -> {
                setMotorSpeed(-butterClawConstants.ButterClawRotate_SPEED, ButterClawRotator);
            },

            () -> {
                setMotorSpeed(0, ButterClawRotator);
            }
        );
    }

    public Command ShootButter() {
        return this.startEnd(
            () -> {
                setMotorSpeed(butterClawConstants.ButterClawShoot_SPEED, ButterShooter);
            },

            () -> {
                setMotorSpeed(0, ButterShooter);
            }
        );
    }

    public Command IntakeButter() {
        return this.startEnd(
            () -> {
                setMotorSpeed(butterClawConstants.ButterClawIntake_SPEED, ButterShooter);
            },

            () -> {
                setMotorSpeed(0, ButterShooter);
            }
        );
    }

    public void setMotorSpeed(double speed, SparkMax motor) {
        motor.set(speed);
    }
}
