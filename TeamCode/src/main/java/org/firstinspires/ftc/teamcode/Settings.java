package org.firstinspires.ftc.teamcode;

/**
 * Created by mg15 on 10/14/2016.
 */

public class Settings {
    public static int stageRedCorner1Forward = 1;
    public static int stageRedCorner2TurnLeft = 2;
    public static int stageRedCorner3LineFollow = 3;
    public static int stageRedCorner4AlignBucket =4;
    public static int bucketAngle=300;
    public static int stagecorner1shoot = 1;
    public static int stageDriveForwardcorner2 = 2;
    public static int stageStoppingcorner3 = 3;
    public static int stagemiddle1shoot = 1;
    public static int stageDriveForwardmiddle2 = 2;
    public static int stageStoppingmiddle3 = 3;
    public static int stageShooterSpinUp = 0;
    public static double normalDriveSpeed = 0.75;
    public static int cornerDriveDistance = 155;
    public static int middleDriveDistance = 140;
    public static int redTapeLightVal = 99;
    public static double GearRatio = 2 / 1; //motor revoulutions /wheel revoulutions
    public final static int ticsPerRevoulution = 1440;
    public final static double wheelCircumfence = 8*Math.PI; //wheel diameter * PI
    public static double TicsPerCM = (GearRatio *ticsPerRevoulution)/wheelCircumfence;
    public static int RedTapeAngle = 315;
    public static double redTapeDistance= 60;
    public static int firstLaunch = 4;
    public static int firstReset = 5;
    public static int secondLaunch = 7;
    public static int secondReset = 8;
    public static int turnOffShooter = 9;
    public static double launch = .6;
    public static double reset = 0;
    public static double shooterSpeedTeleOP = .25;
    public static double spinnerShooterAuto = .3;
    public static double bestShooterSpeed = 120 * Math.PI;
    public static int beaconRight = 1;
    public static int beaconLeft = 0;
    public static int stage1FIRE = 1;
    public static int stage2Charge = 2;
    public static int stage3Stop = 3;
    public static double driveSpeed = 1;
    public static double spinnerShooterMiddle = .25;
    public static int shooterTicksPerRev = 7;
    public static int shooterMotorMaxRPM = 6000;
    public static double posTriggerTol = .0;
    public static double Tics2CM (int tics){
        return tics / TicsPerCM;

    }
}