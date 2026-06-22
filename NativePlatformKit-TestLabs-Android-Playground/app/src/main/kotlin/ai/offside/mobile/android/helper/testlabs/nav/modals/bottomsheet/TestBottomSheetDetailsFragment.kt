package ai.offside.mobile.android.helper.testlabs.nav.modals.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetViewModel
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBottomSheetContentLayoutBinding

class TestBottomSheetDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTestBottomSheetContentLayoutBinding
    private val viewModel:BottomSheetViewModel by viewModels({requireActivity()})
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBottomSheetContentLayoutBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAgree.setOnClickListener {
            viewModel.dismiss()
        }
    }
}