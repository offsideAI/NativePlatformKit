package ai.offside.mobile.android.helper.testlabs.nav.tile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileButton
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelBigDecimalValuePair
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelValuePair
import ai.offside.mobile.android.component.ui.tile.paymentaccount.data.PaymentAccountTileButton
import ai.offside.mobile.android.component.ui.tile.paymentaccount.data.PaymentAccountTileDataModel
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestOffsidePaymentAccountTileBinding
import java.math.BigDecimal

class TestUIRedesignOffsidePaymentAccountTileFragment : Fragment() {
    private lateinit var binding: FragmentTestOffsidePaymentAccountTileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentTestOffsidePaymentAccountTileBinding.inflate(
                LayoutInflater.from(context),
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accounts = listOf(
            PaymentAccountTileDataModel(
                "Vaccation Cabin x123",
                "Mortgage",
                AccountTileLabelBigDecimalValuePair(
                    "Monthly Payment Due",
                    BigDecimal.valueOf(123.4)
                ),
                AccountTileLabelValuePair("Recurring Payment Scheduled", "Aug 4, 2025"),
                AccountTileLabelValuePair(
                    "Balance",
                    MaterialTextViewBindingAdapters.getCurrencyText(BigDecimal.valueOf(12351.49))
                ),
                AccountTileLabelBigDecimalValuePair(
                    "Last Payment Amount",
                    BigDecimal.valueOf(12.4)
                ),
                AccountTileLabelValuePair(
                    "Last Payment Date",
                    "May 4, 2024"
                ),
                object : AccountTileButton {
                    override val label = "View Account"
                    override fun onButtonClick(view: View) {
                    }
                },
                object : PaymentAccountTileButton {
                    override val label = "Make Payment"
                    override var visibility = true
                    override fun onButtonClick(view: View) {
                    }
                },
                object : PaymentAccountTileButton {
                    override val label = "Request Advance"
                    override var visibility = true
                    override fun onButtonClick(view: View) {
                    }
                },
                object : PaymentAccountTileButton {
                    override val label = "View Payment History"
                    override var visibility = true
                    override fun onButtonClick(view: View) {
                    }
                },
            ),
            PaymentAccountTileDataModel(
                "Vaccation Cabin x123",
                "Mortgage",
                AccountTileLabelBigDecimalValuePair(
                    "Monthly Payment Due",
                    BigDecimal.valueOf(123.4)
                ),
                AccountTileLabelValuePair(
                    "Balance",
                    MaterialTextViewBindingAdapters.getCurrencyText(BigDecimal.valueOf(12351.49))
                ),
                AccountTileLabelValuePair(
                    "Your payment is due in x days",
                    "May 4, 2024"
                ),
                AccountTileLabelBigDecimalValuePair(
                    "Last Payment Amount",
                    BigDecimal.valueOf(12.4)
                ),
                AccountTileLabelValuePair(
                    "Last Payment Date",
                    "May 4, 2024"
                ),
                object : AccountTileButton {
                    override val label = "View Account"
                    override fun onButtonClick(view: View) {
                    }
                },
                object : PaymentAccountTileButton {
                    override val label = "Make Payment"
                    override var visibility = true
                    override fun onButtonClick(view: View) {
                    }
                },
                object : PaymentAccountTileButton {
                    override val label = "Request Advance"
                    override var visibility = false
                    override fun onButtonClick(view: View) {
                    }
                },
                object : PaymentAccountTileButton {
                    override val label = "View Payment History"
                    override var visibility = true
                    override fun onButtonClick(view: View) {
                    }
                },
            ),
        )
        val accountsAdapter = OffsidePaymentAccountTileAdapter(accounts)
        binding.accountsRecyclerView.adapter = accountsAdapter
    }
}