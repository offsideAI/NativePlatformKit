package ai.offside.mobile.android.helper.testlabs.nav.buttons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignMainPageBinding

class TestTakeoverStickyButtonsMainFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignMainPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listview.adapter =
            ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignThemeListAdapter(
                resources.getStringArray(
                    R.array.layouts_array
                )
            ) { position ->
                when (position) {
                    0 -> {
                        val dialogFragment = TestTakeoverPrimaryFragment()
                        dialogFragment.show(
                            childFragmentManager,
                            TAG_STICKY_PRIMARY_BUTTON
                        )
                    }

                    1 -> {
                        val dialogFragment = TestTakeoverPrimarySecondaryHorizontalFragment()
                        dialogFragment.show(
                            childFragmentManager,
                            TAG_STICKY_BUTTONS_HORIZONTALLY_STACKED
                        )                    }

                    2 -> {
                        val dialogFragment =  TestTakeoverPrimarySecondaryVerticalFragment()
                        dialogFragment.show(
                            childFragmentManager,
                            TAG_STICKY_BUTTONS_VERTICALLY_STACKED
                        )
                    }

                    3 -> {
                        val dialogFragment = TestTakeoverPrimaryTertiaryFragment()
                        dialogFragment.show(
                            childFragmentManager,
                            TAG_STICKY_BUTTONS_TERTIARY_STACKED
                        )
                    }

                }
            }
    }
    companion object {
        private const val TAG_STICKY_PRIMARY_BUTTON = "TAG_STICKY_PRIMARY_BUTTON"
        private const val TAG_STICKY_BUTTONS_HORIZONTALLY_STACKED =
            "TAG_STICKY_BUTTONS_HORIZONTALLY_STACKED"
        private const val TAG_STICKY_BUTTONS_VERTICALLY_STACKED =
            "TAG_STICKY_BUTTONS_VERTICALLY_STACKED"
        private const val TAG_STICKY_BUTTONS_TERTIARY_STACKED =
            "TAG_STICKY_BUTTONS_TERTIARY_STACKED"
    }
}