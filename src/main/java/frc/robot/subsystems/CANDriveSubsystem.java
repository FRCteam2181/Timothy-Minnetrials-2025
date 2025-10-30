// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import com.revrobotics.spark.SparkBase.PersistMode;
// import com.revrobotics.spark.SparkBase.ResetMode;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.config.SparkMaxConfig;

// import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

// Class to drive the robot over CAN
public class CANDriveSubsystem extends SubsystemBase {
  private final WPI_TalonSRX leftLeader;
  private final WPI_VictorSPX leftFollower;
  private final WPI_TalonSRX rightLeader;
  private final WPI_VictorSPX rightFollower;

  private final DifferentialDrive drive;

  public CANDriveSubsystem() {
    // create brushed motors for drive
    leftLeader = new WPI_TalonSRX(DriveConstants.LEFT_LEADER_ID);
    leftFollower = new WPI_VictorSPX(DriveConstants.LEFT_FOLLOWER_ID);
    rightLeader = new WPI_TalonSRX(DriveConstants.RIGHT_LEADER_ID);
    rightFollower = new WPI_VictorSPX(DriveConstants.RIGHT_FOLLOWER_ID);

    // set up differential drive class
    drive = new DifferentialDrive(leftLeader, rightLeader);

    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.
    //leftLeader.setCANTimeout(250);
    //rightLeader.setCANTimeout(250);
    //leftFollower.setCANTimeout(250);
    //rightFollower.setCANTimeout(250);

    // Create the configuration to apply to motors. Voltage compensation
    // helps the robot perform more similarly on different
    // battery voltages (at the cost of a little bit of top speed on a fully charged
    // battery). The current limit helps prevent tripping
    // breakers.
    //SparkMaxConfig config = new SparkMaxConfig();
    //config.voltageCompensation(12);
    //config.smartCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);

    // Set configuration to follow leader and then apply it to corresponding
    // follower. Resetting in case a new controller is swapped
    // in and persisting in case of a controller reset due to breaker trip
    leftFollower.follow(leftLeader);
    //leftFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFollower.follow(rightLeader);
    //rightFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Remove following, then apply config to right leader
    //config.disableFollowerMode();
    //rightLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // Set conifg to inverted and then apply to left leader. Set Left side inverted
    // so that postive values drive both sides forward
    rightLeader.setInverted(true);
    rightFollower.setInverted(true);
    //leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    leftLeader.configVoltageCompSaturation(12);
    leftLeader.enableVoltageCompensation(true);
    leftLeader.enableCurrentLimit(true);
    leftLeader.configPeakCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);

    leftFollower.configVoltageCompSaturation(12);
    leftFollower.enableVoltageCompensation(true);

    rightLeader.configVoltageCompSaturation(12);
    rightLeader.enableVoltageCompensation(true);
    rightLeader.enableCurrentLimit(true);
    rightLeader.configPeakCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);

    rightFollower.configVoltageCompSaturation(12);
    rightFollower.enableVoltageCompensation(true);
  }

  @Override
  public void periodic() {
  }

  // sets the speed of the drive motors
  public void driveArcade(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation);
  }
}
