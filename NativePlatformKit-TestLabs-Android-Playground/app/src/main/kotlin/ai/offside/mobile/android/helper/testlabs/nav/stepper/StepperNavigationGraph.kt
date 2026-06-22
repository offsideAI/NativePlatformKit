package ai.offside.mobile.android.helper.testlabs.nav.stepper

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import ai.offside.mobile.android.helper.testlabs.R

enum class StepperNavigationGraph(
    @IdRes val id: Int,
    @NavigationRes val graphRes: Int,
    @IdRes val startDestination: Int,
    val stepCount: Int
) {
    FIVE_STEP(
        id = R.id.component_ui_stepper_five_step_nav_graph,
        graphRes = R.navigation.component_ui_stepper_five_step_nav_graph,
        startDestination = R.id.component_ui_stepper_step_one_nav_graph,
        stepCount = 5,
    ),
    FOUR_STEP(
        id = R.id.component_ui_stepper_four_step_nav_graph,
        graphRes = R.navigation.component_ui_stepper_four_step_nav_graph,
        startDestination = R.id.component_ui_stepper_step_one_nav_graph,
        stepCount = 4,
    ),
    THREE_STEP(
        id = R.id.component_ui_stepper_three_step_nav_graph,
        graphRes = R.navigation.component_ui_stepper_three_step_nav_graph,
        startDestination = R.id.component_ui_stepper_step_one_nav_graph,
        stepCount = 3,
    )
}