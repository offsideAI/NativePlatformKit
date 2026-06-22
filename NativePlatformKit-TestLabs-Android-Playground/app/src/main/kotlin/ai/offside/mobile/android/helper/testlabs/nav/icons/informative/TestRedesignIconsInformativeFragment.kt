package ai.offside.mobile.android.helper.testlabs.nav.icons.informative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignIconsInformativeBinding

/**
 * Shows different type for informative icons
 */
class TestRedesignIconsInformativeFragment : Fragment() {
    lateinit var binding: FragmentTestUiRedesignIconsInformativeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignIconsInformativeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setAccessibilityDelegate(
            binding.recurringIconImageView,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.className = ""
                }
            })
    }
}
