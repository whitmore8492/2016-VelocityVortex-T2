/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

//@Autonomous(name = "Prototype", group = "")  // @Autonomous(...) is the other common choice
@Disabled
public class Charlie_Prototype extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private int state = 1;
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private ColorSensor ColorSensor = null;
    private GyroSensor gyro = null;
    private MRI_RangeFinder far = null;
    private Servo beaconServo = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        ColorSensor = hardwareMap.colorSensor.get("colorSensor");
        gyro = hardwareMap.gyroSensor.get("gyroSensor");
        far = new MRI_RangeFinder(hardwareMap.i2cDevice.get("far"));
        beaconServo = hardwareMap.servo.get("bacon");
        gyro.calibrate();
        // eg: Set the  ve motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        // telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        state = 1;
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        int lightAlpha = ColorSensor.alpha();
        telemetry.addData("ColorSensor Alpha: ", lightAlpha);
        telemetry.addData("state: ", state);
        int distance = far.getDistanceCM();
        telemetry.addData("distance Alpha: ", distance);
        if (state == 1) {
            leftMotor.setPower(1);
            rightMotor.setPower(1);

            if (lightAlpha < 85) {
                state = 2;
            }
        }
        if (state == 2) {

            int gyroValue = gyro.getHeading();
            if (gyroValue < 45) {
                leftMotor.setPower(.5);
                rightMotor.setPower(-.5);
            } else {
                leftMotor.setPower(0);
                rightMotor.setPower(0);
                state = 3;
            }
        }
        if (state == 3) {
            if (distance > 15) {

                leftMotor.setPower(.5);
                rightMotor.setPower(.5);
            } else {
                leftMotor.setPower(0);
                rightMotor.setPower(0);
                state = 4;
            }
        }
        if (state == 4) {
            if (lightAlpha < 50) {


                if (distance > 5) {

                    leftMotor.setPower(1);
                    rightMotor.setPower(1);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 5;
                }
            } else {
                beaconServo.setPosition(Settings.beaconRight);
                if (distance > 5) {
                    leftMotor.setPower(1);
                    rightMotor.setPower(1);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 5;
                }
            }

            if (state == 5)
                if (distance < 15) {
                    leftMotor.setPower(-1);
                    rightMotor.setPower(-1);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 6;
                }
            int gyroValue = gyro.getHeading();
            ;
            if (state == 6) {
                if (gyroValue < 160) {

                    leftMotor.setPower(.5);
                    rightMotor.setPower(-.5);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 7;
                }
            }

            if (state == 7) {
                leftMotor.setPower(1);
                rightMotor.setPower(1);

                if (lightAlpha < 85) {
                    state = 8;
                }
            }
            if (state == 8) {

                 gyroValue = gyro.getHeading();
                if (gyroValue < 70) {
                    leftMotor.setPower(.5);
                    rightMotor.setPower(-.5);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 9;
                }
            }
            if (state == 9) {
                if (distance > 15) {

                    leftMotor.setPower(1);
                    rightMotor.setPower(1);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 10;
                }
            }
            if (state == 10) {
                if (lightAlpha < 50) {
                    if (distance > 5) {

                        leftMotor.setPower(1);
                        rightMotor.setPower(1);
                    } else {
                        leftMotor.setPower(0);
                        rightMotor.setPower(0);
                        state = 11;
                    }
                }
            }
            if (state == 12) {
                if (lightAlpha > 50) {

                }
                if (distance < 15) {
                    leftMotor.setPower(-1);
                    rightMotor.setPower(-1);
                } else {
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    state = 13;
                }
            }

//


//        telemetry.addData("Status", "Running: " + runtime.toString());
//
//       int lightAlpha = ColorSensor.alpha();
//       telemetry.addData("ColorSensor Alpha: ", lightAlpha);
//
//        if (lightAlpha > 15) {
//            leftMotor.setPower(1);
//            rightMotor.setPower(.05);
//        } else {
//            leftMotor.setPower(.05);
//            rightMotor.setPower(1);
//       }

//        int gyroValue = gyro.getHeading();
//        if (gyroValue < 80) {
//            leftMotor.setPower(.5);
//            rightMotor.setPower(-.5);
//        } else {
//            leftMotor.setPower(0);
//            rightMotor.setPower(0);
        }
    /*
     * Code to run ONCE after the driver hits STOP
     */


    }
}


