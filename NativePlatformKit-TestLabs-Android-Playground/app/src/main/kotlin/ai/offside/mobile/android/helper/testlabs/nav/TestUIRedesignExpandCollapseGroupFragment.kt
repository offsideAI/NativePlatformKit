package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.listitem.data.getSimpleListItem
import ai.offside.mobile.android.component.ui.listitem.recycler.ListItemRecyclerAdapter
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestExpandCollapseGroupBinding

class TestUIRedesignExpandCollapseGroupFragment: Fragment() {

    private var _binding: FragmentTestExpandCollapseGroupBinding? = null
    private val binding get() = _binding!!

    val listItems = listOf(
        getSimpleListItem(
            "List Item",
        ) {},
        getSimpleListItem(
            "List Item",
            secondaryText = "Secondary Text"
        ) {}
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestExpandCollapseGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = ListItemRecyclerAdapter()
        adapter.setListItems(listItems)

        binding.expandCollapseGroup.setGroupData("Group", binding.expandCollapseListContainer)
        binding.expandCollapseRecyclerView.adapter = adapter

        binding.expandCollapseSecondGroup.setGroupData("Another Group", binding.expandCollapseSecondListContainer)
        binding.expandCollapseSecondRecyclerView.adapter = adapter

    }
}