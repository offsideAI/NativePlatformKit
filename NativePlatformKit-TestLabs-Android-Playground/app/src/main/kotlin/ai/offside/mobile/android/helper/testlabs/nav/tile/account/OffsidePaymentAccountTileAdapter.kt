package ai.offside.mobile.android.helper.testlabs.nav.tile.account

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.tile.paymentaccount.PaymentAccountTileLayout
import ai.offside.mobile.android.component.ui.tile.paymentaccount.data.PaymentAccountTileData
import ai.offside.mobile.android.component.ui.tile.paymentaccount.data.PaymentAccountTileDataModel

class OffsidePaymentAccountTileAdapter(val accounts: List<PaymentAccountTileDataModel>) :
    RecyclerView.Adapter<OffsidePaymentAccountTileAdapter.OffsidePaymentAccountTileViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OffsidePaymentAccountTileViewHolder {
        val paymentAccountTileLayout = PaymentAccountTileLayout(parent.context, attachToParent = false)
        return OffsidePaymentAccountTileViewHolder(paymentAccountTileLayout)
    }

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(holder: OffsidePaymentAccountTileViewHolder, position: Int) {
        holder.bindData(accounts[position])
    }

    class OffsidePaymentAccountTileViewHolder(private val paymentAccountTileLayout: PaymentAccountTileLayout) :
        RecyclerView.ViewHolder(paymentAccountTileLayout.root) {
        fun bindData(accountData: PaymentAccountTileDataModel) {
            paymentAccountTileLayout.setAccountData(accountData)
        }
    }
}