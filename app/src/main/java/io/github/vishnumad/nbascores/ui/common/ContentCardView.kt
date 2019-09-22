package io.github.vishnumad.nbascores.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.utils.convertDpToPx

open class ContentCardView : MaterialCardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val CARD_MARGIN_DP = 4f
        private const val CARD_CORNER_RADIUS_DP = 2f
        private const val CARD_ELEVATION_DP = 0f
    }

    init {
        cardElevation = context.convertDpToPx(CARD_ELEVATION_DP)
        radius = context.convertDpToPx(CARD_CORNER_RADIUS_DP)

        val backgroundColor = ContextCompat.getColor(context, R.color.card_background)
        setCardBackgroundColor(backgroundColor)

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val margin = context.convertDpToPx(CARD_MARGIN_DP).toInt()
        with(layoutParams as MarginLayoutParams) {
            topMargin = margin
            leftMargin = margin
            rightMargin = margin
            bottomMargin = 0
        }
    }
}