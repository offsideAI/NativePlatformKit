package ai.offside.mobile.android.helper.testlabs.nav

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestContainerBinding

class TestUIRedesignContainerFragment : Fragment() {
    private var _binding: FragmentTestContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setProgressBarData()

        setTitleContainerData(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setProgressBarData() {
        //example a11y description for textview preceding the progress bar
        binding.progressBarLayout.progressBarContainerText.contentDescription =
            "Amount $1,500 of 2000, 75.0 percent complete"

        binding.progressBarLayout.progressBar.progress = 75
    }

    private fun setTitleContainerData(view: View) {
        binding.titleContainerDataModel = ResourcesCompat.getDrawable(
            resources,
            ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_mail,
            view.context.theme
        )?.let {
            TitleContainerData(
                "Title Container",
                it
            )
        }
    }
}

data class TitleContainerData(
    val title: String,
    val iconInt: Drawable
)