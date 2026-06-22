package ai.offside.mobile.android.component.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NavigationRes
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.BottomSheetFlowLayoutBinding

/**
 *  [BottomSheetFlowDialogFragment]
 *  @param navGraphId - Navigation graph (eg: R.navigation.graph)
 *  @param bundle - Send the StartDestination Fragment arguments as Bundle
 */
class BottomSheetFlowDialogFragment(
    @NavigationRes val navGraphId: Int,
    val bundle: Bundle? = null
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFlowLayoutBinding
    private lateinit var navController: NavController
    private lateinit var headerTitle: CharSequence

    private val viewModel:BottomSheetViewModel by viewModels ({requireActivity()})

    override fun getTheme(): Int = R.style.Offside_Redesign_BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFlowLayoutBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigationController()
        configureAccessibility()
        // Dismiss the BottomSheet
        viewModel.dismiss.observe(viewLifecycleOwner) {
            it?.also {
                this@BottomSheetFlowDialogFragment.isVisible.also {
                    dismiss()
                }
            }
        }
    }

    /**
     *  Setup navigationController with Toolbar
     */
    private fun setUpNavigationController() {

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_sheet_content) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(navGraphId, bundle)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                navController.graph.startDestinationId
            )
        )

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            binding.bottomSheetHeader.bottomSheetBackButton.isVisible =
                !appBarConfiguration.isTopLevelDestination(destination)
            headerTitle = arguments?.getCharSequence(Offside_BOTTOM_SHEET_BUNDLE_KEY_TITLE)
                ?: destination.label ?: ""
            binding.bottomSheetHeader.apply {
                bottomSheetTitle.text = headerTitle
                bottomSheetSubTitle.text =
                    arguments?.getString(Offside_BOTTOM_SHEET_BUNDLE_KEY_SUBTITLE, "")
            }
        }

        //Back Button
        binding.bottomSheetHeader.bottomSheetBackButton.setOnClickListener {
            navigateUp(navController, appBarConfiguration)
        }

        //Close Button
        binding.bottomSheetHeader.bottomSheetCloseButton.apply {
            contentDescription = this.context.getString(R.string.a11y_bottom_sheet_close_button)
            setOnClickListener {
                dismiss()
            }
        }
    }

    /**
     * Configure Accessibility for Title as Heading
     */
    private fun configureAccessibility() {
        with(binding.bottomSheetHeader.bottomSheetTitle) {
            isFocusable = true
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            ViewCompat.setAccessibilityHeading(this, true)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = (super.onCreateDialog(savedInstanceState)) as BottomSheetDialog
        with(dialog) {
            dismissWithAnimation = true
            setOnShowListener {
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    ?.apply {
                        val maxHeight =
                            CustomBottomSheetBehavior.getMaxHeightOfBottomSheet(
                                context
                            )
                        if (this.height > maxHeight) {
                            val bottomSheetLayoutParams = this.layoutParams
                            bottomSheetLayoutParams.height = maxHeight
                            this.layoutParams = bottomSheetLayoutParams
                        }
                        BottomSheetBehavior.from(this).apply {
                            this.maxHeight = maxHeight
                            state = BottomSheetBehavior.STATE_EXPANDED
                        }

                    }
            }
            setOnKeyListener { _, keyCode, event ->
                when {
                    keyCode == KeyEvent.KEYCODE_BACK && event.action != KeyEvent.ACTION_DOWN ->
                        navController.navigateUp()

                    else -> false
                }
            }
        }
        return dialog
    }

    companion object {
        const val Offside_BOTTOM_SHEET_BUNDLE_KEY_SUBTITLE = "subTitle"
        const val Offside_BOTTOM_SHEET_BUNDLE_KEY_TITLE = "title"
        const val Offside_BOTTOM_SHEET_BUNDLE_KEY_START_DESTINATION_ARGS = "startDestinationArgs"
    }
}