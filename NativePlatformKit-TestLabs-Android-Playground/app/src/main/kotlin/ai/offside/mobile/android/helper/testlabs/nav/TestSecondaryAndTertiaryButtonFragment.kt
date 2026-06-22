package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R

/**
 * A simple [Fragment] subclass.
 * Use the [TestSecondaryAndTertiaryButtonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestSecondaryAndTertiaryButtonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_test_secondary_and_tertiary_button,
            container,
            false
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestSecondaryAndTertiaryButtonFragment.
         */
        @JvmStatic
        fun newInstance() = TestSecondaryAndTertiaryButtonFragment()
    }
}