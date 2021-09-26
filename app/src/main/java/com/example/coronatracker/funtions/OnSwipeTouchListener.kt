package com.example.coronatracker.funtions

import android.content.Context
import android.view.View.OnTouchListener
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View
import kotlin.math.abs

class OnSwipeTouchListener(context: Context?) : OnTouchListener {
    private val detector: GestureDetector
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    fun onSwipeLeft() {}
    fun onSwipeRight() {}
    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val distanceX = e2.x - e1.x
            val distanceY = e2.y - e1.y
            if (abs(distanceX) > abs(distanceY) && (abs(distanceX)
                        > SWIPE_DISTANCE_THRESHOLD) && abs(velocityX) > Companion.SWIPE_VELOCITY_THRESHOLD
            ) {
                if (distanceX > 0) onSwipeRight() else onSwipeLeft()
                return true
            }
            return false
        }


    }

    init {
        detector = GestureDetector(context, GestureListener())
    }
    companion object {
        private const val SWIPE_DISTANCE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }
}