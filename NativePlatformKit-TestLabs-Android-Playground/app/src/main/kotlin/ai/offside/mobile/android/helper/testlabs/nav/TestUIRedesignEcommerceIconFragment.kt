package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignEcommerceBadgeBinding

class TestUIRedesignEcommerceIconFragment : Fragment(R.layout.fragment_test_ui_redesign_ecommerce_badge) {
    private var _binding: FragmentTestUiRedesignEcommerceBadgeBinding? = null
    private val binding: FragmentTestUiRedesignEcommerceBadgeBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiRedesignEcommerceBadgeBinding.bind(view)

        val data = EcommerceIconData(
            ecommerceIconUrl = "",
            userInitials = "",
            isLargeIcon = false,
            isRegisteredUser = false
        )

        binding.data = data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

data class EcommerceIconData(
    val ecommerceIconUrl: String,
    val userInitials: String,
    val isLargeIcon: Boolean,
    val isRegisteredUser: Boolean,
)