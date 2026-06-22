package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestInfoBoxBinding


class TestUIRedesignInfoBoxFragment : Fragment() {
    lateinit var binding: FragmentTestInfoBoxBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestInfoBoxBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.infoBox1.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox1.setInfoTextWithLink(getSpannableString())
        binding.infoBox2.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox3.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox3.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox4.setInfoTextWithLink(getSpannableString())

        binding.infoBox5.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox5.setInfoTextWithLink(getSpannableString())
        binding.infoBox6.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox7.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox7.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox8.setInfoTextWithLink(getSpannableString())

        binding.infoBox9.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox9.setInfoTextWithLink(getSpannableString())
        binding.infoBox10.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox11.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox11.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox12.setInfoTextWithLink(getSpannableString())

        binding.infoBox13.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox13.setInfoTextWithLink(getSpannableString())
        binding.infoBox14.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox15.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox15.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox16.setInfoTextWithLink(getSpannableString())

        binding.infoBox17.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox17.setInfoTextWithLink(getSpannableString())
        binding.infoBox18.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox19.setInfoTitle(requireContext().getString(R.string.debug_ui_redesign_info_box_title))
        binding.infoBox19.setInfoText(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        binding.infoBox20.setInfoTextWithLink(getSpannableString())

        binding.infoBox21.setInfoText("${requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text)} : Full Width Info Box")
        binding.infoBox22.setInfoText("${requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text)} : Info Box with no container")
        binding.infoBox23.setInfoText("${requireContext().getString(R.string.debug_ui_redesign_info_box_inline_text)} : Inline")
    }

    private fun getSpannableString(): SpannableString {
        val ss =
            SpannableString(requireContext().getString(R.string.debug_ui_redesign_info_box_simple_text))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            }
        }
        ss.setSpan(clickableSpan, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ss
    }
}