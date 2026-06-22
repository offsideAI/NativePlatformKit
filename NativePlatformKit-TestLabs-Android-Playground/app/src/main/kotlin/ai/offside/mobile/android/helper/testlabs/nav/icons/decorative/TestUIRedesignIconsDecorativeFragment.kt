package ai.offside.mobile.android.helper.testlabs.nav.icons.decorative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestIconsDecorativeBinding
import ai.offside.mobile.android.helper.testlabs.nav.adapter.IconsListAdapter

class TestUIRedesignIconsDecorativeFragment : Fragment() {
    lateinit var binding: FragmentTestIconsDecorativeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestIconsDecorativeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val decorIconsEnum = IconsDecorative.values()
        binding.listview.adapter = IconsListAdapter(decorIconsEnum)
    }
}




