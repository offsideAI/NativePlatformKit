package ai.offside.mobile.android.helper.testlabs.nav.modals.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetFlowDialogFragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBottomSheetLayoutBinding

class TestBottomSheetMainFragment : Fragment() {
    private lateinit var binding: FragmentTestBottomSheetLayoutBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBottomSheetLayoutBinding.bind(
            inflater.inflate(
                R.layout.fragment_test_bottom_sheet_layout,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnStandardBottomsheet.setOnClickListener {
            navController.navigate(R.id.action_testBottomSheetMainFragment_to_testBottomSheetStandardFragment)
        }

        binding.btnPersistentBottomsheet.setOnClickListener {
            val args = Bundle()
            args.putString(BottomSheetFlowDialogFragment.Offside_BOTTOM_SHEET_BUNDLE_KEY_SUBTITLE,"")
            navController.navigate(R.id.action_testBottomSheetMainFragment_to_testBottomSheetPersistentFragment, args)
        }

        binding.btnPersistentSubheaderBottomsheet.setOnClickListener {
            navController.navigate(R.id.action_testBottomSheetMainFragment_to_testBottomSheetPersistentFragment)
        }

        binding.btnFlowBottomsheet.setOnClickListener {
            navController.navigate(R.id.action_testBottomSheetMainFragment_to_testBottomSheetFlowFragment)
        }
    }
}