package ai.offside.mobile.android.helper.testlabs.nav.modals.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetFlowDialogFragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignListAdapter
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBottomSheetContentListLayoutBinding
import ai.offside.mobile.android.helper.testlabs.nav.modals.data.accountListWithBalance

class TestBottomSheetListFragment:Fragment() {
    private lateinit var binding: FragmentTestBottomSheetContentListLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBottomSheetContentListLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RedesignListAdapter(accountListWithBalance) { onItemClick() }
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

    }

    private fun onItemClick(){
        val navController = findNavController()
        val args = Bundle()
        args.putString(BottomSheetFlowDialogFragment.Offside_BOTTOM_SHEET_BUNDLE_KEY_SUBTITLE,resources.getString(R.string.debug_ui_redesign_accept_terms))
        navController.navigate(R.id.action_testBottomSheetListFragment_to_testBottomSheetDetailsFragment,args)
    }
}