package com.example.metproject.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView

class FadingImageViewKotlin : AppCompatImageView {
    private var mFadeRight = false
    private var mFadeLeft = false
    private var mFadeTop = false
    private var mFadeBottom = false
    private var c: Context

    constructor(
        c: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(c, attrs, defStyle) {
        this.c = c
        init()
    }

    constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
        this.c = c
        init()
    }

    constructor(c: Context) : super(c) {
        this.c = c
        init()
    }

    private fun init() {
        // Enable horizontal fading
        this.isHorizontalFadingEdgeEnabled = true
        this.isVerticalFadingEdgeEnabled = true
        // Apply default fading length
        setEdgeLength(50)
        // Apply default side
        setFadeBottom(true)
    }

    fun setFadeRight(fadeRight: Boolean) {
        mFadeRight = fadeRight
    }

    fun setFadeLeft(fadeLeft: Boolean) {
        mFadeLeft = fadeLeft
    }

    fun setFadeTop(fadeTop: Boolean) {
        mFadeTop = fadeTop
    }

    fun setFadeBottom(fadeBottom: Boolean) {
        mFadeBottom = fadeBottom
    }

    fun setEdgeLength(length: Int) {
        setFadingEdgeLength(getPixels(length))
    }

    override fun getTopFadingEdgeStrength(): Float {
        return if (mFadeTop) 1.0f else 0.0f
    }

    override fun getBottomFadingEdgeStrength(): Float {
        return if (mFadeBottom) 1.0f else 0.0f
    }

    override fun getLeftFadingEdgeStrength(): Float {
        return if (mFadeLeft) 1.0f else 0.0f
    }

    override fun getRightFadingEdgeStrength(): Float {
        return if (mFadeRight) 1.0f else 0.0f
    }

    override fun hasOverlappingRendering(): Boolean {
        return true
    }

    public override fun onSetAlpha(alpha: Int): Boolean {
        return false
    }

    private fun getPixels(dipValue: Int): Int {
        val r = c.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue.toFloat(), r.displayMetrics
        ).toInt()
    }
}
