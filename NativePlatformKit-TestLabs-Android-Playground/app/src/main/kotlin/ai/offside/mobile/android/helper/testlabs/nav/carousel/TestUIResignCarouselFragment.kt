package ai.offside.mobile.android.helper.testlabs.nav.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ai.offside.mobile.android.component.ui.imagetile.ImageTileState
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignCarouselBinding
import ai.offside.mobile.android.helper.testlabs.databinding.TestUiRedesignCarouselLayoutBinding

class TestUIResignCarouselFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignCarouselBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignCarouselBinding.inflate(inflater, container, false)
        initViewPager()
        return binding.root
    }

    /**
     * Initialize carousel view pager
     */
    private fun initViewPager() {
        val carouselOptionsTwoAdapter = CarouselCardTileAdapter()
        carouselOptionsTwoAdapter.addItems(getCardTilesList(2))
        binding.carouselOptionsTwo.viewPager.adapter = carouselOptionsTwoAdapter
        bindTabLayoutViewPager(binding.carouselOptionsTwo)

        val carouselOptionsThreeAdapter = CarouselCardTileAdapter()
        carouselOptionsThreeAdapter.addItems(getCardTilesList(3))
        binding.carouselOptionsThree.viewPager.adapter = carouselOptionsThreeAdapter
        bindTabLayoutViewPager(binding.carouselOptionsThree)

        val carouselOptionsFourAdapter = CarouselCardTileAdapter()
        carouselOptionsFourAdapter.addItems(getCardTilesList(4))
        binding.carouselOptionsFour.viewPager.adapter = carouselOptionsFourAdapter
        bindTabLayoutViewPager(binding.carouselOptionsFour)

        val carouselOptionsFiveAdapter = CarouselCardTileAdapter()
        carouselOptionsFiveAdapter.addItems(getCardTilesList(5))
        binding.carouselOptionsFive.viewPager.adapter = carouselOptionsFiveAdapter
        bindTabLayoutViewPager(binding.carouselOptionsFive)

        val carouselOptionsSixAdapter = CarouselCardTileAdapter()
        carouselOptionsSixAdapter.addItems(getCardTilesList(6))
        binding.carouselOptionsSix.viewPager.adapter = carouselOptionsSixAdapter
        bindTabLayoutViewPager(binding.carouselOptionsSix)
    }

    /**
     * Bind/Attach Tab layout and view pager
     *
     * @param carouselViewBinding carousel layout to bind
     */
    private fun bindTabLayoutViewPager(carouselViewBinding: TestUiRedesignCarouselLayoutBinding) {
        TabLayoutMediator(carouselViewBinding.tabLayout, carouselViewBinding.viewPager) { tab, _ ->
            tab.contentDescription = resources.getString(R.string.debug_ui_redesign_image_tile_title)
        }.attach()
    }

    /**
     * Gets card tiles for carousel view
     *
     * @param count number of cards tiles needed for the carousel
     */
    private fun getCardTilesList(count: Int): List<ImageTileState> {
        val tileList = mutableListOf<ImageTileState>()
        for (i in 1 .. count) {
            tileList.add(ImageTileState.ImageTile(resources.getString(R.string.async_url), resources.getString(R.string.debug_ui_redesign_image_tile_title)))
        }
        return tileList
    }
}