package com.example.firstkotlinapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class FloatingRingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint = Paint().apply {
        color = Color.BLUE  // Ring color
        strokeWidth = 10f
        style = Paint.Style.STROKE  // Hollow circle
        isAntiAlias = true
    }

    private var ringX = 300f
    private var ringY = 300f
    private var ringRadius = 150f

    private var lastTouchX = 0f
    private var lastTouchY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the ring at the current position
        canvas.drawCircle(ringX, ringY, ringRadius, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                // Optional: Scale up the ring when touched
                animateRingScale(1.4f)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastTouchX
                val dy = event.y - lastTouchY
                ringX += dx
                ringY += dy
                lastTouchX = event.x
                lastTouchY = event.y
                invalidate()  // Redraw the view with the new position
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Optional: Scale back the ring when released
                animateRingScale(1.0f)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun animateRingScale(scale: Float) {
        val animator = android.animation.ValueAnimator.ofFloat(1.0f, scale)
        animator.duration = 300  // Animation duration
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            ringRadius = 150 * animatedValue  // Adjust the ring's size
            invalidate()
        }
        animator.start()
    }
}

