package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignButtonMainPageBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignButtonMainPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignButtonMainPageFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignButtonMainPageBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignButtonMainPageBinding.inflate(inflater, container, false)
        binding.option1.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testPrimaryButtonsFragment)
        }
        binding.option2.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testSecondaryAndTertiaryButtonFragment)
        }
        binding.option3.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testMicroButtonsFragment)
        }
        binding.option4.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testUIRedesignJumboButtonsFragment)
        }
        binding.option5.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testCompoundButtonsFragmentFragment)
        }
        binding.option7.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testGroupedCheckBoxesFragment)
        }
        binding.option8.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testIconLinkButtonFragment)
        }
        binding.option9.setOnClickListener(){
            navController.navigate(R.id.action_testUIRedesignButtonMainPageFragment_to_testTakeoverStickyButtonFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignButtonMainPageFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignButtonMainPageFragment()
    }
}