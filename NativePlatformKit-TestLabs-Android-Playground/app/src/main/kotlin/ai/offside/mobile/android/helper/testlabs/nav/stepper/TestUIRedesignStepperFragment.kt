package ai.offside.mobile.android.helper.testlabs.nav.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignStepperBinding
import ai.offside.mobile.android.component.ui.stepper.StepperListener

class TestUIRedesignStepperFragment : Fragment(R.layout.fragment_test_ui_redesign_stepper), StepperListener {
    private lateinit var binding: FragmentTestUiRedesignStepperBinding

    private val navFragment: NavHostFragment
        get() = childFragmentManager.findFragmentById(
            R.id.stepper_fragment_container_view
        ) as NavHostFragment

    internal val navController: NavController
        get() = navFragment.navController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignStepperBinding.inflate(inflater, container, false)
        binding.navigationStepper.setStepperListener(this)

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            binding.stepMenu.context,
            R.array.stepper_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.stepMenu.adapter = adapter
        }
        binding.stepMenu.onItemSelectedListener = adapterOnItemSelectedListener

        childFragmentManager.setFragmentResultListener(
            getString(R.string.debug_ui_redesign_stepper_fragment_result_key), this
        ) { _, bundle ->
            val goToNextStep = bundle.getBoolean(getString(R.string.debug_ui_redesign_stepper_next_step_arguments_text))
            if (goToNextStep) { binding.navigationStepper.goToNextStep(bundle) }
            else { binding.navigationStepper.goToPreviousStep() }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController.graph = navController.navInflater.inflate(StepperNavigationGraph.FIVE_STEP.graphRes)
    }

    override fun onStepForward(currentStep: Int, data: Bundle?) {
        Toast.makeText(context, "Current Step $currentStep", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onStepsCompleted(data: Bundle?) {
        Toast.makeText(context, "Steps completed", Toast.LENGTH_SHORT)
            .show()
    }

    private val adapterOnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            var stepCount = 0
            when (position) {
                0 -> {
                    stepCount = StepperNavigationGraph.FIVE_STEP.stepCount
                    navController.graph = navController.navInflater.inflate(StepperNavigationGraph.FIVE_STEP.graphRes)
                }
                1 -> {
                    stepCount = StepperNavigationGraph.FOUR_STEP.stepCount
                    navController.graph = navController.navInflater.inflate(StepperNavigationGraph.FOUR_STEP.graphRes)
                }
                2 -> {
                    stepCount = StepperNavigationGraph.THREE_STEP.stepCount
                    navController.graph = navController.navInflater.inflate(StepperNavigationGraph.THREE_STEP.graphRes)
                }
            }
            binding.navigationStepper.resetAndInitializeStepper(stepCount = stepCount)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            //do nothing
        }
    }
}