package ai.offside.mobile.android.component.ui.slider

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Button
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.color.MaterialColors
import com.google.android.material.imageview.ShapeableImageView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.databinding.SliderButtonBinding
import ai.offside.mobile.android.component.ui.databinding.SliderComponentBinding
import ai.offside.mobile.android.component.ui.slider.data.OnSwipeListener
import ai.offside.mobile.android.component.ui.slider.data.SliderButtonData
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentData
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataModel
import kotlin.math.abs

/**
 * Component class for Custom Slider View
 * Handles data binding and accessibility actions for swipe-able buttons
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class SliderComponentLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {
    var binding: SliderComponentBinding = SliderComponentBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private var onSwipeStartListenerCallback: OnSwipeListener? = null

    /** Drag Threshold value for when the system detects movement as drag */
    private val dragThreshold = ViewConfiguration.get(context).scaledTouchSlop
    private var dX: Float = 0f

    /** Click event for slider */
    private var clickEvent = SliderClickEvent.NONE
    private var isClick = false

    /** SliderButtonData triggered on long swipe */
    private lateinit var longSwipeLeftButtonData: SliderButtonData
    private lateinit var longSwipeRightButtonData: SliderButtonData

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    /**
     * Updates slider data and adds accessibility action
     */
    fun setSliderComponentData(dataModel: SliderComponentDataModel) {
        binding.dataModel = dataModel
        val data = dataModel.data

        if (data is SliderComponentData.LeftButton) {
            updateSliderButtonInfo(
                binding.leftSwipeMenuButton,
                SliderClickEvent.LEFT_BUTTON,
                data.leftButtonData,
                R.attr.offside_onSurfaceVariantLowStatic,
                R.color.sys_color_neutral40
            )
            longSwipeLeftButtonData = data.leftButtonData
        }

        if (data is SliderComponentData.OneRightButton) {
            updateSliderButtonInfo(
                binding.rightSwipeMenuRightButton,
                SliderClickEvent.RIGHT_BUTTON_RIGHT,
                data.firstButtonData,
                R.attr.offside_secondaryStatic,
                R.color.sys_color_blue30
            )
            longSwipeRightButtonData = data.firstButtonData
        }
        if (data is SliderComponentData.TwoRightButton) {
            updateSliderButtonInfo(
                binding.rightSwipeMenuMiddleButton,
                SliderClickEvent.RIGHT_BUTTON_MIDDLE,
                data.secondButtonData,
                R.attr.offside_primaryStatic,
                R.color.sys_color_blue40
            )
        }
        if (data is SliderComponentData.ThreeRightButton) {
            updateSliderButtonInfo(
                binding.rightSwipeMenuLeftButton,
                SliderClickEvent.RIGHT_BUTTON_LEFT,
                data.thirdButtonData,
                R.attr.offside_primaryVariantStatic,
                R.color.sys_color_blue20
            )
        }
        setLongSwipeTransitionListener()
    }

    /**
     * Sets main content view
     *
     * @param view
     */
    fun setMainContent(view: View) {
        binding.mainContent.removeAllViews()
        binding.mainContent.addView(view)
    }

    /**
     * Sets role as button for A11Y support
     */
    fun addRoleDescription(contentDescription: String) {
        binding.mainContent.modifyRoleDescription(contentDescription, Button::class.java.simpleName)
    }

    /**
     * Sets click action for main content
     *
     * @param clickAction
     */
    fun setMainContentClickAction(clickAction: () -> Unit) {
        setSliderButtonOnClickListener(
            binding.mainContent,
            SliderClickEvent.MAIN_CONTENT,
            clickAction
        )
    }

    /**
     * Updates slider button color
     *
     * @param sliderButton
     * @param color
     * @param defaultColor
     */
    private fun updateSliderButtonColor(
        sliderButton: SliderButtonBinding,
        @AttrRes color: Int,
        @ColorRes defaultColor: Int
    ) {
        sliderButton.root.setBackgroundColor(
            MaterialColors.getColor(
                context,
                color,
                ContextCompat.getColor(context, defaultColor)
            )
        )
    }

    /**
     * Updates label, icon resource and onClick for slider button
     *
     * @param button
     * @param buttonInfo
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun updateSliderButtonInfo(
        button: SliderButtonBinding,
        clickEvent: SliderClickEvent,
        buttonInfo: SliderButtonData,
        @AttrRes color: Int,
        @ColorRes defaultColor: Int
    ) {
        button.buttonData = buttonInfo
        updateSliderButtonColor(button, color, defaultColor)
        setSliderButtonOnClickListener(button.root, clickEvent) {
            buttonInfo.onButtonClick(buttonInfo)
        }
        addAccessibilityAction(binding.mainContent, buttonInfo)
    }

    /**
     * Adds accessibility action to target view
     *
     * @param view
     * @param button
     * @param buttonData
     */
    private fun addAccessibilityAction(
        view: View,
        buttonData: SliderButtonData
    ) {
        ViewCompat.addAccessibilityAction(view, buttonData.label) { _, _ ->
            buttonData.onButtonClick(buttonData)
            true
        }
    }

    /**
     * Resets the motion state of the compoennt
     */
    fun resetState() {
        binding.sliderLayoutParent.transitionToState(R.id.start)
    }

    /**
     * Intercept touch events, handle clicks and executes swipe callbacks
     *
     * @param event
     */
    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                isClick = true
                dX = event.x
            }

            MotionEvent.ACTION_MOVE -> {
                if (abs(event.x - dX) > dragThreshold) {
                    isClick = false
                    resetViewRipple()
                }
                if (binding.sliderLayoutParent.progress != 0f) {
                    onSwipeStartListenerCallback?.onSwipeStart(this)
                }
            }

            MotionEvent.ACTION_UP -> {
                if (isClick) {
                    handleViewClick()
                }
                clickEvent = SliderClickEvent.NONE
                onSwipeStartListenerCallback?.onTouchUp()
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    /**
     * Resets ripples of the views
     */
    private fun resetViewRipple() {
        binding.leftSwipeMenuButton.root.isPressed = false
        binding.rightSwipeMenuLeftButton.root.isPressed = false
        binding.rightSwipeMenuMiddleButton.root.isPressed = false
        binding.rightSwipeMenuRightButton.root.isPressed = false
        binding.mainContent.isPressed = false
    }

    /**
     * Handle click events for slider MainContent and Buttons
     */
    private fun handleViewClick() {
        when (clickEvent) {
            SliderClickEvent.LEFT_BUTTON -> binding.leftSwipeMenuButton.root.performClick()
            SliderClickEvent.RIGHT_BUTTON_LEFT -> binding.rightSwipeMenuLeftButton.root.performClick()
            SliderClickEvent.RIGHT_BUTTON_MIDDLE -> binding.rightSwipeMenuMiddleButton.root.performClick()
            SliderClickEvent.RIGHT_BUTTON_RIGHT -> binding.rightSwipeMenuRightButton.root.performClick()
            SliderClickEvent.MAIN_CONTENT -> binding.mainContent.performClick()
            SliderClickEvent.NONE -> Unit
        }
        isClick = false
    }

    fun setOnSwipeListener(callback: OnSwipeListener) {
        onSwipeStartListenerCallback = callback
    }

    /**
     * Sets onClick listener for clickable view of slider
     *
     * @param view
     * @param sliderClickEvent
     * @param action
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setSliderButtonOnClickListener(
        view: View,
        sliderClickEvent: SliderClickEvent,
        action: () -> Unit
    ) {
        view.setOnClickListener {
            /** Triggers ripple effect for the buttons */
            if (!view.isPressed) {
                view.isPressed = true
            }
            view.isPressed = false
            action()
        }
        view.setOnTouchListener { _, _ ->
            clickEvent = sliderClickEvent
            return@setOnTouchListener false
        }
        view.isClickable = false
    }

    /**
     * Creates and sets transition listener to trigger left or right most button action on long swipe transition
     */
    private fun setLongSwipeTransitionListener() {
        val longSwipeTransitionListener = object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {}

            override fun onTransitionChange(
                p0: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {}

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                if (p0?.currentState == R.id.right_trigger_end && ::longSwipeRightButtonData.isInitialized) {
                        longSwipeRightButtonData.onButtonClick(longSwipeRightButtonData)
                        resetViewRipple()
                        resetState()
                }

                if (p0?.currentState == R.id.left_trigger_end && ::longSwipeLeftButtonData.isInitialized) {
                    longSwipeLeftButtonData.onButtonClick(longSwipeLeftButtonData)
                    resetViewRipple()
                    resetState()
                }
            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        }
        if(!::longSwipeRightButtonData.isInitialized){
            disableLeftSwipe()
        }
        if(!::longSwipeLeftButtonData.isInitialized){
            disableRightSwipe()
        }
        binding.sliderLayoutParent.setTransitionListener(longSwipeTransitionListener)
    }

    /**
     * Disables left swipe transitions
     */
    private fun disableLeftSwipe(){
        binding.sliderLayoutParent.enableTransition(R.id.left_swipe, false)
        binding.sliderLayoutParent.enableTransition(R.id.left_long_swipe, false)
        binding.sliderLayoutParent.setTransition(R.id.right_swipe)
    }

    /**
     * Disables right swipe transitions
     */
    private fun disableRightSwipe() {
        binding.sliderLayoutParent.enableTransition(R.id.right_swipe, false)
        binding.sliderLayoutParent.enableTransition(R.id.right_long_swipe, false)
        binding.sliderLayoutParent.setTransition(R.id.left_swipe)
    }

    companion object {
        @BindingAdapter("sliderButtonDrawable")
        @JvmStatic
        fun ShapeableImageView.setSliderButtonDrawable(@DrawableRes drawableSrc: Int) {
            setImageResource(drawableSrc)
        }
    }
}