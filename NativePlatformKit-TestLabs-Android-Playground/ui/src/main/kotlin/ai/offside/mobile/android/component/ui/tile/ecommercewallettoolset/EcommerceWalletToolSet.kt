package ai.offside.mobile.android.component.ui.tile.ecommercewallettoolset

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.EcommerceWalletToolSetBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData
import ai.offside.mobile.android.component.ui.listitem.recycler.ListItemRecyclerAdapter
import ai.offside.mobile.android.component.ui.tile.ecommercewallettoolset.adapter.EcommerceWalletToolSetAdapter
import ai.offside.mobile.android.component.ui.tile.ecommercewallettoolset.data.EcommerceWalletToolSetData

/**
 * [EcommerceWalletToolSet] responsible for displaying the virtual wallet account tool-set features
 * [setFeatureList] will check features list and based on the size it will display the list or tabs
 * @param context
 * @param attributeSet
 */
class EcommerceWalletToolSet(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding: EcommerceWalletToolSetBinding =
        EcommerceWalletToolSetBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * @param toolSetData : based size of features list, it will display the list or tabs
     * And the customizable button visibility
     */
    fun setFeatureList(toolSetData: EcommerceWalletToolSetData) {
        binding.data = toolSetData
        if (toolSetData.featuresList.size <= FEATURE_LIST_MAX_SIZE) {
            setToolSetAsList(toolSetData.featuresList)
        } else {
            setToolSetAsTabs(toolSetData.featuresList)
        }
    }

    /**
     * @param featuresList : feature list to be displayed on tool-set
     * [ListItemData.ListItemWithIcon] and [ListItemRecyclerAdapter] reused to handle the display
     */
    private fun setToolSetAsList(featuresList: List<ListItemData.ListItemWithIcon>) {
        val adapter = ListItemRecyclerAdapter()
        adapter.setListItems(featuresList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.toolSetFeatureList.layoutManager = layoutManager
        binding.toolSetFeatureList.adapter = adapter
    }

    /**
     * @param featuresList : feature list to be displayed on tool-set
     * [EcommerceWalletToolSetAdapter] will display the features list in tab (equally distributed)
     * The max span count will be 4, if the list size is less then the actual size will be the span count
     */
    private fun setToolSetAsTabs(featuresList: List<ListItemData.ListItemWithIcon>) {
        (binding.ecommerceWalletToolSetContainer.layoutParams as  MarginLayoutParams).marginStart = context.resources.getDimension(R.dimen.margin_small).toInt()
        (binding.ecommerceWalletToolSetContainer.layoutParams as  MarginLayoutParams).marginEnd = context.resources.getDimension(R.dimen.margin_small).toInt()
        val adapter = EcommerceWalletToolSetAdapter()
        binding.toolSetFeatureList.adapter = adapter
        adapter.submitList(featuresList)
        val spanCount = if (featuresList.size > ECOMMERCE_WALLET_TOOL_SET_MAX_COUNT) {
            ECOMMERCE_WALLET_TOOL_SET_MAX_COUNT
        } else {
            featuresList.size
        }
        binding.toolSetFeatureList.layoutManager = GridLayoutManager(context, spanCount)
    }

    companion object {
        private const val ECOMMERCE_WALLET_TOOL_SET_MAX_COUNT = 4
        private const val FEATURE_LIST_MAX_SIZE = 2
    }
}