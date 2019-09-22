package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.ui.common.ContentCardView
import kotlinx.android.synthetic.main.details_last_play_layout.view.*

class LastPlayView : ContentCardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.details_last_play_layout, this, true)
    }

    fun setLastPlay(clock: String, description: String) {
        last_play_text.text = "$clock - $description"
    }
}