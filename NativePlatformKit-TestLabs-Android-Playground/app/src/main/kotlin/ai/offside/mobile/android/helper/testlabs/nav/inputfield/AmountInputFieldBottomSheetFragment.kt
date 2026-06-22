package ai.offside.mobile.android.helper.testlabs.nav.inputfield

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CompoundButton
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetViewModel
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignBottomSheetAmountInputFieldBinding
import ai.offside.mobile.android.component.ui.inputfield.AmountBottomSheetDataItem
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldConstant
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldFilter
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldTextWatcher
import ai.offside.mobile.android.component.ui.inputfield.InputFieldUtils
import ai.offside.mobile.android.component.ui.inputfield.RadioGroupCheckListener
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Bottom sheet modal for Amount input field with different options to select amount & also other option to enter amount manually.
 *
 */
class AmountInputFieldBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTestUiRedesignBottomSheetAmountInputFieldBinding
    private lateinit var navController: NavController
    private var selectedAmountRadioButton: CompoundButton? = null
    private lateinit var amountBottomSheetViewModel: AmountBottomSheetViewModel
    private val bottomSheetViewModel: BottomSheetViewModel by viewModels({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentTestUiRedesignBottomSheetAmountInputFieldBinding.inflate(
            inflater, container, false
        )

        amountBottomSheetViewModel = ViewModelProvider(requireActivity())[AmountBottomSheetViewModel::class.java]
        binding.amountBottomSheetViewModel = amountBottomSheetViewModel

        //set to adjust screen height automatically, when soft keyboard appears on screen. Otherwise input edit text gets hidden behind then keyboard.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        } else {
            @Suppress("DEPRECATION")
            requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        initUi()
    }

    /**
     * Initializes UI items.
     */
    private fun initUi() {
        val defaultAmountText: String = requireContext().getString(R.string.debug_ui_redesign_amount_input_default_text)
        RadioGroupCheckListener.makeGroup(binding.amountOption1RadioButton,
            binding.amountOption2RadioButton,
            binding.amountOption3RadioButton,
            binding.amountOption4RadioButton,
            radioGroupCustomCheckListener = object :
                RadioGroupCheckListener.RadioGroupCustomCheckListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    selectedAmountRadioButton = buttonView
                    if (isChecked) {
                        val selectedState = requireContext().getString(ai.offside.mobile.android.component.ui.R.string.a11y_state_selected)
                        selectedAmountRadioButton?.announceForAccessibility(selectedState)
                    }
                    when (buttonView?.id) {
                        R.id.amount_option4_radioButton -> {
                            binding.otherAmountEditText.apply {
                                setText(defaultAmountText)
                            }
                            binding.bottomSheetOtherAmountLayout.visibility = View.VISIBLE
                            (dialog as? BottomSheetDialog)?.behavior?.state =
                                BottomSheetBehavior.STATE_EXPANDED
                            InputFieldUtils.initiateFocus(context, binding.otherAmountEditText)
                            binding.bottomSheetAmountModalOkayButton.isEnabled = false
                        }

                        else -> {
                            InputFieldUtils.hideKeyboard(context, binding.otherAmountEditText)
                            binding.bottomSheetOtherAmountLayout.visibility = View.GONE
                            binding.bottomSheetAmountModalOkayButton.isEnabled = true
                            bottomSheetViewModel.bottomSheetData.postValue(buttonView?.tag as AmountBottomSheetDataItem)
                        }
                    }
                }

            })

        binding.bottomSheetAmountModalOkayButton.setOnClickListener {
            selectedAmountRadioButton?.let {
                when (selectedAmountRadioButton?.id) {
                    R.id.amount_option4_radioButton -> {
                        val otherAmountText = binding.otherAmountEditText.text.toString()
                        if (!TextUtils.isEmpty(otherAmountText)) {
                            val enteredAmount: BigDecimal = BigDecimal(
                                otherAmountText.replace("[$,]".toRegex(), "")
                            ).setScale(2, RoundingMode.FLOOR)
                            val optionLabel = (selectedAmountRadioButton?.tag as AmountBottomSheetDataItem).label
                            bottomSheetViewModel.bottomSheetData.postValue(
                                AmountBottomSheetDataItem(
                                    enteredAmount,
                                    optionLabel
                                )
                            )
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                AmountInputFieldConstant.selectedAmountBottomSheetKey,
                                AmountBottomSheetDataItem(
                                    amount = enteredAmount,
                                    label = optionLabel
                                )
                            )
                        } else {
                            dismiss()
                        }
                    }

                    else -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            AmountInputFieldConstant.selectedAmountBottomSheetKey,
                            selectedAmountRadioButton!!.tag
                        )
                    }
                }
            }
            dismiss()
        }

        binding.bottomSheetCloseButtonAmountInput.setOnClickListener {
            bottomSheetViewModel.dismiss()
            dismiss()
        }

        val otherAmountTextWatcher = AmountInputFieldTextWatcher(binding.otherAmountEditText,
            object : AmountInputFieldTextWatcher.AmountInputTextChangeListener {
                override fun afterTextChange(s: Editable?, formattedInput: BigDecimal?) {
                    formattedInput?.let {
                        binding.bottomSheetAmountModalOkayButton.isEnabled =
                            formattedInput > BigDecimal(10)
                    }
                }
            })

        binding.otherAmountEditText.apply {
            filters += AmountInputFieldFilter()
            addTextChangedListener(otherAmountTextWatcher)
            setText(defaultAmountText)
        }
    }

    companion object {

        /**
         * Method to create a wew instance of this fragment.
         *
         * @return [AmountInputFieldBottomSheetFragment]
         */
        @JvmStatic
        fun newInstance(): AmountInputFieldBottomSheetFragment =
            AmountInputFieldBottomSheetFragment()
    }
}