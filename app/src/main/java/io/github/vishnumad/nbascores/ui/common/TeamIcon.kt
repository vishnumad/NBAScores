package io.github.vishnumad.nbascores.ui.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import io.github.vishnumad.nbascores.R
import kotlinx.android.synthetic.main.team_icon_view.view.*

class TeamIcon(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    companion object {
        private const val DEFAULT_TEXT_COLOR = Color.WHITE
        private const val DEFAULT_TEAM_ABBREV = "..."
    }

    private var teamAbbrev: String

    @ColorInt
    private var textColor: Int

    @ColorInt
    private var bgColor: Int = ContextCompat.getColor(context, R.color.colorAccent)

    init {
        context
            .theme
            .obtainStyledAttributes(attrs, R.styleable.TeamIcon, 0, 0)
            .apply {
                try {
                    teamAbbrev = getString(R.styleable.TeamIcon_teamAbbrev) ?: DEFAULT_TEAM_ABBREV
                    textColor = getColor(R.styleable.TeamIcon_textColor, DEFAULT_TEXT_COLOR)
                    bgColor = getColor(R.styleable.TeamIcon_backgroundColor, bgColor)
                } finally {
                    recycle()
                }
            }

        // Inflate the layout into the card view
        inflate(context, R.layout.team_icon_view, this)

        setupBackground()
        setupTextView()
    }

    fun setTeam(teamAbbrev: String, @ColorInt bgColor: Int) {
        this.teamAbbrev = teamAbbrev
        this.bgColor = bgColor

        setupBackground()
        setupTextView()
    }

    private fun setupBackground() {
        setCardBackgroundColor(bgColor)
        cardElevation = 0f
    }

    private fun setupTextView() {
        abbrev_text_view.text = teamAbbrev
        abbrev_text_view.setTextColor(textColor)
    }
}