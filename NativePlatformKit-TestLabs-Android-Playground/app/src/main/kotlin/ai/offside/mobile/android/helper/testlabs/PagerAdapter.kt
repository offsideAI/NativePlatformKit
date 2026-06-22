package ai.offside.mobile.android.helper.testlabs

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ai.offside.mobile.android.helper.testlabs.nav.TestUIRedesignButtonsFragment
import ai.offside.mobile.android.helper.testlabs.nav.TestUIRedesignTypographyFragment

class  PagerAdapter(private val context: Context, fm: FragmentManager) :
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val listOfTitles =
        arrayOf(
            R.string.debug_ui_redesign_tab_typography,
            R.string.debug_ui_redesign_tab_buttons,
            R.string.debug_ui_redesign_tab_components, //text_input, radiobutton, checkbox, cards
            R.string.debug_ui_redesign_tab_modals, // Modals, BottomSheet, Dialog, SnackBar
            R.string.debug_ui_redesign_tab_misc
        )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> {
                TestUIRedesignButtonsFragment.newInstance()
            }
            0 -> {
                TestUIRedesignTypographyFragment.newInstance()
            }
            2 -> {
                TestUIRedesignTypographyFragment.newInstance()
            }
           /* 3 -> {
               MaterialCardsDemoFragment.newInstance()
            }
            4 -> {
                MaterialModalsFragment.newInstance()
            }
            5 -> {
                MaterialMiscWidgetsFragment.newInstance()
            }*/
            else -> {
                TestUIRedesignButtonsFragment.newInstance()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(listOfTitles[position])
    }

    override fun getCount(): Int {
        return listOfTitles.size
    }

}