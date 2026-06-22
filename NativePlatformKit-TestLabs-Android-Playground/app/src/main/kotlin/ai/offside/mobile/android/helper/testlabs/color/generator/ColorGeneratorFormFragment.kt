package ai.offside.mobile.android.helper.testlabs.color.generator

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import ai.offside.mobile.android.component.ui.color.UIComponentColor
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.ColorGeneratorFormFragmentLayoutBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu

class ColorGeneratorFormFragment : Fragment() {
    private lateinit var binding: ColorGeneratorFormFragmentLayoutBinding

    private val colorGeneratorViewModel: ColorGeneratorViewModel by navGraphViewModels(
        navGraphId = R.id.testlabs_color_generator_nav_graph,
        factoryProducer = { ColorGeneratorViewModel.Factory }
    )

    private fun View.showPopup(
        menuFiller: (menu: Menu) -> Unit,
        menuClickListener: (item: MenuItem) -> Unit
    ) {
        PopupMenu(requireActivity(), this)
            .apply {
                inflate(R.menu.color_generator_empty_menu)
                menuFiller(menu)
                setOnMenuItemClickListener { true.also { _ -> menuClickListener(it) } }
            }
            .show()
    }

    private fun ColorGeneratorFormFragmentLayoutBinding.configure() {
        colorGeneratorResultsButton.setOnClickListener {
            it.findNavController().navigate(
                getString(R.string.color_generator_results_route)
            )
            colorGeneratorViewModel.onEvent(
                event = ColorGeneratorEvent.Navigation.ToResults
            )
        }

        colorGeneratorViewModel.run {
            colorGeneratorSelectorFormInclude
                .colorGeneratorSelectorFamily
                .setOnClickListener { v ->
                    v.showPopup(
                        menuFiller = {
                            UIComponentColor
                                .families
                                .onEach { cFam ->
                                    it.add(Menu.NONE, cFam.ordinal, cFam.ordinal, cFam.name)
                                }
                        }
                    ) { menuItem ->
                        colorGeneratorViewModel
                            .onFamilyChosen(
                                family = UIComponentColor.readFamily("${menuItem.title}")
                            )
                        colorGeneratorResultsButton.isEnabled = false
                    }
                }

                colorGeneratorState.observe(viewLifecycleOwner) { generatorState ->
                    when (generatorState) {
                        is ColorGeneratorState.FormType.Selectors.Family -> {
                            colorGeneratorSelectorFormInclude
                                .colorGeneratorSelectorFamily
                                .text = generatorState.family.name
                            colorGeneratorSelectorFormInclude
                                .colorGeneratorSelectorColor
                                .apply {
                                    isEnabled = generatorState.colors.isNotEmpty()
                                    setText(R.string.color_generator_selector_color_empty)
                                    setOnClickListener { v ->
                                        v.showPopup(
                                            menuFiller = {
                                                generatorState
                                                    .colors
                                                    .onEach { fColor ->
                                                        fColor.addToMenu(
                                                            menu = it,
                                                            context = v.context
                                                        )
                                                    }
                                            }
                                        ) { menuItem ->
                                            generatorState
                                                .colors
                                                .find { c -> c.colorRes == menuItem.itemId }
                                                ?.let { color ->
                                                    colorGeneratorSelectorFormInclude
                                                        .colorGeneratorSelectorColor
                                                        .text = getString(
                                                            R.string.color_generator_selector_color_selected,
                                                            (color as Enum<*>).name,
                                                            color.colorString(context = v.context)
                                                        )
                                                    colorGeneratorViewModel.onFamilyColorChosen(color = color)
                                                }
                                        }
                                    }
                                }
                        }

                        is ColorGeneratorState.FormType.Selectors.FamilyColor -> {
                            colorGeneratorResultsButton.isEnabled = true
                        }

                        is ColorGeneratorState.FormType.Manual -> {
                            colorGeneratorResultsButton.isEnabled = true
                        }

                        else -> {}
                    }
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ColorGeneratorFormFragmentLayoutBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = colorGeneratorViewModel
        }
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.configure()
    }
}