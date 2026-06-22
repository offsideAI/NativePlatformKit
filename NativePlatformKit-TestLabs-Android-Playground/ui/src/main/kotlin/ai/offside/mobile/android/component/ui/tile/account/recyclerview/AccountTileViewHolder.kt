package ai.offside.mobile.android.component.ui.tile.account.recyclerview

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.slider.SliderComponentLayout
import ai.offside.mobile.android.component.ui.tile.account.AccountTileLayout
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileButton
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileData
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileDataModel

/**
 * View Holder class for account tile with sliders
 *
 * @param sliderComponentLayout
 */
class AccountTileViewHolder(private val sliderComponentLayout: SliderComponentLayout) :
    RecyclerView.ViewHolder(sliderComponentLayout) {

    fun bindView(accountTileData: AccountTileDataModel) {
        resetView(accountTileData)
        updateAccessibilityAction(accountTileData.tileData)
    }

    /**
     * Removes all view in main content and adds new [AccountTileLayout]
     *
     * @param accountTileData
     */
    private fun resetView(accountTileData: AccountTileDataModel) {
        val accountTileLayout = AccountTileLayout(sliderComponentLayout.context)
        accountTileLayout.setAccountTileData(accountTileData)
        sliderComponentLayout.setSliderComponentData(accountTileData.sliderData)
        sliderComponentLayout.setMainContent(accountTileLayout)
        sliderComponentLayout.setMainContentClickAction { accountTileData.onClick(accountTileData) }
    }

    /**
     * Updates talkback accessibility action if the following widgets are added to the tile
     *
     * @param accountTile
     */
    private fun updateAccessibilityAction(accountTile: AccountTileData) {
        if (accountTile is AccountTileData.AccountTileWidgetMoreInfoButton) {
            addTalkbackAction(accountTile.moreButton)
        }
        if (accountTile is AccountTileData.AccountTileWidgetPrimaryMicroButton) {
            addTalkbackAction(accountTile.primaryButtonData)
        }
        if (accountTile is AccountTileData.AccountTileWidgetAlternateMicroButton) {
            addTalkbackAction(accountTile.alternateButton)
        }
        if (accountTile is AccountTileData.AccountTileWidgetPricingUpdate) {
            addTalkbackAction(accountTile.learnMoreButton)
        }
    }

    /**
     * Adds accessibility talkback action to main content view of slider
     *
     * @param tileButton [AccountTileButton]
     */
    private fun addTalkbackAction(tileButton: AccountTileButton) {
        ViewCompat.addAccessibilityAction(
            sliderComponentLayout.binding.mainContent,
            tileButton.label
        ) { view, _ ->
            tileButton.onButtonClick(view)
            true
        }
    }
}