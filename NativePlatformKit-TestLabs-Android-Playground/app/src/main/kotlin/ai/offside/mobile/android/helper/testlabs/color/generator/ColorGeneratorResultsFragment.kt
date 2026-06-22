package ai.offside.mobile.android.helper.testlabs.color.generator

import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.ColorGeneratorResultsFragmentLayoutBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ColorGeneratorResultsFragment : Fragment() {
    private lateinit var binding: ColorGeneratorResultsFragmentLayoutBinding

    private val colorGeneratorViewModel: ColorGeneratorViewModel by navGraphViewModels(
        navGraphId = R.id.testlabs_color_generator_nav_graph,
        factoryProducer = { ColorGeneratorViewModel.Factory }
    )

    private val colorAdapter: ColorGeneratorAdapter = ColorGeneratorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ColorGeneratorResultsFragmentLayoutBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = colorGeneratorViewModel
        }
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            colorGeneratorOutputRecyclerView.apply {
                adapter = colorAdapter
                itemAnimator = DefaultItemAnimator()
            }
            colorGeneratorViewModel.mainModel.observe(viewLifecycleOwner) { mainModel ->
                colorGeneratorOutputSourceInclude.model = mainModel
            }
            colorGeneratorViewModel.generatedModels.observe(viewLifecycleOwner) { genModels ->
                colorAdapter.renderModels(models = genModels)
            }
        }
    }
}