package ai.offside.mobile.android.helper.testlabs.nav.controls


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignSearchBarStandardBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TestUIRedesignSearchBarStandardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestUIRedesignSearchBarStandardFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignSearchBarStandardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTestUiRedesignSearchBarStandardBinding.inflate(inflater,container, false)
        val items: Array<String> = resources.getStringArray(R.array.debug_ui_redesign_search_suggestions_list)
        binding.searchSuggestionView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        setupSearchView()
        return binding.root
    }

    private fun setupSearchView() {
        binding.contactsSearch.apply {
            inflateMenu(ai.offside.mobile.android.component.ui.R.menu.searchbar_menu)
            menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(false)
            setOnMenuItemClickListener {
                    menuItem ->  binding.contactsSearch.clearText()
                    menuItem.setVisible(false)
                    true
            }
        }
        binding.searchView.apply{
            setText(binding.contactsSearch.text)
            editText.setOnEditorActionListener { _, _, _ ->
                binding.contactsSearch.menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(true)
                submitSearchQuery(binding.searchView.text.toString())
                return@setOnEditorActionListener true
            }
        }
        binding.searchSuggestionView.setOnItemClickListener { _, _, position, _ ->
            binding.contactsSearch.apply{
                setText(binding.searchSuggestionView.getItemAtPosition(position).toString())
                menu.findItem(ai.offside.mobile.android.component.ui.R.id.action_clear).setVisible(true)
            }
            binding.searchView.hide()
        }
    }

    private fun submitSearchQuery(queryString:String) {
        binding.contactsSearch.setText(queryString)
        binding.searchView.hide()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestUIRedesignSearchBarFragment.
         */
        @JvmStatic
        fun newInstance() = TestUIRedesignSearchBarStandardFragment()
    }
}