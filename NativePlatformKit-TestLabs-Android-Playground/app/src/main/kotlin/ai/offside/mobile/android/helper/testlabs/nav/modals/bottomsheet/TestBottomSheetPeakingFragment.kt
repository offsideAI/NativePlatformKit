package ai.offside.mobile.android.helper.testlabs.nav.modals.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetPeekFragment
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBottomSheetContentListLayoutBinding
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBottomSheetPeakingLayoutBinding
import ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignListAdapter
import ai.offside.mobile.android.helper.testlabs.nav.modals.data.nearByAtmsList

class TestBottomSheetPeakingFragment : Fragment() {
    private lateinit var binding: FragmentTestBottomSheetPeakingLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBottomSheetPeakingLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val peakingBottomSheetLayoutBinding = binding.peakingBottomSheetFragment
        val contentBinding = FragmentTestBottomSheetContentListLayoutBinding.inflate(
            LayoutInflater.from(requireContext())
        )
        contentBinding.listview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RedesignListAdapter(nearByAtmsList) { onItemClick() }
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        peakingBottomSheetLayoutBinding.getFragment<BottomSheetPeekFragment>()
            .setContentView(contentBinding.root)

        if(arguments != null){
            binding.peakingBottomSheetFragment.getFragment<BottomSheetPeekFragment>().hideSubTitle()
        }
    }

    private fun onItemClick() {
        Toast.makeText(requireContext(), "Clicked Item", Toast.LENGTH_SHORT).show()
    }
}