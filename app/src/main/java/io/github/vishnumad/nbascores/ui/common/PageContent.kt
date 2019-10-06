package io.github.vishnumad.nbascores.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ViewAnimator

/**
 * A [View] that makes showing and hiding content easier. Switches between child [View]s.
 * Only one child [View] will be visible at a time.
 */
class PageContent : ViewAnimator {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val defaultDuration: Long = 150

    init {
        // Sets the default animations
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = defaultDuration
        }

        val fadeOut = AlphaAnimation(1f, 0f).apply {
            duration = defaultDuration
        }

        inAnimation = fadeIn
        outAnimation = fadeOut
    }

    /**
     * Shows the child view if it is not already visible
     */
    fun show(view: View) {
        val index = indexOfChild(view)
        require(index != -1) { "View must be a child of the PageContent" }

        if (displayedChild != index)
            displayedChild = index
    }

    /**
     * Set custom animations for the view changing effect
     */
    fun setAnimations(inAnim: Animation, outAnim: Animation) {
        inAnimation = inAnim
        outAnimation = outAnim
    }
}
