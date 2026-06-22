package ai.offside.mobile.android.component.ui.listitem.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.EmphasizedListItemLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithButtonLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithCheckBoxGroupLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithIconLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithRadioGroupLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithStatusLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithSwitchLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.SimpleListItemLayoutBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.SimpleListItemViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithEmphasizedViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithButtonViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithCheckboxGroupViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithIconViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithRadioGroupViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithStatusViewHolder
import ai.offside.mobile.android.component.ui.listitem.recycler.viewholders.ListItemWithSwitchViewHolder

/**
 * Recycler Adapter class for List Item
 */
class ListItemRecyclerAdapter : RecyclerView.Adapter<ListItemViewHolder>() {
    private var listItems: MutableList<ListItemData> = mutableListOf()

    /**
     * Adds List Item
     *
     * @param item [ListItemData]
     */
    fun addListItem(item: ListItemData) {
        listItems.add(item)
        notifyItemInserted(listItems.indexOf(item))
    }

    /**
     * Sets list items
     *
     * @param items
     */
    fun setListItems(items: List<ListItemData>) {
        listItems = items.toMutableList()
    }

    /**
     * Updates list item
     *
     * @param updatedListItems
     */
    fun updateListItems(updatedListItems: List<ListItemData>) {
        val diffCallback = DiffUtilCallback(listItems, updatedListItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listItems.clear()
        listItems.addAll(updatedListItems)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Returns appropriate View Holder according to [viewType]
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemViewHolder {
        return when (viewType) {
            R.layout.list_item_with_button_layout -> {
                val binding = ListItemWithButtonLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithButtonViewHolder(binding)
            }

            R.layout.list_item_with_icon_layout -> {
                val binding = ListItemWithIconLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithIconViewHolder(binding)
            }

            R.layout.emphasized_list_item_layout -> {
                val binding = EmphasizedListItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithEmphasizedViewHolder(binding)
            }

            R.layout.list_item_with_status_layout -> {
                val binding = ListItemWithStatusLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithStatusViewHolder(binding)
            }

            R.layout.list_item_with_switch_layout -> {
                val binding = ListItemWithSwitchLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithSwitchViewHolder(binding)
            }

            R.layout.list_item_with_radio_group_layout -> {
                val binding = ListItemWithRadioGroupLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithRadioGroupViewHolder(binding)
            }

            R.layout.list_item_with_check_box_group_layout -> {
                val binding = ListItemWithCheckBoxGroupLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ListItemWithCheckboxGroupViewHolder(binding)
            }

            else -> {
                val binding = SimpleListItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SimpleListItemViewHolder(binding)
            }
        }
    }

    override fun getItemCount() = listItems.size

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val dividerVisibility = if (listItems.lastIndex == position) {
            View.GONE
        } else {
            View.VISIBLE
        }
        holder.bind(listItems[position], dividerVisibility)
    }

    /**
     * Returns view type value based of data types
     */
    override fun getItemViewType(position: Int): Int =
        when (listItems[position]) {
            is ListItemData.ListItemWithIcon -> R.layout.list_item_with_icon_layout
            is ListItemData.ListItemWithEmphasizedIcon -> R.layout.emphasized_list_item_layout
            is ListItemData.ListItemWithStatus -> R.layout.list_item_with_status_layout
            is ListItemData.ListItemWithSwitch -> R.layout.list_item_with_switch_layout
            is ListItemData.RadioGroupListItem -> R.layout.list_item_with_radio_group_layout
            is ListItemData.CheckboxGroupListItem -> R.layout.list_item_with_check_box_group_layout
            is ListItemData.ListItemWithButton -> R.layout.list_item_with_button_layout
            else -> -1
        }

    /**
     * DiffUtil used to refresh the list
     * Calculates the difference between 2 list and updates the respective list-item instead of whole list getting change
     */
    class DiffUtilCallback(
        private val oldList: List<ListItemData>,
        private val newList: List<ListItemData>
    ) :
        DiffUtil.Callback() {

        /** Old List item size */
        override fun getOldListSize(): Int = oldList.size

        /** New List item size */
        override fun getNewListSize(): Int = newList.size

        /**
         * @param oldItemPosition
         * @param newItemPosition
         * @return true if the label matches, if the items not same then the view will be redrawn (refreshed)
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.label == newItem.label
        }

        /**
         * @param oldItemPosition
         * @param newItemPosition
         * @return true if both class values are equal, if the items not same then the view will be redrawn (refreshed)
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}