package ai.offside.mobile.android.component.ui.asyncimage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.AsyncImageLayoutBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * @param context
 * @param attributeSet
 *
 * Attributes declared as [R.styleable.AsyncImage] will serve the required data for rendering the dynamic images from URL
 * All the StyledAttributes are supported for data-binding
 * FaceBook Shimmer used for loader/shimmer upon the image loaded successfully the shimmer will the dismissed
 * On error the overlay container will be displayed
 */
class AsyncImage(
    context: Context,
    attributeSet: AttributeSet? = null
): ConstraintLayout(context, attributeSet) {

    private val binding: AsyncImageLayoutBinding = AsyncImageLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    @DrawableRes
    private var placeholder: Int = R.drawable.ic_decorative_card_placeholder

    init {
        val attributeArray = context.obtainStyledAttributes(attributeSet, R.styleable.AsyncImage, 0, 0)
        // PlaceHolder image resource ID
        placeholder = attributeArray.getResourceId(R.styleable.AsyncImage_placeholderImageSrc, R.drawable.ic_decorative_card_placeholder)
        binding.tileArtImage.setImageResource(placeholder)
        // Accessibility content description
        binding.root.contentDescription = attributeArray.getString(R.styleable.AsyncImage_contentDescription).orEmpty()
        attributeArray.recycle()
    }

    /**
     * @param placeholder Drawable Resource Id reference
     */
    fun setPlaceholderImageSrc(@DrawableRes placeholder: Int) {
        this.placeholder = placeholder
    }

    /**
     * @param imageUrl dynamic image URL
     * Default the shimmer will be displayed with placeholder icon on
     * On Error the overlay will be displayed with 0.5 opacity
     */
    fun setImageUrl(imageUrl: String) {
        binding.imageShimmer.root.startShimmer()
        val placeholderDrawable = ContextCompat.getDrawable(context, placeholder)
        Picasso.get()
            .load(imageUrl)
            .placeholder(placeholderDrawable ?: resources.getDrawable(placeholder, context.theme))
            .into(binding.tileArtImage, object : Callback {
                override fun onSuccess() {
                    stopShimmer()
                }

                override fun onError(e: Exception?) {
                    stopShimmer()
                    binding.errorGroup.isVisible = true
                }
            })
    }

    /**
     * Stop the shimmer and set the visibility to GONE
     */
    private fun stopShimmer() {
        binding.imageShimmer.root.isVisible = false
        binding.imageShimmer.root.stopShimmer()
    }
}