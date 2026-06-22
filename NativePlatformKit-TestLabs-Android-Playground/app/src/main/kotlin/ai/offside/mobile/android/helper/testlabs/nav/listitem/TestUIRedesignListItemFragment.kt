package ai.offside.mobile.android.helper.testlabs.nav.listitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ai.offside.mobile.android.component.ui.bottomsheet.BottomSheetFlowDialogFragment
import ai.offside.mobile.android.component.ui.listitem.recycler.ListItemRecyclerAdapter
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignListItemBinding

class TestUIRedesignListItemFragment : Fragment() {
    private var _binding: FragmentTestUiRedesignListItemBinding? = null
    val binding: FragmentTestUiRedesignListItemBinding get() = _binding!!

    private lateinit var viewModel: TestUIRedesignListItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestUiRedesignListItemBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[TestUIRedesignListItemViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomSheetButton.setOnClickListener {
            val bottomSheet = BottomSheetFlowDialogFragment(
                navGraphId = R.navigation.component_ui_list_item_bottom_sheet_nav_graph
            )
            bottomSheet.show(childFragmentManager, "")
        }

        val emphasizedListAdapter = ListItemRecyclerAdapter()
        binding.emphasizedListItems.adapter = emphasizedListAdapter
        viewModel.emphasizedListItem.observe(viewLifecycleOwner) { listItems ->
            emphasizedListAdapter.setListItems(listItems)
        }

        val adapter = ListItemRecyclerAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.listItem.observe(viewLifecycleOwner) { listItems ->
            adapter.setListItems(listItems)
        }

        val buttonAdapter = ListItemRecyclerAdapter()
        binding.listItemWithButtonRecyclerView.adapter = buttonAdapter
        viewModel.buttonsListItem.observe(viewLifecycleOwner) { listItems ->
            buttonAdapter.setListItems(listItems)
        }

        val radioAdapter = ListItemRecyclerAdapter()
        binding.radioRecyclerView.adapter = radioAdapter
        viewModel.radioListItem.observe(viewLifecycleOwner) {
            binding.radioGroupLabel.text = it.label
            radioAdapter.setListItems(listOf(it))
        }

        val checkboxAdapter = ListItemRecyclerAdapter()
        binding.checkboxRecyclerView.adapter = checkboxAdapter
        viewModel.checkboxItem.observe(viewLifecycleOwner) {
            binding.checkboxGroupLabel.text = it.label
            checkboxAdapter.setListItems(listOf(it))
        }
    }
}