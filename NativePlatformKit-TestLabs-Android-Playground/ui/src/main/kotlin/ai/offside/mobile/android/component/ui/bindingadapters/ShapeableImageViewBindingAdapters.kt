package ai.offside.mobile.android.component.ui.bindingadapters

import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("drawableRes")
internal fun ShapeableImageView.updateImageResource(@DrawableRes drawableRes: Int) {
    setImageResource(drawableRes)
}