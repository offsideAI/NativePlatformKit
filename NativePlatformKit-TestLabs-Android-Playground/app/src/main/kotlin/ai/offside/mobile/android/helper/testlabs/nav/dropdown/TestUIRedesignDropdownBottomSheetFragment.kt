package ai.offside.mobile.android.helper.testlabs.nav.dropdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetViewModel
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData
import ai.offside.mobile.android.component.ui.listitem.data.getRadioGroupListItem
import ai.offside.mobile.android.component.ui.listitem.recycler.ListItemRecyclerAdapter
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestDropdownBottomSheetBinding

/**
 * Fragment class for Dropdown BottomSheet component
 *  * @param context
 *  * @param attrs
 */
class TestUIRedesignDropdownBottomSheetFragment : Fragment() {

    private var _binding: FragmentTestDropdownBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val bottomSheetViewModel: BottomSheetViewModel by viewModels({requireActivity()})
    private var bottomSheetSelectedRadioItem: CompoundButtonData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestDropdownBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val _radioListItem = MutableLiveData<ListItemData.RadioGroupListItem>().apply {
        value = getRadioGroupListItem(
            "",
            listOf(
                CompoundButtonData(
                    "Ecommerce Wallet Spend x1234",
                ),
                CompoundButtonData(
                    "Ecommerce Wallet Reserve x4783",
                    "Available Balance :$3.234",
                ),
                CompoundButtonData(
                    "Ecommerce Wallet Growth x3212",
                    "Available Balance: $77753.23",
                    "Free Until March: 12, 23"
                )
            ),
        ) { buttonGroup, selectedIndex ->
            bottomSheetSelectedRadioItem = radioListItem.value?.items?.get(selectedIndex)
        }
    }

    val radioListItem: LiveData<ListItemData.RadioGroupListItem> = _radioListItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioAdapter = ListItemRecyclerAdapter()
        binding.radioRecyclerView.adapter = radioAdapter

        radioListItem.observe(viewLifecycleOwner) {
            radioAdapter.setListItems(listOf(it))
        }

        //on "Select" clicked update the bottomsheet selected data
        binding.bottomSheetSelectButton.setOnClickListener {
            bottomSheetViewModel.bottomSheetData.postValue(bottomSheetSelectedRadioItem)
            bottomSheetViewModel.dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}