package ai.offside.mobile.android.helper.testlabs.nav.listitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ai.offside.mobile.android.component.ui.listitem.recycler.ListItemRecyclerAdapter
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestListItemBottomSheetBinding

class TestBottomSheetListItemFragment : Fragment() {
    private var _binding: FragmentTestListItemBottomSheetBinding? = null
    val binding: FragmentTestListItemBottomSheetBinding get() = _binding!!

    private lateinit var viewModel: TestUIRedesignListItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestListItemBottomSheetBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[TestUIRedesignListItemViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetRadioAdapter = ListItemRecyclerAdapter()
        binding.recyclerView.adapter = bottomSheetRadioAdapter
        viewModel.listItem.observe(viewLifecycleOwner) {
            bottomSheetRadioAdapter.setListItems(it)
        }
    }
}