package ai.offside.mobile.android.component.ui.dialogloader

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.DialogLoaderLayoutBinding


/**
 * Helper class for displaying the loader dialog.
 */
class LoadingDialog(val context: Context) {
    private lateinit var dialog: AlertDialog
    fun showLoading() {
        val binding = DialogLoaderLayoutBinding.inflate(LayoutInflater.from(context))
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setView(binding.root)
        alertDialogBuilder.setTitle("\u00A0")
        dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val customViewParent = binding.root.parent as FrameLayout
        val params = customViewParent.layoutParams as FrameLayout.LayoutParams
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        customViewParent.layoutParams = params
        binding.root.announceForAccessibility(context.getString(R.string.loading))
    }

    fun hideLoading() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}