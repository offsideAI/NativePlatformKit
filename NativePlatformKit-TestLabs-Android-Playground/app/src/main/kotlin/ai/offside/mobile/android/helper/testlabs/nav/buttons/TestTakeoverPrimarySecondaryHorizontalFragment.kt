package ai.offside.mobile.android.helper.testlabs.nav.buttons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestTakeoverWithStickyButtonsHorizontalBinding

class TestTakeoverPrimarySecondaryHorizontalFragment: DialogFragment() {

    private lateinit var binding: FragmentTestTakeoverWithStickyButtonsHorizontalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, ai.offside.mobile.android.component.ui.R.style.Theme_Offside_Base)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestTakeoverWithStickyButtonsHorizontalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.takeoverCloseButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.stickyButtonsContainer.primaryButton.text = getString(R.string.takeover_sticky_button_confirm)
        binding.stickyButtonsContainer.primaryButton.setOnClickListener {
            Toast.makeText(context, "Primary Button Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.stickyButtonsContainer.secondaryButton.text = getString(R.string.takeover_sticky_button_cancel)
        binding.stickyButtonsContainer.secondaryButton.setOnClickListener {
            Toast.makeText(context, "Secondary Button Clicked", Toast.LENGTH_SHORT).show()
        }

    }

}