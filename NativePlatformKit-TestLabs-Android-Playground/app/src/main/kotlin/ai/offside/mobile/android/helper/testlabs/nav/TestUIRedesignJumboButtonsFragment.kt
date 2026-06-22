package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import ai.offside.mobile.android.helper.testlabs.R

/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignJumboButtonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignJumboButtonsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_jumbo_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val enableSwitch: SwitchMaterial = view.findViewById(R.id.enabled_switch)
        enableSwitch.setOnCheckedChangeListener{ _, isChecked ->
            val jumboLayoutWIcon:ConstraintLayout = view.findViewById(R.id.jumbo_buttons_layout_icon)
            jumboLayoutWIcon.findViewById<MaterialButton>(R.id.btn_jumbo_icon_left).isEnabled = isChecked
            jumboLayoutWIcon.findViewById<MaterialButton>(R.id.btn_jumbo_icon_right).isEnabled = isChecked
            val jumboLayout:ConstraintLayout = view.findViewById(R.id.jumbo_buttons_layout)
            jumboLayout.findViewById<MaterialButton>(R.id.btn_jumbo_icon_left).isEnabled = isChecked
            jumboLayout.findViewById<MaterialButton>(R.id.btn_jumbo_icon_right).isEnabled = isChecked
            view.findViewById<MaterialButton>(R.id.btn_jumbo_full).isEnabled = isChecked
            view.findViewById<MaterialButton>(R.id.btn_jumbo_full_icon).isEnabled = isChecked
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignJumboButtonsFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignJumboButtonsFragment()
    }
}