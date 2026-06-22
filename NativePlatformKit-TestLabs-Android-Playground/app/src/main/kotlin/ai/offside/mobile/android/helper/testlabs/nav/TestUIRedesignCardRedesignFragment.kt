package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.extensions.addSpaceBetweenCharacter
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiCardWorksheetDemoBinding

class TestUIRedesignCardRedesignFragment: Fragment(R.layout.fragment_test_ui_card_worksheet_demo) {

    private var _binding: FragmentTestUiCardWorksheetDemoBinding? = null
    private val binding: FragmentTestUiCardWorksheetDemoBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiCardWorksheetDemoBinding.bind(view)

        val data = CardWorkSheetData(
                "",
            resources.getString(R.string.card_worksheet_card_name),
            resources.getString(R.string.card_worksheet_card_number),
            resources.getString(R.string.card_worksheet_supplementary),
            resources.getString(R.string.card_worksheet_secondary)
        )
        binding.data = data
        binding.simpleCardWorksheet.setCardSupplementaryDescription(getCanNumberContentDescription())
        binding.fullCardWorksheet.setCardTitleContentDescription(resources.getString(R.string.card_worksheet_card_name).plus(getCanNumberContentDescription()))
    }

    /**
     * @return content description of the card-number
     */
    private fun getCanNumberContentDescription(): String {
        return resources.getString(
            R.string.card_ending_accessibility_format,
            resources.getString(R.string.card_worksheet_card_number).replace("x", "").addSpaceBetweenCharacter()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class CardWorkSheetData(
    val cardImageUrl: String,
    val cardName: String,
    val suffix: String,
    val supplementary: String,
    val secondaryInfo: String
)