package ai.offside.mobile.android.helper.testlabs.nav.modals.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetFlowDialogFragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBottomSheetHostLayoutBinding

class TestBottomSheetFlowFragment :Fragment(){
    private lateinit var binding: FragmentTestBottomSheetHostLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBottomSheetHostLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            val flowBottomSheet = BottomSheetFlowDialogFragment(
                navGraphId = R.navigation.component_ui_bottom_sheet_flow_nav_graph
            )
            flowBottomSheet.show(childFragmentManager,"")
        }

    }

}