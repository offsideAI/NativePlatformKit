package ai.offside.mobile.android.helper.testlabs.nav.modals

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.dialogloader.LoadingDialog
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignLoadingBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignLoadingDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignLoadingDialogFragment : Fragment(R.layout.fragment_test_ui_redesign_loading) {
    private val DISMISS_DIALOG_AFTER: Long = 3000
    private var loadingDialog: LoadingDialog? = null

    private var _binding: FragmentTestUiRedesignLoadingBinding? = null
    private val binding: FragmentTestUiRedesignLoadingBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiRedesignLoadingBinding.bind(view)

        binding.btnShowLoading.setOnClickListener {

            showLoading()

            //Dismiss dialog after 3 seconds.
            Handler(Looper.getMainLooper()).postDelayed({ hideLoading() }, DISMISS_DIALOG_AFTER)
        }

    }

    private fun showLoading() {
        loadingDialog = LoadingDialog(requireContext())
        loadingDialog?.showLoading()
    }

    private fun hideLoading() {
        loadingDialog?.hideLoading()
    }

}