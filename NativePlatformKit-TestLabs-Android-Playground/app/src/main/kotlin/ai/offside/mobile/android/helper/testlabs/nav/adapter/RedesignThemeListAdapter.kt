package ai.offside.mobile.android.helper.testlabs.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.helper.testlabs.databinding.ItemRedesignListviewBinding

class RedesignThemeListAdapter(private val data: Array<String>, private val onclick: ((Int) -> Unit)): RecyclerView.Adapter<RedesignThemeListAdapter.RedesignListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedesignListViewHolder {
        return RedesignListViewHolder(ItemRedesignListviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RedesignListViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class RedesignListViewHolder(private val binding: ItemRedesignListviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String, position: Int) {
            binding.headerLabel.text = data
            binding.root.setOnClickListener {
                onclick.invoke(position)
            }
        }

    }
}