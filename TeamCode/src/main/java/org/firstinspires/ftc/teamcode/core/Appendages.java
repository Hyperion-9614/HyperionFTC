package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Manages all appendages (servos, arms, etc.) on robot
 */

public class Appendages {

    public Hardware hardware;

    public String compWheelsStatus = "";
    public String foundationMoverStatus = "";
    public String chainBarStatus = "";
    public String clawStatus = "";
    public String autoClawSwingStatus = "";
    public String autoClawGripStatus = "";

    public Appendages(Hardware hardware) {
        this.hardware = hardware;

        resetVerticalSlideEncoders();

        hardware.compWheelsL.setDirection(DcMotorSimple.Direction.REVERSE);
        hardware.foundationMoverL.setDirection(Servo.Direction.REVERSE);
        hardware.chainBarL.setDirection(Servo.Direction.REVERSE);
        hardware.autoClawSwing.setDirection(Servo.Direction.REVERSE);

        setCompWheelsStatus("stop");
        setFoundationMoverStatus("up");
        setChainBarStatus("in");
        setClawStatus("open");
        setAutoClawSwingStatus("up");
        setAutoClawGripStatus("open");
    }

    //////////////////////// APPENDAGE CONTROL //////////////////////////

    public void setVerticalSlidePower(double power) {
        hardware.vertSlideL.setPower(power);
        hardware.vertSlideR.setPower(power);
    }

    public void setVerticalSlideMode(DcMotor.RunMode mode) {
        hardware.vertSlideL.setMode(mode);
        hardware.vertSlideR.setMode(mode);
    }

    public void setVerticalSlideTarget(int target) {
        hardware.vertSlideL.setTargetPosition(target);
        hardware.vertSlideR.setTargetPosition(target);
    }

    public void setVerticalSlidePosition(int target) {
        setVerticalSlideTarget(target);
        setVerticalSlideMode(DcMotor.RunMode.RUN_TO_POSITION);

        setVerticalSlidePower(1);
        ElapsedTime timer = new ElapsedTime();
        while ((hardware.vertSlideL.isBusy() || hardware.vertSlideR.isBusy()) && timer.milliseconds() <= 4000
                && (hardware.context.gamepad1.left_stick_x == 0 && hardware.context.gamepad1.left_stick_y == 0 && hardware.context.gamepad1.right_stick_x == 0)) {
            hardware.motion.localizer.update();
            hardware.unimetry.update();
        }

        setVerticalSlidePower(0);
        setVerticalSlideMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void resetVerticalSlideEncoders() {
        setVerticalSlideMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setVerticalSlideMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setCompWheelsPower(double power) {
        hardware.compWheelsL.setPower(power);
        hardware.compWheelsR.setPower(power);
    }

    public void setCompWheelsStatus(String inStopOut) {
        compWheelsStatus = inStopOut.toLowerCase();
        double power = 0;
        switch (compWheelsStatus) {
            case "in":
                power = -1;
                break;
            case "out":
                power = 1;
                break;
        }
        setCompWheelsPower(power);
    }

    public void setFoundationMoverStatus(String downUp) {
        foundationMoverStatus = downUp.toLowerCase();
        if (foundationMoverStatus.equals("down")) {
            hardware.foundationMoverL.setPosition(1.0);
            hardware.foundationMoverR.setPosition(1.0);
        } else {
            hardware.foundationMoverL.setPosition(0);
            hardware.foundationMoverR.setPosition(0);
        }
    }

    public void setChainBarPosition(double position) {
        hardware.chainBarL.setPosition(position);
        hardware.chainBarR.setPosition(position);
        hardware.context.sleep(500);
    }

    public void setChainBarStatus(String upInOut) {
        chainBarStatus = upInOut.toLowerCase();
        if (chainBarStatus.equals("up")) {
            setChainBarPosition(0.8);
        } else if (chainBarStatus.equals("in")) {
            setChainBarPosition(0.95);
        } else if (chainBarStatus.equals("out")) {
            setChainBarPosition(0.5);
        }
    }

    public void cycleChainBar() {
        switch (chainBarStatus) {
            case "up":
                setChainBarStatus("in");
                break;
            case "in":
                setChainBarStatus("out");
                break;
            case "out":
                setChainBarStatus("up");
                break;
        }
    }

    public void setClawStatus(String openClosed) {
        clawStatus = openClosed.toLowerCase();
        if (clawStatus.equals("open")) {
            hardware.claw.setPower(1.0);
        } else {
            hardware.claw.setPower(-1.0);
        }
        hardware.context.sleep(500);
    }

    public void setAutoClawSwingStatus(String downUp) {
        autoClawSwingStatus = downUp.toLowerCase();
        if (autoClawSwingStatus.equals("down")) {
            hardware.autoClawSwing.setPosition(0);
        } else {
            hardware.autoClawSwing.setPosition(0.5);
    }
        hardware.context.sleep(500);
    }

    public void setAutoClawGripStatus(String openClosed) {
        autoClawGripStatus = openClosed.toLowerCase();
        if (autoClawGripStatus.equals("open")) {
            hardware.autoClawGrip.setPower(1.0);
        } else {
            hardware.autoClawGrip.setPower(0);
        }
        hardware.context.sleep(500);
    }

}
