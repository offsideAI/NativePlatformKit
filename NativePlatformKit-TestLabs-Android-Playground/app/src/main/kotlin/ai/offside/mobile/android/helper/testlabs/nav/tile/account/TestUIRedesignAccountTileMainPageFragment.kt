package ai.offside.mobile.android.helper.testlabs.nav.tile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignMainPageBinding
import ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignThemeListAdapter

class TestUIRedesignAccountTileMainPageFragment : Fragment() {
    private var _binding: FragmentTestUiRedesignMainPageBinding? = null
    private val binding: FragmentTestUiRedesignMainPageBinding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTestUiRedesignMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.listview.adapter =
            RedesignThemeListAdapter(resources.getStringArray(R.array.debug_ui_redesign_account_tile_group_list)) { position ->
                when (position) {
                    0 -> navController.navigate(R.id.action_testUIRedesignAccountTileMainPageFragment_to_testUIRedesignAccountTileFragment)
                    1 -> navController.navigate(R.id.action_testUIRedesignAccountTileMainPageFragment_to_testUIRedesignOffsidePaymentAccountTileFragment)
                    2 -> navController.navigate(R.id.action_testUIRedesignAccountTileMainPageFragment_to_testEcommerceWalletToolSetFragment)
                    3 -> navController.navigate(R.id.action_testAccountTileDemoMainFragment_to_testDepositAccountTileDemoFragment)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}