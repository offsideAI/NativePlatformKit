package ai.offside.mobile.android.helper.testlabs.nav.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignStepperCompletedStepFragmentBinding

class TestUiRedesignStepperStepCompletedFragment : Fragment(R.layout.fragment_test_ui_redesign_stepper_completed_step_fragment) {
    private lateinit var binding: FragmentTestUiRedesignStepperCompletedStepFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignStepperCompletedStepFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}