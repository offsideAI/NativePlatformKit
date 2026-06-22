package ai.offside.mobile.android.helper.testlabs.nav.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestRedesignReviewFieldTileBinding

/**
 * A simple [Fragment] subclass.
 * Shows different type for review field tile display
 */
class TestRedesignReviewFieldTileFragment :
    Fragment(R.layout.fragment_test_redesign_review_field_tile) {

    private var _binding: FragmentTestRedesignReviewFieldTileBinding? = null
    private val binding: FragmentTestRedesignReviewFieldTileBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestRedesignReviewFieldTileBinding.bind(view)

        binding.editableReviewField.setActionListener {
            Toast.makeText(
                context,
                getString(R.string.review_edit_action_clicked),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.reviewFieldWithIconNormal.setActionListener {
            Toast.makeText(context, getString(R.string.review_tool_tip_clicked), Toast.LENGTH_SHORT)
                .show()
        }
        binding.reviewFieldWithIconLabelWrapped.setActionListener {
            Toast.makeText(context, getString(R.string.review_tool_tip_clicked), Toast.LENGTH_SHORT)
                .show()
        }
        binding.reviewFieldWithIconValueWrapped.setActionListener {
            Toast.makeText(context, getString(R.string.review_tool_tip_clicked), Toast.LENGTH_SHORT)
                .show()
        }
        binding.reviewFieldWithIconLabelAndValueWrapped.setActionListener {
            Toast.makeText(context, getString(R.string.review_tool_tip_clicked), Toast.LENGTH_SHORT)
                .show()
        }
    }

}