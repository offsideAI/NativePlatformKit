package ai.offside.mobile.android.helper.testlabs.nav.inputfield

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import ai.offside.mobile.android.component.ui.calendar.A11yDayViewDecorator
import ai.offside.mobile.android.component.ui.inputfield.setAccessibilityDelegateForCharCountTextInput
import ai.offside.mobile.android.component.ui.inputfield.setAccessibilityDelegateForErrorTextInput
import ai.offside.mobile.android.component.ui.inputfield.setAccessibilityDelegateForTextInputLayout
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignInputFieldBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Fragment class to Test input fields.
 *
 */
class TestUIRedesignInputFieldFragment : Fragment() {

    lateinit var binding: FragmentTestUiRedesignInputFieldBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignInputFieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    /**
     * Method to initialize text input fields.
     *
     */
    private fun initUI() {
        binding.inputFieldActiveStateLayout.apply {
            inputLayoutActiveField.setAccessibilityDelegateForTextInputLayout(
                inputEditTextActiveField
            )
            inputLayoutFilledField.setAccessibilityDelegateForTextInputLayout(
                inputEditTextFilledField
            )
        }
        binding.inputFieldHiddenStateLayout.apply {
            inputEditTextHiddenState.transformationMethod = PasswordTransformationMethod()
            inputLayoutHiddenState.endIconContentDescription =
                getString(R.string.input_field_ui_hidden_state_pwd_content_description)
        }
        binding.inputFieldErrorStateLayout.apply {
            inputLayoutUnfilledErrorField.setAccessibilityDelegateForErrorTextInput(
                inputEditTextUnfilledErrorField
            )
            if (inputEditTextUnfilledErrorField.isEnabled) {
                inputLayoutUnfilledErrorFieldDivider.visibility = View.VISIBLE
                inputLayoutUnfilledErrorField.apply {
                    isErrorEnabled = true
                    error = getString(R.string.debug_ui_redesign_input_field_error_text)
                }
            }

            inputLayoutErrorField.setAccessibilityDelegateForErrorTextInput(inputEditTextErrorField)
            inputEditTextErrorField.doAfterTextChanged {
                val isValidInput =
                    !it.isNullOrEmpty() && it.matches("^[a-zA-Z]+[a-zA-Z. ]{0,100}\$".toRegex())
                if (isValidInput) {
                    inputLayoutErrorFieldDivider.visibility = View.GONE
                    inputLayoutErrorField.apply {
                        isErrorEnabled = false
                        error = null
                    }
                } else {
                    inputLayoutErrorFieldDivider.visibility = View.VISIBLE
                    inputLayoutErrorField.apply {
                        isErrorEnabled = true
                        error = getString(R.string.debug_ui_redesign_input_field_error_text)
                    }
                }
            }
        }

        //Input Fields With Calendar Icon
        binding.inputFieldWithIconLayout.apply {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"))

            inputLayoutWithIconField.setEndIconOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Send date")
                    .setDayViewDecorator(A11yDayViewDecorator())
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                picker.addOnPositiveButtonClickListener {
                   inputEditTextWithIconField.setText(sdf.format(it))
                }
                picker.show(childFragmentManager, "Calendar")
            }


            inputEditTextWithIconFilledField.setText(sdf.format(MaterialDatePicker.todayInUtcMilliseconds()))
            inputLayoutWithIconFilledField.setEndIconOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Send date")
                    .setDayViewDecorator(A11yDayViewDecorator())
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                picker.addOnPositiveButtonClickListener {
                    inputEditTextWithIconFilledField.setText(sdf.format(it))
                }
                picker.show(childFragmentManager, "Calendar")
            }
        }

        binding.inputFieldTextWrapLayout.apply {
            inputLayoutTextWrapField.setAccessibilityDelegateForTextInputLayout(
                inputEditTextTextWrapField
            )
            inputLayoutTextWrapWithCountField.apply {
                setAccessibilityDelegateForCharCountTextInput(inputEditTextTextWrapWithCountField)
                inputEditTextTextWrapWithCountField.setOnEditFieldActionListener()
            }
        }
        binding.inputFieldLastLayout.apply {
            inputLayoutLastField.setAccessibilityDelegateForTextInputLayout(inputEditTextLastField)
        }
        binding.inputFieldEmptyWhiteLayout.apply {
            inputLayoutEmptyWhite.setAccessibilityDelegateForTextInputLayout(inputEditTextEmptyWhite)
        }
        binding.inputFieldCounterLayout.apply {
            inputTextFieldEmptyCounterLayout.apply {
                setAccessibilityDelegateForCharCountTextInput(inputEditTextFieldEmptyCounter)
                inputEditTextFieldEmptyCounter.setOnEditFieldActionListener()
            }
            inputTextWrapFieldCounterLayout.apply {
                setAccessibilityDelegateForCharCountTextInput(inputEditTextWrapFieldCounter)
                inputEditTextWrapFieldCounter.setOnEditFieldActionListener()
            }
            inputTextFieldFilledActiveCounterLayout.apply {
                setAccessibilityDelegateForCharCountTextInput(inputEditTextFieldFilledActiveCounter)
                inputEditTextFieldFilledActiveCounter.setOnEditFieldActionListener()
            }
        }
    }
}