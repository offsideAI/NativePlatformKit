package ai.offside.mobile.android.helper.testlabs.nav

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

/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignMainPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignMainPageFragment : Fragment() {

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
        binding.listview.adapter = RedesignThemeListAdapter(resources.getStringArray(R.array.debug_ui_redesign_theme_group_list)) { position ->
            when(position) {
                //Themse
                0 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignThemeFragment)
                //Typography
                1 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignTypographyDemoFragment)
                //Buttons
                2 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignButtonMainPageFragment)
                //Modals
                3 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignModelMainPageFragment)
                //Slider
                4 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testSliderFragment)
                //Controls
                5 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignControlsFragment)
                //Info Boxes
                6 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignInfoBoxFragment)
                //Amount Input Field
                7 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignAmountInputFieldFragment)
                //Card Specific Input Field
                8 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignCardTileFragment)
                //Card Tile Component
                9 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignImageTileDemoFragment)
                //Stepper
                10 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignStepperFragment)
                //Review Filed Tile
                11 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testReviewFiledTileFragment)
                //WebViews
                12 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignWebViewsFragment)
                //Account Tile
                13 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testAccountTileFragment)
                //OnlinePayments Tile
                14 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testOnlinePaymentsTileFragment)
                //Carousel
                15 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignCarouselFragment)
                //Badges
                16 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testFragmentBadgesFragment)
                //Card Worksheet
                17 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testCardWorkSheetFragment)
                //Actionable Icons
                18 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignActionableIconsFragment)
                //Input Fields
                19 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignInputFieldFragment)
                //Container
                20 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignContainerFragment)
                //Decorative Icons
                21 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignIconsDecorativeFragment)
                //Informative Icons
                22 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testInformativeIconsFragment)
                //FeatureTile
                23 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testFeatureTileFragment)
                //ListItem
                24 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignListItemFragment)
                //FeatureBasedIcons
                25 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignFeatureBasedIconsFragment)
                //Account Transactions Tile
                26 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignAccountTransactionTilesFragment)
                //List Actions
                27 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignListActionsFragment)
                //Ecommerce Badges
                28 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignEcommerceBadgeFragment)
                //Ecommerce Tile
                29 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignEcommerceTileFragment)
                //List Actions
                30 -> navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignTransferActivityTilesFragment)
                //Dropdown Field
                31 ->  navController.navigate(R.id.action_testUIRedesignMainPageFragment_to_testUIRedesignDropdownFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignMainPageFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignMainPageFragment()
    }
}