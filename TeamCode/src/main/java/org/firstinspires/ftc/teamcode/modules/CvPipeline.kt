package org.firstinspires.ftc.teamcode.modules

import android.util.Log
import org.openftc.easyopencv.OpenCvPipeline


abstract class CvPipeline : OpenCvPipeline() {

    private val stoneRowMaxWidth = 6
    var detectedSkystonePosition = -1
    var PipelineActive = true
    val width = 1280
    val height = 720

    fun getSkystonePositions(leftMostPosition: Int): IntArray {
        val firstSkystonePosition = detectedSkystonePosition + leftMostPosition
        var secondSkystonePosition: Int

        secondSkystonePosition = firstSkystonePosition + 3
        if (firstSkystonePosition >= stoneRowMaxWidth / 2) {
            secondSkystonePosition = firstSkystonePosition - 3
        }

        val skystonePositions = intArrayOf(firstSkystonePosition, secondSkystonePosition)
        skystonePositions.sort()
        Log.i("Skystone Positions", skystonePositions.toString())
        return skystonePositions
    }

}