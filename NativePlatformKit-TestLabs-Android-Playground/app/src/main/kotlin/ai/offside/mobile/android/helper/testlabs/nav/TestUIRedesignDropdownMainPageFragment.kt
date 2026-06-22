package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestDropdownMainPageBinding

class TestUIRedesignDropdownMainPageFragment:Fragment() {

    private var _binding: FragmentTestDropdownMainPageBinding? = null
    private val binding: FragmentTestDropdownMainPageBinding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestDropdownMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = androidx.navigation.Navigation.findNavController(view)
        binding.dropdownListview.adapter =
            ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignThemeListAdapter(
                resources.getStringArray(
                    ai.offside.mobile.android.helper.testlabs.R.array.debug_ui_redesign_dropdown_components_list
                )
            ) { position ->
                when (position) {
                    0 -> navController.navigate(ai.offside.mobile.android.helper.testlabs.R.id.action_testUIRedesignDropdownMainPageFragment_to_testUIRedesignDropdownFragment)
                    1 -> navController.navigate(ai.offside.mobile.android.helper.testlabs.R.id.action_testUIRedesignDropdownMainPageFragment_to_testUIRedesignExpandCollapseFragment)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}