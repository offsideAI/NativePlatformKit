package ai.offside.mobile.android.helper.testlabs.nav.controls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignSearchBarElevatedBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignSearchBarElevatedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignSearchBarElevatedFragment : Fragment() {
    private lateinit var binding: FragmentTestUiRedesignSearchBarElevatedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignSearchBarElevatedBinding.inflate(inflater,container, false)
        val items: Array<String> = resources.getStringArray(R.array.debug_ui_redesign_search_suggestions_list)
        binding.searchSuggestionViewElevated.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        binding.locationSearch.apply {
            inflateMenu(ai.offside.mobile.android.component.ui.R.menu.searchbar_menu)
            menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(false)
            setOnMenuItemClickListener {
                    menuItem ->  binding.locationSearch.clearText()
                binding.locationSearch.menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(false)
                true
            }
        }
        binding.searchViewElevated.apply{
            setText(binding.locationSearch.text)
            editText.setOnEditorActionListener { _, _, _ ->
                binding.locationSearch.menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(true)
                submitSearchQuery(binding.searchViewElevated.text.toString())
                return@setOnEditorActionListener true
            }
        }
        binding.searchSuggestionViewElevated.setOnItemClickListener { _, _, position, _ ->
            binding.locationSearch.apply {
                setText(binding.searchSuggestionViewElevated.getItemAtPosition(position).toString())
                menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(true)
            }
            binding.searchViewElevated.hide()
        }
    }


    private fun submitSearchQuery(queryString:String)
    {
        binding.locationSearch.setText(queryString)
        binding.searchViewElevated.hide()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignSearchBarElevatedFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignSearchBarElevatedFragment()

    }
}