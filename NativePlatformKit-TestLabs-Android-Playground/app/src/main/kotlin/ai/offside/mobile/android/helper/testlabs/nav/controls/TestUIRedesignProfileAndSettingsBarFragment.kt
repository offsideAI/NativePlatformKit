package ai.offside.mobile.android.helper.testlabs.nav.controls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignProfileAndSettingsBarBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignProfileAndSettingsBarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignProfileAndSettingsBarFragment : Fragment() {

    private lateinit var binding:FragmentTestUiRedesignProfileAndSettingsBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentTestUiRedesignProfileAndSettingsBarBinding.inflate(inflater,container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialButtonToggleGroup.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Snackbar.make(view, tab?.text.toString(), Snackbar.LENGTH_SHORT)
                    .setAction("View") {
                        Toast.makeText(context, tab?.text, Toast.LENGTH_SHORT).show()
                    }.show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignProfileAndSettingsBarFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignProfileAndSettingsBarFragment()

    }
}