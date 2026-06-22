package ai.offside.mobile.android.helper.testlabs.color.generator

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.ColorGeneratorGeneratedColorListItemLayoutBinding
import ai.offside.mobile.android.helper.testlabs.databinding.ColorGeneratorProvidedColorListItemLayoutBinding
import ai.offside.mobile.android.lib.timber.log
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class ColorGeneratorAdapter constructor(

): RecyclerView.Adapter<ColorGeneratorAdapter.ColorGeneratorViewHolder<out ViewDataBinding>>() {
    sealed class ColorGeneratorViewHolder<VDB : ViewDataBinding>(
        protected val binding: VDB
    ) : RecyclerView.ViewHolder(binding.root) {
        class ProvidedColor(
            binding: ColorGeneratorProvidedColorListItemLayoutBinding
        ) : ColorGeneratorViewHolder<ColorGeneratorProvidedColorListItemLayoutBinding>(binding) {
            fun setProvidedColor(model: ColorGeneratorModel) {
                binding.model = model
                binding.executePendingBindings()
            }
            companion object { const val VIEW_TYPE: Int = 0 }
        }

        class GeneratedColor(
            binding: ColorGeneratorGeneratedColorListItemLayoutBinding
        ) : ColorGeneratorViewHolder<ColorGeneratorGeneratedColorListItemLayoutBinding>(binding) {
            fun setGenerateColor(model: ColorGeneratorModel, colorIteration: Int = 1) {
                binding.model = model
                binding.apply {
                    colorGeneratorListItemLabel.text = root.resources.run {
                        getString(
                            R.string.color_generator_color_label_generated,
                            getStringArray(R.array.color_generator_values)[colorIteration]
                        )
                    }
                }
                binding.executePendingBindings()
            }
            companion object { const val VIEW_TYPE: Int = 1 }
        }
    }

    private val colorGeneratorModels: MutableSet<ColorGeneratorModel> = mutableSetOf()
    fun clear() {
        val clearedSize = colorGeneratorModels.size
        colorGeneratorModels.clear()
        notifyItemRangeRemoved(0, clearedSize)
    }

    fun renderModels(models: Collection<ColorGeneratorModel>) {
        clear()
        colorGeneratorModels.addAll(models)
        log(
            level = Log.ERROR,
            tag = this::class.simpleName
        ) {
            """
            Loading models
            ${models.joinToString("\n")}
            """.trimIndent()
        }
        notifyItemRangeInserted(0, models.size)
    }

    override fun getItemViewType(
        position: Int
    ): Int = ColorGeneratorViewHolder.GeneratedColor.VIEW_TYPE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ColorGeneratorViewHolder<out ViewDataBinding> =
        ColorGeneratorViewHolder.GeneratedColor(
            binding = ColorGeneratorGeneratedColorListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(
        holder: ColorGeneratorViewHolder<out ViewDataBinding>,
        position: Int,
    ) {
        when (holder) {
            is ColorGeneratorViewHolder.GeneratedColor ->
                holder.setGenerateColor(
                    model = colorGeneratorModels.elementAt(position),
                    colorIteration = position
                )
            is ColorGeneratorViewHolder.ProvidedColor ->
                holder.setProvidedColor(
                    model = colorGeneratorModels.elementAt(0)
                )
        }
    }

    override fun getItemCount(): Int = colorGeneratorModels.size
}