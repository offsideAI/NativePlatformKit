package ai.offside.mobile.android.helper.testlabs.nav.modals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ai.offside.mobile.android.component.ui.calendar.A11yDayViewDecorator
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentRedesignUiCalendarDemoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


/**
 * Reference Material 3 Date-Picker
 * https://github.com/material-components/material-components-android/blob/master/docs/components/DatePicker.md#design-api-documentation
 * https://m3.material.io/components/date-pickers/specs
 * https://m2.material.io/components/date-pickers/android#anatomy-and-key-properties
 *
 */
class TestUIRedesignCalendarDemoFragment: Fragment(R.layout.fragment_redesign_ui_calendar_demo) {

    private var _binding: FragmentRedesignUiCalendarDemoBinding? = null
    private val binding: FragmentRedesignUiCalendarDemoBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRedesignUiCalendarDemoBinding.bind(view)

        binding.datePickerActions.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Send date")
                .setDayViewDecorator(A11yDayViewDecorator())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            picker.addOnPositiveButtonClickListener {
                displayDateSelection(it)
            }
            picker.show(childFragmentManager, "Calendar")

        }

        binding.calendarForwardActions.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Send date")
                .setDayViewDecorator(A11yDayViewDecorator())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build())
                .build()

            picker.addOnPositiveButtonClickListener {
                displayDateSelection(it)
            }
            picker.show(childFragmentManager, "Calendar")

        }

        binding.calendarBackwardActions.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Birth date")
                .setDayViewDecorator(A11yDayViewDecorator())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now()).build())
                .build()
            picker.addOnPositiveButtonClickListener {
                displayDateSelection(it)
            }
            picker.show(childFragmentManager, "Calendar")

        }

        binding.calendarRangeActions.setOnClickListener {

            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = today
            calendar[Calendar.YEAR] = 2010
            val startDate = calendar.timeInMillis

            calendar.timeInMillis = today
            calendar[Calendar.YEAR] = 2040
            val endDate = calendar.timeInMillis

            val constraints: CalendarConstraints = CalendarConstraints.Builder()
                .setOpenAt(MaterialDatePicker.todayInUtcMilliseconds())
                .setStart(startDate)
                .setEnd(endDate)
                .build()

            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Send date")
                .setDayViewDecorator(A11yDayViewDecorator())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraints)
                .build()
            picker.addOnPositiveButtonClickListener {
                displayDateSelection(it)
            }
            picker.show(childFragmentManager, "Calendar")
        }

    }

    /**
     * @param date : Long value of selected date
     * format the selected date and display it on the View
     */
    private fun displayDateSelection(date: Long) {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        binding.dateSelection.text = sdf.format(date)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}