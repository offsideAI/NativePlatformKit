package ai.offside.mobile.android.helper.testlabs.nav.badge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestBadgesBinding

class TestFragmentBadgesFragment:Fragment() {
    private lateinit var binding : FragmentTestBadgesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBadgesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotificationBadge.setOnClickListener {
            findNavController().navigate(R.id.action_testFragmentBadgesFragment_to_testFragmentNotificationBadgeFragment)
        }

        binding.btnGeneralBadge.setOnClickListener {
            findNavController().navigate(R.id.action_testFragmentBadgesFragment_to_testFragmentInfoBadgeFragment)
        }
    }
}