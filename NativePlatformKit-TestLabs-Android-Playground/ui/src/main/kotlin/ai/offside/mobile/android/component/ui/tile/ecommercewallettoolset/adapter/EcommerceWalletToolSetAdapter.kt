package ai.offside.mobile.android.component.ui.tile.ecommercewallettoolset.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.databinding.EcommerceWalletToolSetTabItemBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * [EcommerceWalletToolSetAdapter] responsible for listing the Ecommerce Wallet Tool-Set data in RecyclerView
 * [EcommerceWalletToolSetViewHolder] holds the responsibility to bind the data to view
 * [EcommerceWalletToolSetDiffUtil] notifies the ListAdapter with list of [ListItemWithIcon],
 *      if there is any new list submitted then only the difference will be redrawn
 */
class EcommerceWalletToolSetAdapter: ListAdapter<ListItemData.ListItemWithIcon, EcommerceWalletToolSetAdapter.EcommerceWalletToolSetViewHolder>(EcommerceWalletToolSetDiffUtil()){

    /**
     * @param parent
     * @param viewType
     *
     * @return [EcommerceWalletToolSetViewHolder] and binding as [EcommerceWalletToolSetTabItemBinding]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcommerceWalletToolSetViewHolder {
        return EcommerceWalletToolSetViewHolder(EcommerceWalletToolSetTabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * @param holder
     * @param position
     *
     * Binds the feature data into view
     */
    override fun onBindViewHolder(holder: EcommerceWalletToolSetViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    /**
     * @param viewBinding
     * Responsible for binding the data into view
     */
    class EcommerceWalletToolSetViewHolder(private val viewBinding: EcommerceWalletToolSetTabItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        /**
         * @param data : [ListItemWithIcon]
         * Data-binding used to bind the view and data
         */
        fun onBind(data: ListItemData.ListItemWithIcon) {
            viewBinding.feature = data
            viewBinding.toolSetItem.modifyRoleDescription(data.label.toString(), Button::class.java.simpleName)
        }
    }

    /**
     * DiffUtil used to refresh the list
     * Calculates the difference between 2 list and updates the respective list-item instead of whole list getting change
     * [submitList] will be used to submit the list of data to be listed in recyclerview
     */
    class EcommerceWalletToolSetDiffUtil: DiffUtil.ItemCallback<ListItemData.ListItemWithIcon>() {
        /**
         * @param oldItem
         * @param newItem
         * @return true if the label matches, if the items not same then the view will be redrawn (refreshed)
         */
        override fun areItemsTheSame(
            oldItem: ListItemData.ListItemWithIcon,
            newItem: ListItemData.ListItemWithIcon
        ): Boolean {
            return oldItem.label == newItem.label
        }

        /**
         * @param oldItem
         * @param newItem
         * @return true if both class values are equal, if the items not same then the view will be redrawn (refreshed)
         */
        override fun areContentsTheSame(
            oldItem: ListItemData.ListItemWithIcon,
            newItem: ListItemData.ListItemWithIcon
        ): Boolean {
            return oldItem == newItem
        }
    }
}