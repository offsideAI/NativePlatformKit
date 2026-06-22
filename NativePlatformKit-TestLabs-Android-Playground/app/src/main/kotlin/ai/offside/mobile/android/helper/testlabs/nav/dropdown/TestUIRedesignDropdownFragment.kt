package ai.offside.mobile.android.helper.testlabs.nav.dropdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetFlowDialogFragment
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetViewModel
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData
import ai.offside.mobile.android.component.ui.dropdown.DropdownData
import ai.offside.mobile.android.component.ui.dropdown.DropdownDataModel
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestDropdownLayoutBinding

/**
 * Fragment class that holds example Dropdown component
 */
class TestUIRedesignDropdownFragment : Fragment() {

    private var _binding: FragmentTestDropdownLayoutBinding? = null
    private val binding get() = _binding!!

    private val bottomSheetViewModel: BottomSheetViewModel by viewModels({ requireActivity() })

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestDropdownLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val dropdownModel = DropdownDataModel(
        data = object : DropdownData {
            override val label = "From Account"
            override var selectedItem: CompoundButtonData = CompoundButtonData("")
            override fun onClick(view: View) {
                openDropdownBottomSheet()
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //set dropdown data
        binding.dropdown.setDropdownData(
            dropdownModel
        )

        var isDropdownActive = false
        //listener to set dropdown to inactive/active on button click
        binding.setInvactiveButton.setOnClickListener {
            binding.dropdown.setDropdownEnabled(isDropdownActive)
            isDropdownActive = !isDropdownActive
        }
    }

    private fun openDropdownBottomSheet() {
        val dropdownBottomSheet = BottomSheetFlowDialogFragment(
            navGraphId = R.navigation.component_ui_dropdown_bottom_sheet_nav_graph
        )
        dropdownBottomSheet.show(childFragmentManager, "")

        //update selected item from bottomsheet selection
        bottomSheetViewModel.bottomSheetData.observe(
            viewLifecycleOwner
        ) { item ->
            if (item is CompoundButtonData) {
                dropdownModel.setItem(item)
                binding.dropdown.setDropdownData(dropdownModel)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bottomSheetViewModel.bottomSheetData.postValue(null)
    }

}