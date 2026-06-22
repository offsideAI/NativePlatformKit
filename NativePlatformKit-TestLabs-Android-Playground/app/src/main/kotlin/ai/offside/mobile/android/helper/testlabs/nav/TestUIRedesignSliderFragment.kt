package ai.offside.mobile.android.helper.testlabs.nav

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textview.MaterialTextView
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestSliderBinding
import ai.offside.mobile.android.component.ui.slider.SliderComponentLayout
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataModel
import ai.offside.mobile.android.helper.testlabs.SliderViewModel

class TestUIRedesignSliderFragment : Fragment() {
    lateinit var viewModel: SliderViewModel
    lateinit var binding: FragmentTestSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentTestSliderBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = ViewModelProvider(this)[SliderViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.slider1Data.observe(viewLifecycleOwner) {
            addSliderComponent(
                binding.slider1,
                it,
                requireContext().getString(R.string.debug_ui_redesign_slider_single_left_button)
            )
        }

        viewModel.slider2Data.observe(viewLifecycleOwner) {
            addSliderComponent(
                binding.slider2,
                it,
                requireContext().getString(R.string.debug_ui_redesign_slider_single_right_button)
            )
        }

        viewModel.slider3Data.observe(viewLifecycleOwner) {
            addSliderComponent(
                binding.slider3,
                it,
                requireContext().getString(R.string.debug_ui_redesign_slider_two_right_button)
            )
        }

        viewModel.slider4Data.observe(viewLifecycleOwner) {
            addSliderComponent(
                binding.slider4,
                it,
                requireContext().getString(R.string.debug_ui_redesign_slider_three_right_button)
            )
        }
    }

    private fun addSliderComponent(
        sliderComponent: SliderComponentLayout,
        buttonData: SliderComponentDataModel,
        text: String
    ) {
        sliderComponent.setSliderComponentData(buttonData)
        sliderComponent.setMainContent(getTextView(text))
        sliderComponent.setMainContentClickAction { onClick() }
        sliderComponent.addRoleDescription(text)
    }

    private fun getTextView(text: String): MaterialTextView {
        val textview = MaterialTextView(requireContext())
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 300)
        textview.layoutParams = layoutParams
        textview.gravity = Gravity.CENTER
        textview.text = text
        return textview
    }

    private fun onClick() {
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
    }
}