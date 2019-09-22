package io.github.vishnumad.nbascores.ui.schedule

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.ImageButton
import io.github.vishnumad.nbascores.R

class DateSwitcherView : FrameLayout, DatePickerDialog.OnDateSetListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private val dateSelectorButton: Button
    private val nextDateButton: ImageButton
    private val prevDateButton: ImageButton

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private var listener: Listener? = null

    init {
        inflate(context, R.layout.date_switcher_view_layout, this)

        dateSelectorButton = findViewById(R.id.select_date_button)
        nextDateButton = findViewById(R.id.button_next)
        prevDateButton = findViewById(R.id.button_prev)

        dateSelectorButton.setOnClickListener {
            openDatePicker()
        }

        nextDateButton.setOnClickListener { listener?.onNextButtonClick() }
        prevDateButton.setOnClickListener { listener?.onPrevButtonClick() }
    }

    fun setDate(state: DateSwitcherState) {
        dateSelectorButton.text = state.label
        year = state.year
        month = state.month - 1
        day = state.day
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    private fun openDatePicker() {
        val picker = DatePickerDialog(context, R.style.DatePickerDialog, this, year, month, day)
        picker.show()
    }

    override fun onDateSet(view: DatePicker?, setYear: Int, setMonth: Int, setDay: Int) {
        listener?.onDateSelected(setYear, setMonth + 1, setDay)
    }

    interface Listener {
        fun onDateSelected(year: Int, month: Int, day: Int)
        fun onNextButtonClick()
        fun onPrevButtonClick()
    }
}