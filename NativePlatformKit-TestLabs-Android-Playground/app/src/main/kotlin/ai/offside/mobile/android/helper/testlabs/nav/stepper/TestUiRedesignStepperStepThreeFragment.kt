package ai.offside.mobile.android.helper.testlabs.nav.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignStepperStepThreeFragmentBinding

class TestUiRedesignStepperStepThreeFragment : Fragment(R.layout.fragment_test_ui_redesign_stepper_step_three_fragment) {
    private lateinit var binding: FragmentTestUiRedesignStepperStepThreeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignStepperStepThreeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(view)
        binding.btnNext.setOnClickListener {
            parentFragment?.parentFragmentManager?.setResult(requireContext(), true)
            val navId = if (navController.graph.count() - 1 == StepperNavigationGraph.THREE_STEP.stepCount) {
                R.id.action_testUIRedesignStepperStepThreeFragment_to_testUIRedesignStepperStepCompletedFragment
            } else {
                R.id.action_testUIRedesignStepperStepThreeFragment_to_testUIRedesignStepperStepFourFragment
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