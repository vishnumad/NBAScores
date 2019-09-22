package io.github.vishnumad.nbascores.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import io.github.vishnumad.nbascores.R


class ErrorView : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private val errorMessageView: TextView
    private val reloadButton: Button

    init {
        LayoutInflater.from(context).inflate(R.layout.merge_error_layout, this, true)

        orientation = VERTICAL
        gravity = Gravity.CENTER

        errorMessageView = findViewById(R.id.error_text_view)
        reloadButton = findViewById(R.id.error_reload_button)
    }

    fun setOnReloadButtonClickListener(callback: (View) -> Unit) {
        reloadButton.setOnClickListener(callback)
    }

    fun setError(errorMessage: String) {
        errorMessageView.text = errorMessage
    }
}