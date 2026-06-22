package ai.offside.mobile.android.helper.testlabs.nav.badge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.badges.NotificationBadge
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestNotificationBadgeBinding
import ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity

class TestFragmentNotificationBadgeFragment : Fragment() {
    private lateinit var binding: FragmentTestNotificationBadgeBinding
    private lateinit var toolBar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestNotificationBadgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = (requireActivity() as TestlabsMainActivity).getToolBar()

        val badgeSopNormal = NotificationBadge()
        badgeSopNormal.apply {
            addBadge(binding.btnSopNormal, binding.btnSopNormalBackground)
            setBadgeNumber(28)
        }

        //Badge Notification for Navigation Menu
        val accountBadge = NotificationBadge()
        accountBadge.apply {
            addBadgeToNavMenu(
                binding.notificationBottomNavigation,
                R.id.menu_item_account
            )
            setBadgeNumber(12)
        }

        val rewardsBadge = NotificationBadge()
        rewardsBadge.apply {
            addBadgeToNavMenu(
                binding.notificationBottomNavigation,
                R.id.menu_item_rewards
            )
            setBadgeNumber(1100)
        }
        val cardsBadge = NotificationBadge()
        cardsBadge.apply {
            addBadgeToNavMenu(
                binding.notificationBottomNavigation,
                R.id.menu_item_cards
            )
            setBadgeNumber(8)
        }
        val ecommerceBadge = NotificationBadge()
        ecommerceBadge.apply {
            addBadgeToNavMenu(
                binding.notificationBottomNavigation,
                R.id.menu_item_ecommerce
            )
        }

        //Toolbar Menu
        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {
                val toolBarBadge = NotificationBadge()
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

                override fun onPrepareMenu(menu: Menu) {
                    super.onPrepareMenu(menu)
                    menu.findItem(R.id.menu_item_more).isVisible = true
                    toolBarBadge.apply {
                        addBadgeToToolBarMenu(
                            toolBar,
                            R.id.menu_item_more
                        )
                        setBadgeNumber(3)
                    }
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_item_more) {
                        toolBarBadge.removeBadge()
                    }
                    return true
                }


            }
        )
        //TabBar Layout
        val tabLayoutBadge = NotificationBadge()
        tabLayoutBadge.apply {
            addBadgeToTabBar(binding.tabBarLayout, 0)
            setBadgeNumber(2)
        }

        //Image
        NotificationBadge().apply {
            addBadge(binding.profileIcon, binding.btnProfileIconBackground)
            setBadgeNumber(200)
            adjustBadgeOffsetValue(verticalOffsetValue = 30, horizontalOffsetValue = 50)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        toolBar.menu.findItem(R.id.menu_item_more).isVisible = false
    }

}