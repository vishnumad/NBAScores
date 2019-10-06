package io.github.vishnumad.nbascores.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import io.github.vishnumad.nbascores.R
import kotlinx.android.synthetic.main.merge_error_layout.view.*


class ErrorView : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.merge_error_layout, this, true)

        orientation = VERTICAL
        gravity = Gravity.CENTER
    }

    fun setOnReloadButtonClickListener(callback: (View) -> Unit) {
        error_reload_button.setOnClickListener(callback)
    }

    fun setError(errorMessage: String) {
        error_text_view.text = errorMessage
    }
}