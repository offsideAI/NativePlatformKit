package ai.offside.mobile.android.helper.testlabs.nav

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.listactions.ListActionsData
import ai.offside.mobile.android.component.ui.listactions.ListActionsDataModel
import ai.offside.mobile.android.component.ui.tile.accounttransactions.AccountTransactionType
import ai.offside.mobile.android.component.ui.tile.accounttransactions.AccountTransactionsTileData
import ai.offside.mobile.android.component.ui.tile.accounttransactions.AccountTransactionsTileDataModel
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignListActionsBinding
import java.math.BigDecimal

class TestUIRedesignListActionsFragment: Fragment() {

    private lateinit var binding: FragmentTestUiRedesignListActionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignListActionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accountTransactionList = getAccountTransactionList()
        val actionTileList = getActionTileLIst()

        binding.listActionsNoIcon.apply {
            nonEcommerceWalletTransactionLayout.setTransactionTileData(accountTransactionList.get(0))
            ecommerceWalletTransactionLayout.setTransactionTileData(accountTransactionList.get(1))
            testListActionsLayout.setActionTileData(actionTileList.get(0))
        }
        binding.listActionsLeftIcon.apply {
            nonEcommerceWalletTransactionLayout.setTransactionTileData(accountTransactionList.get(0))
            ecommerceWalletTransactionLayout.setTransactionTileData(accountTransactionList.get(1))
            testListActionsLayout.setActionTileData(actionTileList.get(1))
        }
        binding.listActionsRightIcon.apply {
            nonEcommerceWalletTransactionLayout.setTransactionTileData(accountTransactionList.get(0))
            ecommerceWalletTransactionLayout.setTransactionTileData(accountTransactionList.get(1))
            testListActionsLayout.setActionTileData(actionTileList.get(2))
        }
    }

    private fun getAccountTransactionList() = listOf(
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
                    MaterialColors.getColor(requireContext(), R.attr.offside_onSurface, Color.BLACK)

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
                override val showDivider: Boolean = false
                override val isPendingTransaction: Boolean = false
                override val singleLineTransaction: Boolean = false
                override val accountTransactionType: AccountTransactionType =
                    AccountTransactionType.ACCOUNT_TRANSACTION
                override val amountTextColor: Int =
                    MaterialColors.getColor(requireContext(), R.attr.offside_tertiary, Color.BLACK)

                override fun onClick(tileData: AccountTransactionsTileData) {
                    Toast.makeText(context, "Ecommerce Wallet transaction with green amount tile clicked", Toast.LENGTH_SHORT).show()
                }
            })
    )


    private fun getActionTileLIst() = listOf(
        ListActionsDataModel(
            object  : ListActionsData {
                override val actionText: String = "View More"
                override val leftDrawable: Int = 0
                override val rightDrawable: Int = 0
                override fun onClick() {
                    Toast.makeText(context, "List Actions with no icon", Toast.LENGTH_SHORT).show()
                }
            }
        ),
        ListActionsDataModel(
            object : ListActionsData {
                override val actionText: String = "View More"
                override val leftDrawable: Int = R.drawable.ic_decorative_add_regular
                override val rightDrawable: Int = 0
                override fun onClick() {
                    Toast.makeText(context, "List Actions with left icon", Toast.LENGTH_SHORT).show()
                }
            }
        ),
        ListActionsDataModel(
            object : ListActionsData {
                override val actionText: String = "View Details"
                override val leftDrawable: Int = 0
                override val rightDrawable: Int = R.drawable.ic_actionable_bottom_sheet_arrow
                override fun onClick() {
                    Toast.makeText(context, "List Actions with right icon", Toast.LENGTH_SHORT).show()
                }
            }
        )
    )

}