package ai.offside.mobile.android.helper.testlabs.nav.stepper

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ai.offside.mobile.android.helper.testlabs.R

/**
 *  Sets fragment result for child Fragment manager to receive it in Parent fragment
 */
internal fun FragmentManager.setResult(context: Context, goForward: Boolean) {
    setFragmentResult(
        context.getString(R.string.debug_ui_redesign_stepper_fragment_result_key),
        bundleOf(context.getString(R.string.debug_ui_redesign_stepper_next_step_arguments_text) to goForward)
    )
}

/**
 *  Handles device back press on Fragments
 */
internal fun Fragment.onDeviceBackPress(callback: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = callback.invoke()
        }
    )
}