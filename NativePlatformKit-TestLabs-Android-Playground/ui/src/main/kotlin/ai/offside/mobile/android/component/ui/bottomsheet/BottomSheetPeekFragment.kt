package ai.offside.mobile.android.component.ui.bottomsheet

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.BottomSheetPeekLayoutBinding

/**
 *  BottomSheetPeekFragment - BottomSheet that is always shown in the bottom of the Screen with minimal Peak height
 */
class BottomSheetPeekFragment : Fragment() {
    private lateinit var binding: BottomSheetPeekLayoutBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var bottomSheetTitleText: String
    private lateinit var bottomSheetSubTitleText: String

    private val bottomSheetCallBack = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN, BottomSheetBehavior.STATE_COLLAPSED -> {
                    binding.bottomSheetHeader.bottomSheetCloseButton.text =
                        resources.getText(R.string.a11y_bottom_sheet_show_list_text)
                }

                BottomSheetBehavior.STATE_EXPANDED, BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    binding.bottomSheetHeader.bottomSheetCloseButton.text =
                        resources.getText(R.string.a11y_bottom_sheet_hide_list_text)
                }

                BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> {}
            }
            setContentDescriptionForShowListButton()
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        val peakingBottomSheetAttrs: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PeakBottomSheetFragment)
        bottomSheetTitleText =
            peakingBottomSheetAttrs.getString(R.styleable.PeakBottomSheetFragment_peek_bottom_sheet_fragment_title)
                .toString()
        bottomSheetSubTitleText =
            peakingBottomSheetAttrs.getString(R.styleable.PeakBottomSheetFragment_peek_bottom_sheet_fragment_sub_title)
                .toString()
        peakingBottomSheetAttrs.recycle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetPeekLayoutBinding.inflate(inflater, container, false)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetPeaking)
        bottomSheetBehavior.apply {
            peekHeight =
                CustomBottomSheetBehavior.getMinPeekHeightOfBottomSheet(
                    requireContext()
                )
            state = BottomSheetBehavior.STATE_COLLAPSED
            maxHeight =
                CustomBottomSheetBehavior.getMaxHeightOfBottomSheet(
                    requireContext()
                )
            addBottomSheetCallback(bottomSheetCallBack)
        }
        setTitles()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addShowListButton()
        configureAccessibility()
        setContentDescriptionForShowListButton()
        setFocusOnBottomsheetDragHandle()
    }

    /**
     *  Set Titles for PeakingBottomSheetFragment
     */
    private fun setTitles() {
        binding.bottomSheetHeader.apply {
            bottomSheetTitle.apply {
                text = bottomSheetTitleText
                accessibilityTraversalAfter = binding.bottomSheetDragHandle.id
            }
            bottomSheetSubTitle.apply {
                text = bottomSheetSubTitleText
                accessibilityTraversalAfter = bottomSheetTitle.id
            }
        }
    }

    private fun toggleBottomSheetState() {
        with(bottomSheetBehavior) {
            when (state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    this.state = BottomSheetBehavior.STATE_EXPANDED
                }

                BottomSheetBehavior.STATE_EXPANDED -> {
                    this.state = BottomSheetBehavior.STATE_COLLAPSED
                }

                else -> {
                    this.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
    }

    /**
     *  Show/Hide List Button
     */
    private fun addShowListButton() {
        binding.bottomSheetHeader.bottomSheetCloseButton.apply {
            text = this.context.getString(R.string.a11y_bottom_sheet_show_list_text)
            icon = null
            setOnClickListener { toggleBottomSheetState() }
            accessibilityTraversalAfter = binding.bottomSheetHeader.bottomSheetSubTitle.id
        }
    }

    /**
     *  set A11y focus for the bottomsheet drag handle
     *
     */
    private fun setFocusOnBottomsheetDragHandle() {
        binding.bottomSheetDragHandle.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
        binding.bottomSheetDragHandle.accessibilityTraversalBefore = binding.bottomSheetHeader.bottomSheetTitle.id
    }

    /**
     *  Add BottomSheet Content
     *  @param view - View to be added on main content area , below to the header area
     */
    fun setContentView(view: View) {
        binding.bottomSheetContent.addView(view)
    }

    /**
     *  Hide BottomSheet Subheader
     */
    fun hideSubTitle() {
        binding.bottomSheetHeader.apply {
            bottomSheetSubTitle.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallBack)
    }

    /**
     * Configure Accessibility for Title
     */
    private fun configureAccessibility() {
        with(binding.bottomSheetHeader.bottomSheetTitle) {
            isFocusable = true
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            ViewCompat.setAccessibilityHeading(this, true)
        }
    }

    /**
     *  set A11y Description for Show List button based on the BottomShett state
     *
     */
    private fun setContentDescriptionForShowListButton() {
        ViewCompat.setAccessibilityDelegate(
            binding.bottomSheetHeader.bottomSheetCloseButton,
            object : AccessibilityDelegateCompat() {

                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    val customClickAction = AccessibilityActionCompat(
                        AccessibilityNodeInfoCompat.ACTION_CLICK,
                        CustomBottomSheetBehavior.getBehaviorStateByString(
                            bottomSheetState = bottomSheetBehavior.state,
                            behaviorContext = requireContext()
                        )
                    )
                    info.addAction(customClickAction)
                }
            })
    }

}