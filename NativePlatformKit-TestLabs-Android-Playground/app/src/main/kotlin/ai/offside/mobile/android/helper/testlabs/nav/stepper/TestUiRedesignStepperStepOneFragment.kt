package ai.offside.mobile.android.helper.testlabs.nav.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignStepperStepOneFragmentBinding

class TestUiRedesignStepperStepOneFragment : Fragment(R.layout.fragment_test_ui_redesign_stepper_step_one_fragment) {

    private lateinit var binding: FragmentTestUiRedesignStepperStepOneFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignStepperStepOneFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(view)
        binding.btnNext.setOnClickListener {
            parentFragment?.parentFragmentManager?.setResult(requireContext(), true)
            navController.navigate(R.id.action_testUIRedesignStepperStepOneFragment_to_testUIRedesignStepperStepTwoFragment)
        }
    }
}