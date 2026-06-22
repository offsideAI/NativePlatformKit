package ai.offside.mobile.android.helper.testlabs.nav.inputfield

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetViewModel
import ai.offside.mobile.android.component.ui.inputfield.AmountBottomSheetDataItem
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldConstant
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldFilter
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldTextWatcher
import ai.offside.mobile.android.component.ui.inputfield.InputFieldUtils
import ai.offside.mobile.android.component.ui.inputfield.getTwoLineDropDownAccessibilityText
import ai.offside.mobile.android.component.ui.inputfield.removeCustomErrorMessage
import ai.offside.mobile.android.component.ui.inputfield.setAccessibilityDelegateForAmountInput
import ai.offside.mobile.android.component.ui.inputfield.setAccessibilityDelegateForErrorAmountInput
import ai.offside.mobile.android.component.ui.inputfield.setCustomErrorMessage
import ai.offside.mobile.android.component.ui.inputfield.setDropdownAccessibilityText
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignAmountInputFieldBinding
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale


/**
 * Fragment class to Test amount input fields.
 *
 */
class TestUIRedesignAmountInputFieldFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignAmountInputFieldBinding
    private lateinit var navController: NavController
    private val bottomSheetViewModel: BottomSheetViewModel by viewModels({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignAmountInputFieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        initInputFields()

        //Observe data from Amount bottom sheet fragment
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<AmountBottomSheetDataItem>(
            AmountInputFieldConstant.selectedAmountBottomSheetKey
        )?.observe(viewLifecycleOwner) { selectedAmount ->
            val formattedReceivedAmount: String =
                NumberFormat.getCurrencyInstance(Locale.US).format(selectedAmount.amount)
            binding.amountInputFieldDropdown.apply {
                setText(formattedReceivedAmount)
            }
            binding.amountInputFieldDropdownSecondaryInfo.apply {
                text = selectedAmount.label
            }
            binding.amountInputFieldDropdownLayout.apply {
                val accessibilityText = getTwoLineDropDownAccessibilityText(
                    requireContext().getString(R.string.debug_ui_redesign_amount_a11y_desc).plus(requireContext().getString(R.string.debug_ui_redesign_asterisk_a11y_desc)),
                    formattedReceivedAmount,
                    selectedAmount.label
                )
                setDropdownAccessibilityText(accessibilityText)
            }
        }

        //Live data observer for bottom sheet dismiss event
        bottomSheetViewModel.dismiss.observe(viewLifecycleOwner) {
        }
        //Live data observer for selected amount from bottom sheet
        bottomSheetViewModel.bottomSheetData.observe(viewLifecycleOwner) {
            if (it is AmountBottomSheetDataItem) {
                Toast.makeText(context, "${it.label} ${it.amount}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Function to open amount bottom sheet and clear focus from selected [TextInputEditText]
     */
    private fun openAmountBottomSheet() {
        val currentFocusedView = requireActivity().currentFocus
        if (currentFocusedView is TextInputEditText) {
            currentFocusedView.clearFocus()
        }
        InputFieldUtils.hideKeyboard(requireContext(), binding.root)
        navController.navigate(R.id.action_testUIRedesignAmountInputFieldFragment_to_amountInputFieldBottomSheetFragment)
    }

    /**
     * Method to initialize amount input fields.
     * Note: If design changes to show up & down arrow to represent open & close state of drop-down then to re-use existing
     * vector drawable icon - "ic_bottom_sheet_arrow.xml" use "group" tag to rotate arrow direction as below:
     *
     * <group
     *       android:rotation="180"
     *       android:translateX="24"
     *       android:translateY="24">
     *        <path .../>
     * </group>
     *
     * Ref: https://stackoverflow.com/questions/55038379/android-rotate-vector-image-to-90-degree
     */
    private fun initInputFields() {
        val defaultAmountText = requireContext().getString(R.string.debug_ui_redesign_amount_input_default_text)

        // Scenario-1: Amount Input field
        val scenarioTextWatcher1 = AmountInputFieldTextWatcher(binding.amountInputField,
            object : AmountInputFieldTextWatcher.AmountInputTextChangeListener {
                override fun afterTextChange(s: Editable?, formattedInput: BigDecimal?) {
                }
            })
        binding.amountInputField.apply {
            filters += AmountInputFieldFilter()
            addTextChangedListener(scenarioTextWatcher1)
            setText(defaultAmountText)
            setOnEditFieldActionListener()
        }
        binding.amountInputFieldLayout.setAccessibilityDelegateForAmountInput(binding.amountInputField)

        val activeFieldTextWatcher = AmountInputFieldTextWatcher(binding.amountInputActiveField,
            object : AmountInputFieldTextWatcher.AmountInputTextChangeListener {
                override fun afterTextChange(s: Editable?, formattedInput: BigDecimal?) {
                }
            })
        binding.amountInputFieldActiveLayout.apply {
            binding.amountInputActiveField.apply {
                filters += AmountInputFieldFilter()
                addTextChangedListener(activeFieldTextWatcher)
                setText(defaultAmountText)

                setEndIconOnClickListener {
                    setText(defaultAmountText)
                }
                setOnEditFieldActionListener()
            }
            setAccessibilityDelegateForAmountInput(binding.amountInputActiveField)
        }

        // Scenario-2: Amount Input field with sub text
        val scenarioTextWatcher2 = AmountInputFieldTextWatcher(binding.amountInputFieldToolTipText,
            object : AmountInputFieldTextWatcher.AmountInputTextChangeListener {
                override fun afterTextChange(s: Editable?, formattedInput: BigDecimal?) {
                }
            })
        binding.amountInputFieldToolTipText.apply {
            filters += AmountInputFieldFilter()
            addTextChangedListener(scenarioTextWatcher2)
            setText(defaultAmountText)
            setOnEditFieldActionListener()
        }

        binding.amountInputFieldToolTipLayout.apply {
            setAccessibilityDelegateForAmountInput(binding.amountInputFieldToolTipText)
            setEndIconOnClickListener {
                Toast.makeText(context, getString(R.string.debug_ui_redesign_amount_input_subtext), Toast.LENGTH_SHORT).show()
            }
        }

        // Scenario-3: Amount Input field with error state handling
        binding.amountInputFieldErrorStateLayout.setAccessibilityDelegateForAmountInput(binding.amountInputFieldErrorState)
        val scenarioTextWatcher3 = AmountInputFieldTextWatcher(binding.amountInputFieldErrorState,
            object : AmountInputFieldTextWatcher.AmountInputTextChangeListener {
                override fun afterTextChange(s: Editable?, formattedInput: BigDecimal?) {
                    handleAmountInputForErrorState(formattedInput)
                }
            })
        binding.amountInputFieldErrorState.apply {
            setText(defaultAmountText)
            setSelection(defaultAmountText.length)
            filters += AmountInputFieldFilter()
            addTextChangedListener(scenarioTextWatcher3)
        }

        // Scenario-4: Amount drop-down
        val dropdownAccessibilityText = binding.amountInputFieldDropdownLayout.getTwoLineDropDownAccessibilityText(
            requireContext().getString(R.string.debug_ui_redesign_amount_a11y_desc).plus(requireContext().getString(R.string.debug_ui_redesign_asterisk_a11y_desc)),
            defaultAmountText,
            requireContext().getString(R.string.debug_ui_redesign_amount_input_helper_text)
        )
        binding.amountInputFieldDropdownLayout.apply {
            setOnClickListener {
                openAmountBottomSheet()
            }
            setDropdownAccessibilityText(dropdownAccessibilityText)
        }

        binding.amountInputFieldDropdown.setOnClickListener {
            binding.amountInputFieldDropdownLayout.performClick()
        }

        //Scenario-5: Disabled Amount Input Field
        binding.amountInputFieldsDisabled.apply {
            amountInputFieldDisabledLayout.apply {
                isEnabled = false
                setAccessibilityDelegateForAmountInput(amountInputFieldDisabledText)
            }
            amountInputFieldDisabledDropdownLayout.apply {
                isEnabled = false
                amountInputFieldDisabledDropdownSecondaryInfo.importantForAccessibility =
                    View.IMPORTANT_FOR_ACCESSIBILITY_NO
                amountInputFieldDisabledDropdownText.importantForAccessibility =
                    View.IMPORTANT_FOR_ACCESSIBILITY_NO
                isFocusableInTouchMode = true
                isFocusable = true
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                contentDescription = dropdownAccessibilityText
            }
        }
    }

    /**
     * Handle amount input for error state.
     *
     * @param formattedInput formatted input - amount
     */
    private fun handleAmountInputForErrorState(formattedInput: BigDecimal?) {
        formattedInput?.let {
            if (formattedInput <= BigDecimal(10)) {

                binding.amountInputFieldErrorStateLayout.apply {
                    setCustomErrorMessage(
                        binding.amountInputFieldErrorText,
                        resources.getString(R.string.debug_ui_redesign_amount_input_error_text)
                    )
                    binding.amountInputFieldErrorStateLayout.setAccessibilityDelegateForErrorAmountInput(
                        binding.amountInputFieldErrorState,
                        binding.amountInputFieldErrorText
                    )
                }
            } else {
                binding.amountInputFieldErrorStateLayout.removeCustomErrorMessage(binding.amountInputFieldErrorText)
                binding.amountInputFieldErrorStateLayout.setAccessibilityDelegateForAmountInput(binding.amountInputFieldErrorState)
            }
        }
    }

    companion object {

        /**
         * Method to create a wew instance of this fragment.
         *
         * @return [TestUIRedesignAmountInputFieldFragment]
         */
        @JvmStatic
        fun newInstance(): TestUIRedesignAmountInputFieldFragment =
            TestUIRedesignAmountInputFieldFragment()
    }
}