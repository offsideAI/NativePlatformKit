package ai.offside.mobile.android.helper.testlabs.nav.controls

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignControlsBinding
import ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignThemeListAdapter



/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignControlsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignControlsFragment : Fragment() {
    private var _binding: FragmentTestUiRedesignControlsBinding? = null
    private val binding: FragmentTestUiRedesignControlsBinding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestUiRedesignControlsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.listview.adapter = RedesignThemeListAdapter(resources.getStringArray(R.array.debug_ui_redesign_controls_list)) { position ->
            when(position) {
                0 -> navController.navigate(R.id.action_testUIRedesignControlsFragment_to_testUIRedesignSegmentBarFragment)
                1 -> navController.navigate(R.id.action_testUIRedesignControlsFragment_to_testUIRedesignProfileAndSettingsBarFragment)
                2 -> navController.navigate(R.id.action_testUIRedesignControlsFragment_to_testUIRedesignSearchBarStandardFragment)
                3 -> navController.navigate(R.id.action_testUIRedesignControlsFragment_to_testUIRedesignSearchBarElevatedFragment)
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignControlsFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignControlsFragment()
    }
}