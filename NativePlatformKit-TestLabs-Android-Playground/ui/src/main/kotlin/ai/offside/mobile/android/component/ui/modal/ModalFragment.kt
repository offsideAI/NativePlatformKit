package ai.offside.mobile.android.component.ui.modal

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import com.google.android.material.color.MaterialColors
import com.google.android.material.textview.MaterialTextView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.FragmentGenericModalBinding
import ai.offside.mobile.android.component.ui.modal.model.ModalActionData
import ai.offside.mobile.android.component.ui.modal.model.ModalButtonState
import ai.offside.mobile.android.component.ui.modal.model.ModalButtonType
import ai.offside.mobile.android.component.ui.transition.fade.fadeThrough

/**
 * This Modal Fragment will support for below types
         * Small
         * Small with subheader
         * Medium
         * Large
         * Large with subheader
         * Small with Checkbox
         * Medium with Checkbox
 * The buttons can be configured to max of 3
 * Overflow contents will be scrollable by default on all of the modals
 */
class ModalFragment: AppCompatDialogFragment(R.layout.fragment_generic_modal) {

    private var _binding: FragmentGenericModalBinding? = null
    private val binding: FragmentGenericModalBinding get() = _binding!!

    private val viewModal: ModalViewModel by viewModels({requireParentFragment()})

    /**
     * Added the Offside custom theme to occupy the 80% width of the screen for display, default it will be 65% or less
     * Also the corners are rounded to [offside.redesign.shape.regular]
     */
    override fun getTheme(): Int {
        return R.style.Offside_Redesign_Dialog_Modal
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGenericModalBinding.bind(view)

       viewModal.modalData.observe(viewLifecycleOwner) {
           binding.modalData = it
           addModalBodyContent(it.modalBody)
           if (it.modalSubHeader.isNotBlank()) {
               (binding.modalDescription.layoutParams as  ViewGroup.MarginLayoutParams).topMargin = resources.getDimension(R.dimen.margin_xsmall).toInt()
           }
           binding.headerContainerFadeOut.modalIcon.setImageResource(it.icon)
           val buttonSate = it.buttonState
           setAction(buttonSate.primaryAction)
           when(buttonSate) {
               is ModalButtonState.TwoButtonModal -> setAction(buttonSate.secondaryAction)
               is ModalButtonState.ThreeButtonModal -> {
                   setAction(buttonSate.secondaryAction)
                   setAction(buttonSate.tertiaryAction)
               }
               else -> Unit
           }

           if (it.checkboxModalVisibility == View.VISIBLE) {
               binding.primaryAction.isEnabled = false
               binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                   binding.primaryAction.isEnabled = isChecked
               }
           }
        }
        ViewCompat.setAccessibilityHeading(binding.headerContainerFadeOut.modalTitle, true)
        ViewCompat.setAccessibilityHeading(binding.headerContainerFadeIn.modalTitleSticky, true)
        addHeaderTransition()
    }

    /**
     * When there is scrollable content then while scrolling the body content should fade-out the existing header (icon, title, supplementary)
     *      and fade-in the sticky header with tile and supplementary
     */
    private fun addHeaderTransition() {
        var headerMotionToggled = false
        binding.scrollContent.setOnScrollChangeListener(object: OnScrollChangeListener{
            override fun onScrollChange(
                v: View,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY > oldScrollY && headerMotionToggled.not()) {
                    headerMotionToggled = true
                    val fadeThrough = fadeThrough()
                    TransitionManager.beginDelayedTransition(
                        binding.modal,
                        fadeThrough.setDuration(HEADER_FADE_DURATION)
                    )
                    binding.headerContainerFadeOut.headerContainer.isVisible = false
                    binding.headerContainerFadeIn.headerContainer.isVisible = true
                } else if (oldScrollY > scrollY && headerMotionToggled) {
                    headerMotionToggled = false
                    val fadeThrough = fadeThrough()
                    TransitionManager.beginDelayedTransition(
                        binding.modal,
                        fadeThrough.setDuration(HEADER_FADE_DURATION)
                    )
                    binding.headerContainerFadeOut.headerContainer.isVisible = true
                    binding.headerContainerFadeIn.headerContainer.isVisible = false
                }
            }
        })
    }

    /**
     * As per the story, A11y expectation is to read each paragraph as individual swipe,
     *      The TextView will read the whole content in one go with little pause between paragraphs.
     * String that separated with new line "\n" will be considered as new paragraph.
     * Introduced a container to hold the paragraphs that created dynamically.
     *
     * @param : bodyContent : List<String> -> Array of strings to be displayed, add \n at then end of string for additional space between the paragraphs.
     */
    private fun addModalBodyContent(bodyContent: List<String>) {
        bodyContent.forEach {
            val content = MaterialTextView(requireContext())
            content.setTextColor(MaterialColors.getColor(requireContext(), R.attr.offside_onSurfaceVariantMedium, Color.BLACK))
            content.setTextAppearance(R.style.Offside_Redesign_BodyMediumTextStyle)
            content.text = it
            binding.modalDescription.addView(content)
        }
    }

    /**
     * @param : actionView -> MaterialButton reference to set label, visibility and action listener
     * @param : actionData -> contains the action label and listener
     */
    private fun setAction(actionData: ModalActionData) {
        val actionView = when (actionData.buttonType) {
            ModalButtonType.PRIMARY -> binding.primaryAction
            ModalButtonType.SECONDARY -> binding.secondaryAction
            ModalButtonType.TERTIARY -> binding.tertiaryAction
        }
        actionView.apply {
            text = actionData.label
            isVisible = true
            setOnClickListener {
                dismiss()
                actionData.onActionListener.invoke(it)
            }
        }
    }

    /**
     * @param dialog : [DialogInterface]
     * when the dialog is dismissed the parent fragment dismiss interface will be called for explicit dismiss actions
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (parentFragment as? DialogInterface.OnDismissListener)?.onDismiss(dialog)
    }

    /**
     * clear the binding object when the view destroyed as per the view-binding guideline
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        /**
         * Before creating DialogModal, please make sure [ModalViewModel] has been initialized and the modal data are updated
         * @return : instance of the current fragment
         */
        fun getInstance(): ModalFragment {
            return ModalFragment()
        }

        private const val HEADER_FADE_DURATION = 300L
    }
}