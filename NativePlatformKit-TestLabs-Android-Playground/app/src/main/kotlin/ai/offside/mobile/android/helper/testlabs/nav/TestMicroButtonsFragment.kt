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
 * Use the [TestMicroButtonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestMicroButtonsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_micro_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch1: SwitchMaterial = view.findViewById(R.id.enabled_switch)
        switch1.setOnCheckedChangeListener{ _, isChecked ->
            val microButtonLayout:ConstraintLayout = view.findViewById(R.id.micro_buttons_layout)
            val iconButtonLayout:ConstraintLayout = view.findViewById(R.id.button_icon_layout)
            microButtonLayout.findViewById<MaterialButton>(R.id.btn_micro).isEnabled = isChecked
            microButtonLayout.findViewById<MaterialButton>(R.id.btn_micro_blue).isEnabled = isChecked
            iconButtonLayout.findViewById<MaterialButton>(R.id.btn_micro).isEnabled = isChecked
            iconButtonLayout.findViewById<MaterialButton>(R.id.btn_micro_blue).isEnabled = isChecked
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestMicroButtonsFragment.
         */
        @JvmStatic
        fun newInstance() = TestMicroButtonsFragment()
    }
}