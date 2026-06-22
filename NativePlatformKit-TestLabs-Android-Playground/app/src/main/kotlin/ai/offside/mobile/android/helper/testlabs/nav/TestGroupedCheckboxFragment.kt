package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R

/**
 * A simple [Fragment] subclass.
 * Use the [TestGroupedCheckboxFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestGroupedCheckboxFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_grouped_checkboxes, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestGroupedCheckboxFragment.
         */
        @JvmStatic
        fun newInstance() = TestGroupedCheckboxFragment()
    }
}