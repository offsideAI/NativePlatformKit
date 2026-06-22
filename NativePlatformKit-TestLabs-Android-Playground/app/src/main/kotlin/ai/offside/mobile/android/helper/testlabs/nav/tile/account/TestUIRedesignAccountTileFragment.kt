package ai.offside.mobile.android.helper.testlabs.nav.tile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ai.offside.mobile.android.component.ui.tile.account.recyclerview.AccountTileRecyclerViewLayoutManager
import ai.offside.mobile.android.component.ui.tile.account.recyclerview.AccountTileWithSliderRecyclerAdapter
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestAccountTileBinding

class TestUIRedesignAccountTileFragment : Fragment() {
    private lateinit var binding: FragmentTestAccountTileBinding
    private val bottomSheet = AccountTileTestBottomSheetFragment()
    private lateinit var viewModel: TestUIRedesignAccountTileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentTestAccountTileBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = ViewModelProvider(this)[TestUIRedesignAccountTileViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accountsLayoutManager = AccountTileRecyclerViewLayoutManager(requireContext())
        val adapter = AccountTileWithSliderRecyclerAdapter(accountsLayoutManager)
        binding.accountsRecyclerView.layoutManager = accountsLayoutManager
        binding.accountsRecyclerView.adapter = adapter
        viewModel.accounts.observe(viewLifecycleOwner) {
            adapter.setAccountTileItems(it)
        }
    }
}