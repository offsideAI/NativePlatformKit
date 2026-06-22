package ai.offside.mobile.android.helper.testlabs.nav.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.imagetile.ImageTileState
import ai.offside.mobile.android.helper.testlabs.databinding.TestUiRedesignCarouselItemBinding

/**
 * Recycler Adapter class for carousel with card tile items.
 */
class CarouselCardTileAdapter : RecyclerView.Adapter<CarouselCardTileAdapter.CarouselCardTileViewHolder>() {

    private val data: MutableList<ImageTileState> = mutableListOf()

    /**
     * Add list items to the adapter
     */
    fun addItems(cardTilesList: List<ImageTileState>) {
        data.clear()
        data.addAll(cardTilesList)
        notifyItemRangeChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselCardTileViewHolder {
        return CarouselCardTileViewHolder(
            TestUiRedesignCarouselItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CarouselCardTileViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CarouselCardTileViewHolder(
        private val binding: TestUiRedesignCarouselItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ImageTileState) {
            binding.carouselCardTile.setImageTileData(data)
        }
    }
}