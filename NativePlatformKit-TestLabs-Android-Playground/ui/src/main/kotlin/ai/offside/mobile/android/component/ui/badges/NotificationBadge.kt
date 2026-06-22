package ai.offside.mobile.android.component.ui.badges

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.OptIn
import androidx.appcompat.widget.Toolbar
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.color.MaterialColors
import com.google.android.material.navigation.NavigationBarView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.segment.TabBarLayout

@OptIn(ExperimentalBadgeUtils::class)
class NotificationBadge {
    private lateinit var badge: BadgeDrawable
    private var isCustomViewBadge = false


    /**
     *  Add Badge for Generic View components eg: Buttons
     *  @param anchorView - Any View Component
     *  @param parent - View Should be Wrapped inside the FrameLayout
     */
    fun addBadge(anchorView: View, parent: FrameLayout) {
        isCustomViewBadge = true
        badge = createBadge(anchorView.context)
        BadgeUtils.attachBadgeDrawable(
            badge.apply {
                adjustBadgeOffsetValue()
            },
            anchorView.apply {
                isFocusable = true
                setContentDescriptionForCustomViewBadge(anchorView)
            },
            parent.apply {
                clipChildren = false
                clipToPadding = false
            }
        )
        anchorView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            badge.updateBadgeCoordinates(anchorView, parent)
        }
    }

    /**
     *  Add Badge for Navigation menu
     *  @param navView - NavigationBarView
     *  @param menuItemId - Menu id of the Navigation Bar
     */
    fun addBadgeToNavMenu(navView: NavigationBarView, @IdRes menuItemId: Int) {
        badge = navView.getOrCreateBadge(menuItemId)
    }

    /**
     *  Add Badge for Toolbar menu
     *  @param toolBar - Toolbar
     *  @param menuItemId - Menu id of the ToolBar menu
     */
    fun addBadgeToToolBarMenu(toolBar: Toolbar, @IdRes menuItemId: Int) {
        badge = createBadge(toolBar.context)
        BadgeUtils.attachBadgeDrawable(badge, toolBar, menuItemId)
    }

    /**
     *  Add Badge for TabBar menu
     *  @param tabBarLayout - TabBarLayout
     *  @param tabPosition - Position of Tab
     */
    fun addBadgeToTabBar(tabBarLayout: TabBarLayout, tabPosition: Int) {
        val tabBadge = tabBarLayout.getTabAt(tabPosition)?.orCreateBadge
        if (tabBadge != null) {
            badge = tabBadge.apply {
                backgroundColor = MaterialColors.getColor(tabBarLayout, R.attr.offside_error)
                badgeTextColor =
                    MaterialColors.getColor(tabBarLayout, R.attr.offside_onSecondaryContainer)
            }
        }
    }

    /**
     *  Set badgeNumber in badgeDrawable
     *  @param badgeNumber - Number to display on the badge
     */
    fun setBadgeNumber(badgeNumber: Int) {
        badge.apply {
            isVisible = true
            number = badgeNumber
            adjustHorizontalOffsetForMaxChar()
        }
    }

    /**
     *  Get badgeNumber from badgeDrawable
     *  @return - current Badge Number
     */
    fun getBadgeNumber(): Int = badge.number

    /**
     *  Remove the badge Added to the targetView
     */
    fun removeBadge() {
        badge.isVisible = false
    }

    private fun createBadge(context: Context): BadgeDrawable =
        BadgeDrawable.create(context)

    /**
     *  Adjust the vertical and horizontalOffset of badge
     */
    fun adjustBadgeOffsetValue(
        verticalOffsetValue: Int = BADGE_VERTICAL_OFFSET_FOR_CUSTOM_VIEW,
        horizontalOffsetValue: Int = BADGE_HORIZONTAL_OFFSET_FOR_CUSTOM_VIEW
    ) {
        badge.apply {
            verticalOffset = verticalOffsetValue
            horizontalOffset = horizontalOffsetValue
        }
    }

    /**
     *  Adjust the horizontalOffset value for max badgeCount
     */
    private fun adjustHorizontalOffsetForMaxChar() {
        if (isCustomViewBadge) {
            when {
                (badge.number.toString().length >= MAX_BADGE_COUNT) -> BADGE_HORIZONTAL_OFFSET_FOR_CUSTOM_VIEW_MAX_CHAR
                else -> BADGE_HORIZONTAL_OFFSET_FOR_CUSTOM_VIEW
            }.also {
                badge.horizontalOffset = it
            }
        }
    }

    /**
     *  Set ContentDescription text for Custom View Badge
     *  @param anchorView - CustomView
     */
    private fun setContentDescriptionForCustomViewBadge(anchorView: View) {
        ViewCompat.setAccessibilityDelegate(anchorView, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                if (badge.isVisible && !host.contentDescription.isNullOrEmpty()) {
                    val customContentDescription = host.contentDescription.toString()
                    info.contentDescription =
                        customContentDescription.plus(CONTENT_DESCRIPTION_PAUSE_CHAR)
                            .plus(badge.contentDescription)
                }
            }
        })
    }

    companion object {
        private const val BADGE_VERTICAL_OFFSET_FOR_CUSTOM_VIEW = 40
        private const val BADGE_HORIZONTAL_OFFSET_FOR_CUSTOM_VIEW = 40
        private const val BADGE_HORIZONTAL_OFFSET_FOR_CUSTOM_VIEW_MAX_CHAR = 60
        private const val CONTENT_DESCRIPTION_PAUSE_CHAR = ", "
        private const val MAX_BADGE_COUNT = 3
    }
}