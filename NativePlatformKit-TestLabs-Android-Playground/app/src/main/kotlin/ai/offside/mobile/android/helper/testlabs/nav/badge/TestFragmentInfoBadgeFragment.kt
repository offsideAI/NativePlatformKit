package ai.offside.mobile.android.helper.testlabs.nav.badge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestInfoBadgeBinding

class TestFragmentInfoBadgeFragment:Fragment() {
    private lateinit var binding: FragmentTestInfoBadgeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestInfoBadgeBinding.inflate(inflater, container, false)
        return binding.root
    }

}