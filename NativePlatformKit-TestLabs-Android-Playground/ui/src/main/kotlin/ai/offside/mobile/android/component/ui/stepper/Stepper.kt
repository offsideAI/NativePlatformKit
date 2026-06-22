package ai.offside.mobile.android.component.ui.stepper

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.StepperLineLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.StepperNumberLayoutBinding

/**
 * Navigation stepper component - Custom component to create steps dynamically and navigate forward and back
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class Stepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var stepperListener: StepperListener
    private var completedStep = 0
    private var stepCount:Int

    init {
        val stepperAttributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Stepper)
        stepCount = stepperAttributes.getInt(R.styleable.Stepper_stepCount, 0)
        stepperAttributes.recycle()
        this.orientation = HORIZONTAL
        this.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
        initializeStepper()
    }

    /**
     * Initialize stepper component steps based on step count
     */
    private fun initializeStepper() {
        for (i in 1..stepCount) {
            this.addView(getStepperNumberLayout(i))

            if (i != stepCount) {
                val stepperLineLayoutBinding = StepperLineLayoutBinding.inflate(LayoutInflater.from(context), this, false)
                this.addView(stepperLineLayoutBinding.root)
            }
        }
        setAccessibilityText()
    }

    /**
     * Gets stepper number layout to be added to the stepper
     *
     * @param stepNumber - sets step number to the step
     */
    private fun getStepperNumberLayout(stepNumber: Int): View {
        val stepperNumberLayoutBinding = StepperNumberLayoutBinding.inflate(LayoutInflater.from(context), this, false)
        stepperNumberLayoutBinding.stepperNumber.text = stepNumber.toString()
        return stepperNumberLayoutBinding.root
    }

    /**
     * @param data additional data for next step
     * Go to next step in the stepper
     */
    fun goToNextStep(data: Bundle) {
        if (completedStep == stepCount) return

        val index: Int = if (completedStep == FIRST_STEP_INDEX) {
            this.completedStep
        } else {
            NEXT_STEP_OFFSET_INDEX * completedStep
        }

        val currentStepBinding = StepperNumberLayoutBinding.bind(this.getChildAt(index))
        currentStepBinding.stepperCompleteImage.visibility = View.VISIBLE
        (currentStepBinding.stepperCompleteImage.drawable as Animatable).start()
        currentStepBinding.stepperNumber.visibility = View.GONE

        completedStep++

        if (index != stepperChildCount()) {
            val nextLineIndex = index + 1
            val nextLineBinding = StepperLineLayoutBinding.bind(this.getChildAt(nextLineIndex))
            nextLineBinding.stepperLineImage.visibility = View.VISIBLE
            (nextLineBinding.stepperLineImage.drawable as Animatable).start()
            nextLineBinding.stepperLine.visibility = View.GONE
        }

        if (completedStep == stepCount) stepperListener.onStepsCompleted(data)
        else stepperListener.onStepForward(completedStep + CURRENT_STEP_INDEX, data)
        setAccessibilityText()
    }

    /**
     * Go to previous step in the stepper
     */
    fun goToPreviousStep() {
        if (completedStep == FIRST_STEP_INDEX) return

        val index: Int
        if (completedStep == stepCount) {
            index = stepperChildCount()
            completedStep--
        } else {
            index = NEXT_STEP_OFFSET_INDEX * (completedStep - 1)
            completedStep--
        }

        val currentStepBinding = StepperNumberLayoutBinding.bind(this.getChildAt(index))
        currentStepBinding.stepperNumber.visibility = View.VISIBLE
        currentStepBinding.stepperCompleteImage.visibility = View.GONE

        if (index != stepperChildCount()) {
            val nextLineIndex = index + 1
            val nextLineBinding = StepperLineLayoutBinding.bind(this.getChildAt(nextLineIndex))
            nextLineBinding.stepperLine.visibility = View.VISIBLE
            nextLineBinding.stepperLineImage.visibility = View.GONE
        }
        stepperListener.onStepBack(completedStep + CURRENT_STEP_INDEX)
        setAccessibilityText()
    }


    /**
     * Set navigation listener for callbacks on step changed and on complete of steps
     *
     * @param stepperListener
     */
    fun setStepperListener(stepperListener: StepperListener) {
        this.stepperListener = stepperListener
    }

    /**
     * Reset current stepper and create new stepper with step count provided
     *
     * @param stepCount - number of steps needed for stepper
     */
    fun resetAndInitializeStepper(stepCount: Int) {
        this.stepCount = stepCount
        removeAllViews()
        completedStep = 0
        initializeStepper()
    }

    /**
     * sets stepper component accessibility text
     */
    private fun setAccessibilityText() {
        if (completedStep != stepCount) {
            this.contentDescription = context.getString(
                R.string.stepper_current_step_a11y_text,
                (completedStep + CURRENT_STEP_INDEX),
                stepCount
            )
        } else {
            this.contentDescription = context.getString(R.string.stepper_completed_steps_a11y_text)
        }
    }

    /**
     * Get stepper view child count
     */
    private fun stepperChildCount() = this.childCount - 1

    companion object {
        private const val NEXT_STEP_OFFSET_INDEX = 2
        private const val CURRENT_STEP_INDEX = 1
        private const val FIRST_STEP_INDEX = 0
    }
}