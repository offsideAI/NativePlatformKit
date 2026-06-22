package ai.offside.mobile.android.helper.testlabs.nav.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentRedesignSnackBarDemoBinding

class TestUIRedesignSnackBarDemoFragment: Fragment() {

    private var _binding: FragmentRedesignSnackBarDemoBinding? = null
    private val binding: FragmentRedesignSnackBarDemoBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRedesignSnackBarDemoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSnackbar.setOnClickListener {
            Snackbar.make(it, "Changes have been saved.", resources.getInteger(ai.offside.mobile.android.component.ui.R.integer.snackbar_short_duration))
                .show()
        }
        binding.btnSnackbarWithAction.setOnClickListener {
            Snackbar.make(it, getString(R.string.debug_ui_redesign_snackbar_with_action_label), resources.getInteger(ai.offside.mobile.android.component.ui.R.integer.snackbar_long_duration)).show()
        }
        binding.btnPersistenceSnackbar.setOnClickListener {
            Snackbar.make(it, getString(R.string.debug_ui_redesign_persistence_snackbar_label), Snackbar.LENGTH_INDEFINITE)
                .setAction("View") {
                    Toast.makeText(context, "Action from snack-bar", Toast.LENGTH_SHORT).show()
                }.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}