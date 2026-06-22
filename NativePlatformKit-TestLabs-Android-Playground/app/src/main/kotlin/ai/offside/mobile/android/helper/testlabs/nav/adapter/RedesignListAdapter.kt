package ai.offside.mobile.android.helper.testlabs.nav.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.helper.testlabs.nav.modals.data.MultiListItemData
import ai.offside.mobile.android.helper.testlabs.databinding.DebugMultiListItemLayoutBinding

class RedesignListAdapter(
    private val data: List<MultiListItemData> = emptyList(),
    val onClickListener: () -> Unit
) :
    RecyclerView.Adapter<RedesignListAdapter.MultiListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiListItemViewHolder {
        return MultiListItemViewHolder(
            DebugMultiListItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MultiListItemViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    inner class MultiListItemViewHolder(private val binding: DebugMultiListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(dataModel: MultiListItemData) = with(binding) {
            data = dataModel
            description2.visibility = when {
                dataModel.description2?.isEmpty() == true -> View.GONE
                else -> View.VISIBLE
            }
            binding.listItemContainer.setOnClickListener { onClickListener() }
            ViewCompat.setAccessibilityDelegate(binding.listItemContainer, object : AccessibilityDelegateCompat(){
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.apply {
                        contentDescription = String.format("%s %s %s", dataModel.title, dataModel.description1, dataModel.description2)
                        className = Button::class.qualifiedName
                    }
                }
            })
   }
    }

}