package ai.offside.mobile.android.helper.testlabs.nav.tile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.component.ui.tile.accounttransactions.AccountTransactionType
import ai.offside.mobile.android.component.ui.tile.accounttransactions.AccountTransactionsTileData
import ai.offside.mobile.android.component.ui.tile.accounttransactions.AccountTransactionsTileDataModel
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignAccountTransactionTilesBinding
import java.math.BigDecimal

class TestUIRedesignAccountTransactionTilesFragment: Fragment() {

    private lateinit var binding: FragmentTestUiRedesignAccountTransactionTilesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignAccountTransactionTilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.transactionTileDateLabel.apply {
            transactionDateLabel.text = getString(R.string.debug_ui_redesign_account_transaction_date_label_text)
            ViewCompat.setAccessibilityHeading(transactionDateLabel, true)
        }

        val transactionTileLIst = getTransactionTileDataList()
        binding.transactionTileNonEcommerceWallet.setTransactionTileData(transactionTileLIst.get(0))
        binding.transactionTileEcommerceWalletGreenAmount.setTransactionTileData(transactionTileLIst.get(1))
        binding.transactionTileEcommerceWallet.setTransactionTileData(transactionTileLIst.get(2))
        binding.transactionTilePending.setTransactionTileData(transactionTileLIst.get(3))
        binding.transactionTileLending.setTransactionTileData(transactionTileLIst.get(4))

        binding.depositTransactionTileDateLabel.apply {
            transactionDateLabel.text =
                getString(R.string.debug_ui_redesign_account_transaction_date_label_text_2)
            ViewCompat.setAccessibilityHeading(transactionDateLabel, true)
        }
        // Deposit transaction Green amount UI
        binding.depositTransactionTileGreenAmount.setTransactionTileData(transactionTileLIst.get(5))
        // Deposit transaction with category (Ecommerce Wallet) UI
        binding.depositTransactionTileWithCategoryEcommerceWallet.setTransactionTileData(transactionTileLIst.get(6))
        // Deposit transaction with no category (Non Ecommerce Wallet) UI
        binding.depositTransactionTileWithNoCategory.setTransactionTileData(transactionTileLIst.get(7))
    }

    private fun getTransactionTileDataList() = listOf(
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "ONLINE TRANSFER TO XXXXX5678"
                override val amount: BigDecimal = 47892.88.toBigDecimal()
                override val runningBalance: BigDecimal = 567897.00.toBigDecimal()
                override val category: String? = null
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.ACCOUNT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(requireContext(), ai.offside.mobile.android.component.ui.R.attr.offside_onSurface, Color.BLACK)

                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(context, "Non ecommerce wallet transaction tile clicked", Toast.LENGTH_SHORT).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "ONLINE TRANSFER FROM XXXXX5678"
                override val amount: BigDecimal = 47892.88.toBigDecimal()
                override val runningBalance: BigDecimal = 567897.00.toBigDecimal()
                override val category: String = "Category"
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.ACCOUNT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(requireContext(), ai.offside.mobile.android.component.ui.R.attr.offside_tertiary, Color.BLACK)

                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(context, "Ecommerce Wallet transaction with green amount tile clicked", Toast.LENGTH_SHORT).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "ACH WEB RT045AADFWE45YH ACME BANK TRANSFER"
                override val amount: BigDecimal = 47892.88.toBigDecimal()
                override val runningBalance: BigDecimal = 567897.00.toBigDecimal()
                override val category: String = "Category"
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.ACCOUNT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(requireContext(), ai.offside.mobile.android.component.ui.R.attr.offside_onSurface, Color.BLACK)
                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(context, "Ecommerce Wallet transaction tile clicked", Toast.LENGTH_SHORT).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "ONLINE TRANSFER TO XXXXX5678"
                override val amount: BigDecimal = 47892.88.toBigDecimal()
                override val runningBalance: BigDecimal? = null
                override val category: String? = null
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = true
                override val singleLineTransaction: Boolean = true
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.ACCOUNT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(requireContext(), ai.offside.mobile.android.component.ui.R.attr.offside_onSurface, Color.BLACK)
                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(context, "Pending transaction tile clicked", Toast.LENGTH_SHORT).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "ONLINE TRANSFER TO XXXXX5678"
                override val amount: BigDecimal = 47892.88.toBigDecimal()
                override val runningBalance: BigDecimal? = null
                override val category: String? = null
                override val showDivider: Boolean = false
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = true
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.ACCOUNT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(requireContext(), ai.offside.mobile.android.component.ui.R.attr.offside_onSurface, Color.BLACK)
                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(context, "Lending transaction tile clicked", Toast.LENGTH_SHORT).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "MOBILE CHECK DEPOSIT"
                override val amount: BigDecimal = 200.00.toBigDecimal()
                override val runningBalance: BigDecimal = 2528.74.toBigDecimal()
                override val category: String? = null
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.DEPOSIT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(
                        requireContext(),
                        ai.offside.mobile.android.component.ui.R.attr.offside_tertiary,
                        Color.BLACK
                    )

                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(
                        context,
                        "Deposit transaction Green amount tile clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "MOBILE CHECK DEPOSIT"
                override val amount: BigDecimal = 200.00.toBigDecimal()
                override val runningBalance: BigDecimal = 2528.74.toBigDecimal()
                override val category: String = "Deposits"
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.DEPOSIT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(
                        requireContext(),
                        ai.offside.mobile.android.component.ui.R.attr.offside_onSurface,
                        Color.BLACK
                    )

                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(
                        context,
                        "Deposit transaction with category tile clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }),
        AccountTransactionsTileDataModel(
            object : AccountTransactionsTileData {
                override val description: String = "MOBILE CHECK DEPOSIT"
                override val amount: BigDecimal = 200.00.toBigDecimal()
                override val runningBalance: BigDecimal = 2528.74.toBigDecimal()
                override val category: String? = null
                override val showDivider: Boolean = true
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.DEPOSIT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(
                        requireContext(),
                        ai.offside.mobile.android.component.ui.R.attr.offside_onSurface,
                        Color.BLACK
                    )

                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(
                        context,
                        "Deposit transaction with no category tile clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    )
}