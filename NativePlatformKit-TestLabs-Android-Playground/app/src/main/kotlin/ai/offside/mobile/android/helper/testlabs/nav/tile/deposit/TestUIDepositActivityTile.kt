package ai.offside.mobile.android.helper.testlabs.nav.tile.deposit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.tile.deposit.DepositActivityTileData
import ai.offside.mobile.android.component.ui.tile.deposit.DepositActivityTileDataModel
import ai.offside.mobile.android.component.ui.tile.deposit.DepositActivityTileTextRow
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentDepositActivityTileBinding
import java.math.BigDecimal

class TestUIDepositActivityTile : Fragment(R.layout.fragment_deposit_activity_tile) {
    private var _binding: FragmentDepositActivityTileBinding? = null
    private val binding: FragmentDepositActivityTileBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDepositActivityTileBinding.bind(view)
        val depositTileData = createDepositActivityTileDataModel(
            data = depositActivityTileDataUIList()[0]
        )
        binding.simpleDepositActivityTile.setDepositTileData(depositTileData)

        val depositTileData1 = createDepositActivityTileDataModel(
            data = depositActivityTileDataUIList()[1]
        )
        binding.simpleDepositActivityTile1.setDepositTileData(depositTileData1)

        val depositTileData2 =
            createDepositActivityTileDataModel(data = depositActivityTileDataUIList()[2])

        binding.simpleDepositActivityTile2.setDepositTileData(depositTileData2)
    }

    private fun depositActivityTileDataUIList() = listOf(

        DepositActivityTileDataUI(
            "To Test Account X9898",
            BigDecimal("350.09"),
            "Posted",
            "February 26, 2023"
        ),

        DepositActivityTileDataUI(
            "To Test Account 1 X9898",
            BigDecimal("350.09"),
            "Processing",
            "February 26, 2023"
        ),

        DepositActivityTileDataUI(
            "To Test Account 2 X9898",
            BigDecimal("30.09"),
            "Posted",
            "February 28, 2023"
        )
    )


    private fun createDepositActivityTileDataModel(
        isStartingSecondaryTextVisible: Boolean = true,
        isEndingSecondaryTextVisible: Boolean = true,
        data: DepositActivityTileDataUI
    ): DepositActivityTileDataModel<DepositActivityTileDataUI> {
        val depositTileData = DepositActivityTileDataModel(object :
            DepositActivityTileData<DepositActivityTileDataUI> {
            override val tileData: DepositActivityTileDataUI
                get() = data
            override val primaryTextRow: DepositActivityTileTextRow
                get() = DepositActivityTilePrimaryRowDataValue(
                    depositActivityTileUiData = data
                )
            override val secondaryTextRow: DepositActivityTileTextRow
                get() = DepositActivityTileSecondaryRowDataValue(
                    isStartingSecondaryTextVisible = isStartingSecondaryTextVisible,
                    isEndingSecondaryTextVisible = isEndingSecondaryTextVisible,
                    depositActivityTileUiData = data
                )

            override fun onTileClick(tileView: View, tileData: DepositActivityTileDataUI) {
                Toast.makeText(tileView.context, "Action Clicked", Toast.LENGTH_LONG).show()
            }
        })
        return depositTileData
    }

    internal class DepositActivityTilePrimaryRowDataValue(val depositActivityTileUiData: DepositActivityTileDataUI) :
        DepositActivityTileTextRow {
        override val starting: String
            get() = depositActivityTileUiData.productDescription
        override val startingEnabled: Boolean
            get() = true
        override val ending: String
            get() = "$${depositActivityTileUiData.amount}"
        override val endingEnabled: Boolean
            get() = true

    }

    internal class DepositActivityTileSecondaryRowDataValue(
        val isStartingSecondaryTextVisible: Boolean = true,
        val isEndingSecondaryTextVisible: Boolean = true,
        val depositActivityTileUiData: DepositActivityTileDataUI
    ) :
        DepositActivityTileTextRow {
        override val starting: String
            get() = depositActivityTileUiData.category
        override val startingEnabled: Boolean
            get() = isStartingSecondaryTextVisible
        override val ending: String
            get() = depositActivityTileUiData.runningAmount
        override val endingEnabled: Boolean
            get() = isEndingSecondaryTextVisible

    }

}

data class DepositActivityTileDataUI(
    val productDescription: String,
    val amount: BigDecimal,
    val runningAmount: String,
    val category: String
)