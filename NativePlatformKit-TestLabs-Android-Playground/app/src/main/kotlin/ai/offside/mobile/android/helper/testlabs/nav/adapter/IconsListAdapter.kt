package ai.offside.mobile.android.helper.testlabs.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.helper.testlabs.databinding.ItemRedesignDecorativeIconsListviewBinding
import ai.offside.mobile.android.helper.testlabs.nav.icons.decorative.IconsDecorative

class IconsListAdapter(private val data: Array<IconsDecorative>): RecyclerView.Adapter<IconsListAdapter.RedesignListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedesignListViewHolder {
        return RedesignListViewHolder(ItemRedesignDecorativeIconsListviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RedesignListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class RedesignListViewHolder(private val binding: ItemRedesignDecorativeIconsListviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IconsDecorative) {
            binding.itemLabel.text = data.text
            binding.rightIcon.setImageResource(data.iconResourceId)
        }
    }

}