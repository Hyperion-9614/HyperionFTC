package org.firstinspires.ftc.teamcode.opmodes;

import com.hyperion.motion.math.Vector2D;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Hardware;
import org.firstinspires.ftc.teamcode.core.Motion;

/**
 * Main TeleOp
 * Select op mode then start
 * Controller map: http://team358.org/files/programming/ControlSystem2009-/XBoxControlMapping.jpg
 */

@TeleOp
public class TELE_Main extends LinearOpMode {

    private Hardware hw;
    private boolean isHtStarted;
    private double preHtTheta;

    @Override
    public void runOpMode() {
        hw = new Hardware(this);
        hw.initOpMode("tele");
        while (!isStopRequested() && (!isStarted() || (opModeIsActive() && !hw.isRunning))) {


        }
        executeLoop();
        hw.end();
    }

    public void executeLoop() {
        while (opModeIsActive() && !isStopRequested()) {
            /*
             * GAMEPAD 1
             * LEFT JOYSTICK : Translation in direction of joystick, relative to robot
             * RIGHT JOYSTICK : Pivot in direction of joystick, relative to robot
             */
            Vector2D vel = new Vector2D(gamepad1.left_stick_x, -gamepad1.left_stick_y, true);
            double rot = gamepad1.right_stick_x;
            if (rot == 0) {
                isHtStarted = false;
            } else if (rot != 0 && !isHtStarted) {
                preHtTheta = Motion.robot.theta;
                isHtStarted = true;
            }
            if (isHtStarted) {
                vel.rotate(preHtTheta - Motion.robot.theta);
            }
            Motion.setDrive(vel, rot);
        }
    }

}