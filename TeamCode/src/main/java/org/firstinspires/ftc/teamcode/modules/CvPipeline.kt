package org.firstinspires.ftc.teamcode.modules

import org.openftc.easyopencv.OpenCvPipeline


abstract class CvPipeline : OpenCvPipeline() {

    private val stoneRowMaxWidth = 6
    var skystoneDetected = false
    var detectedSkystonePosition = -1
    val width = 1280
    val height = 960

    fun getSkystonePositions(leftMostPosition: Int): IntArray {
        val firstSkystonePosition = detectedSkystonePosition + leftMostPosition
        var secondSkystonePosition: Int

        secondSkystonePosition = firstSkystonePosition + 3
        if (firstSkystonePosition >= stoneRowMaxWidth / 2) {
            secondSkystonePosition = firstSkystonePosition - 3
        }

        val skystonePositions = intArrayOf(firstSkystonePosition, secondSkystonePosition)
        skystonePositions.sort()

        skystoneDetected = true

        return skystonePositions
    }
}