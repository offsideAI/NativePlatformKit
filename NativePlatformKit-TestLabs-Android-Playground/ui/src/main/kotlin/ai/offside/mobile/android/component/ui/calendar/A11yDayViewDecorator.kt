package ai.offside.mobile.android.component.ui.calendar

import android.content.Context
import android.widget.Button
import com.google.android.material.datepicker.DayViewDecorator
import ai.offside.mobile.android.component.ui.R
import kotlinx.parcelize.Parcelize

@Parcelize
class A11yDayViewDecorator : DayViewDecorator() {
    override fun describeContents(): Int {
        return 0
    }

    /**
     * @param context
     * @param year
     * @param month
     * @param day
     * @param selected
     * @param originalContentDescription
     *
     * As per the document https://m3.material.io/components/date-pickers/accessibility the year should be announced
     * https://jira.offsideint.net/browse/MBF-193 validation found that the year is not announced, hence the fix provided with CustomDayViewDecorator
     *  1. Add the year to the day content description
     *  2. [valid] and [selected] : Selected / Not Selected added to the content description, if the state is not already called out from material3 calendar
     *  3. [valid] Add Button to the day text, A11y expectation is to call-out as Button where as in Calendar it is MaterialTextView
     */
    override fun getContentDescription(
        context: Context,
        year: Int,
        month: Int,
        day: Int,
        valid: Boolean,
        selected: Boolean,
        originalContentDescription: CharSequence?
    ): CharSequence? {
        var contentDescription: String = originalContentDescription.toString()
        // Add year to the day text
        if (contentDescription.contains(year.toString()).not()) {
            contentDescription = originalContentDescription.toString().plus(TEXT_SEPARATOR).plus(year)
        }
        // add the selected / not selected to the content description
        contentDescription = when {
            valid && selected -> context.getString(R.string.calendar_day_selected_content_description).plus(TEXT_SEPARATOR).plus(contentDescription)
            valid && !selected -> context.getString(R.string.calendar_day_not_selected_content_description).plus(TEXT_SEPARATOR).plus(contentDescription)
            else -> contentDescription
        }
        // Add Button to the day text, A11y expectation is to call-out as Button where as in Calendar it is MaterialTextView
        contentDescription = contentDescription.plus(TEXT_SEPARATOR).plus(Button::class.simpleName)

        return super.getContentDescription(
            context,
            year,
            month,
            day,
            valid,
            selected,
            contentDescription
        )
    }
    companion object {
        private const val TEXT_SEPARATOR = ", "
    }
}