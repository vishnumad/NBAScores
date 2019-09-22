package io.github.vishnumad.nbascores.ui.common

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ListView
import androidx.core.view.updateLayoutParams

/**
 * A custom sub-class of [ListView] which is not scrollable. All items in the ListView are shown at the same time.
 * Items are not clickable
 */
class NoScrollListView : ListView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private var prevCount = 0

    init {
        /* Disable clicks and set the selection background to a transparent drawable */
        isClickable = false
        isLongClickable = false
        setSelector(android.R.color.transparent)
    }

    override fun onDraw(canvas: Canvas?) {
        // Resize the height of the ListView to show all items without scrolling.
        // We assume that all the ListView items are of equal height
        val numItems = count
        if (numItems != prevCount) {
            prevCount = numItems
            updateLayoutParams {
                height = if (numItems > 0) {
                    getChildAt(0).height * numItems
                } else {
                    // Set the height to 0 if there are somehow 0 or fewer items
                    0
                }
            }
        }

        super.onDraw(canvas)
    }
}