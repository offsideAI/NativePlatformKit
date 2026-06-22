package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestStickyButtonsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TestStickyButtonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestStickyButtonsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentTestStickyButtonsBinding
    private lateinit var stickyButtonsInBottomSheet: StickyButtonsInBottomSheet;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestStickyButtonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.layouts_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            view.context,
            R.array.layouts_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        binding.materialTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fromHtml(
                context?.getString(R.string.debug_ui_redesign_sample_text),
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            fromHtml(context?.getString(R.string.debug_ui_redesign_sample_text))
        }
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        dismissDialog()
        when (pos) {
            0 -> {
                stickyButtonsInBottomSheet =
                    StickyButtonsInBottomSheet(R.layout.test_sticky_buttons_primary_layout)
                stickyButtonsInBottomSheet.show(childFragmentManager, TAG_STICKY_PRIMARy_BUTTONS)
            }

            1 -> {
                stickyButtonsInBottomSheet =
                    StickyButtonsInBottomSheet(R.layout.test_sticky_buttons_primary_secondary_horizontal_layout)
                stickyButtonsInBottomSheet.show(
                    childFragmentManager,
                    TAG_STICKY_BUTTONS_HORIZONTALLY_STACKED
                )
            }

            2 -> {
                stickyButtonsInBottomSheet =
                    StickyButtonsInBottomSheet(R.layout.test_sticky_buttons_primary_secondary_vertical_layout)
                stickyButtonsInBottomSheet.show(
                    childFragmentManager,
                    TAG_STICKY_BUTTONS_VERTICALLY_STACKED
                )
            }

            3 -> {
                stickyButtonsInBottomSheet =
                    StickyButtonsInBottomSheet(R.layout.test_sticky_buttons_primary_tertiary_stacked)
                stickyButtonsInBottomSheet.show(
                    childFragmentManager,
                    TAG_STICKY_BUTTONS_TERTIARY_STACKED
                )
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback.
    }

    internal class StickyButtonsInBottomSheet(val layoutId: Int) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(layoutId, container, false)
        }
    }

    /**
     *  Dismiss the exiting Bottomsheet Dialog by visibility check
     */
    private fun dismissDialog() {
        if (::stickyButtonsInBottomSheet.isInitialized) {
            if (stickyButtonsInBottomSheet.isVisible) {
                stickyButtonsInBottomSheet.dismiss()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestStickyButtonsFragment.
         */
        @JvmStatic
        fun newInstance() = TestStickyButtonsFragment()
        private const val TAG_STICKY_PRIMARy_BUTTONS = "TAG_STICKY_PRIMARy_BUTTONS"
        private const val TAG_STICKY_BUTTONS_HORIZONTALLY_STACKED =
            "TAG_STICKY_BUTTONS_HORIZONTALLY_STACKED"
        private const val TAG_STICKY_BUTTONS_VERTICALLY_STACKED =
            "TAG_STICKY_BUTTONS_VERTICALLY_STACKED"
        private const val TAG_STICKY_BUTTONS_TERTIARY_STACKED =
            "TAG_STICKY_BUTTONS_TERTIARY_STACKED"
    }
}