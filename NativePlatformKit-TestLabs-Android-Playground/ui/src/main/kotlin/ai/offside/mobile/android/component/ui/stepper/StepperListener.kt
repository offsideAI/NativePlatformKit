package ai.offside.mobile.android.component.ui.stepper

import android.os.Bundle

/**
 * Listener for Navigation Stepper component
 */
interface StepperListener {

    /**
     * Forward action for Call back on navigation step changes
     *
     * @param currentStep current step in the stepper
     * @param data Extra data for next step
     */
    fun onStepForward(currentStep: Int, data: Bundle?)

    /**
     * Call back on steps in stepper component completed
     *
     * @param data Extra data used for stepper completion
     */
    fun onStepsCompleted(data: Bundle?)

    /**
     * Backward action for Call back on navigation step changes
     *
     * @param currentStep current step in the stepper
     */
    fun onStepBack(currentStep: Int) { }
}