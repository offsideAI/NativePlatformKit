package ai.offside.mobile.android.helper.testlabs.nav.tile.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.tile.transfer.TransferActivityTileData
import ai.offside.mobile.android.component.ui.tile.transfer.TransferActivityTileDataModel
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignTransferActivityTilesBinding
import java.math.BigDecimal

class TestUIRedesignTransferActivityTilesFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignTransferActivityTilesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignTransferActivityTilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transferTileList = getTransferTileList()
        binding.transferTransactionTile.setTransferTileDate(transferTileList.get(0))
        binding.transferRecurringTransactionTile.setTransferTileDate(transferTileList.get(1))
    }

    private fun getTransferTileList() = listOf(
        TransferActivityTileDataModel(
            object : TransferActivityTileData {
                override val toAccount: String = "Ecommerce Wallet Growth x0819"
                override val amount: BigDecimal = 892.88.toBigDecimal()
                override val fromAccount: String = "From Ecommerce Wallet Spend x1234"
                override val status: String = "status"
                override val date: String = "05/20/2024"
                override val isRecurring: Boolean = false
                override val showDivider: Boolean = true
                override val singleLineTransaction: Boolean = false
                override fun onClick(tileView: View, tileData: TransferActivityTileData) {
                    Toast.makeText(context, "Transfer transaction tile clicked", Toast.LENGTH_SHORT).show()
                }
            }
        ),
        TransferActivityTileDataModel(
            object : TransferActivityTileData {
                override val toAccount: String = "Ecommerce Wallet Growth x0819"
                override val amount: BigDecimal = 47892.88.toBigDecimal()
                override val fromAccount: String = "From Ecommerce Wallet Spend x1234"
                override val status: String = "status"
                override val date: String = "05/20/2024"
                override val isRecurring: Boolean = true
                override val showDivider: Boolean = false
                override val singleLineTransaction: Boolean = false
                override fun onClick(tileView: View, tileData: TransferActivityTileData) {
                    Toast.makeText(context, "Transfer recurring transaction tile clicked", Toast.LENGTH_SHORT).show()
                }
            }
        )
    )
}