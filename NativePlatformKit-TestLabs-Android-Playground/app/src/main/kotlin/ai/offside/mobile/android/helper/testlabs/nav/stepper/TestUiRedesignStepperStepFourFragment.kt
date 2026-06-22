package ai.offside.mobile.android.helper.testlabs.nav.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignStepperStepFourFragmentBinding

class TestUiRedesignStepperStepFourFragment : Fragment(R.layout.fragment_test_ui_redesign_stepper_step_four_fragment) {
    private lateinit var binding: FragmentTestUiRedesignStepperStepFourFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignStepperStepFourFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(view)
        binding.btnNext.setOnClickListener {
            parentFragment?.parentFragmentManager?.setResult(requireContext(), true)
            val navId = if (navController.graph.count() - 1 == StepperNavigationGraph.FOUR_STEP.stepCount) {
                R.id.action_testUIRedesignStepperStepFourFragment_to_testUIRedesignStepperStepCompletedFragment
            } else {
                R.id.action_testUIRedesignStepperStepFourFragment_to_testUIRedesignStepperStepFiveFragment
            }
            navController.navigate(navId)
        }
        binding.btnPrevious.setOnClickListener {
            parentFragment?.parentFragmentManager?.setResult(requireContext(), false)
            navController.popBackStack()
        }

        onDeviceBackPress { binding.btnPrevious.performClick() }
    }
}