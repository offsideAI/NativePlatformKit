package ai.offside.mobile.android.helper.testlabs.nav.tile.online_payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.tile.online_payments.OnlinePaymentsTileData
import ai.offside.mobile.android.component.ui.tile.online_payments.OnlinePaymentsTileDataModel
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignOnlinePaymentsTilesBinding

/**
 * Fragment class for OnlinePayments tiles
 */
class TestUIRedesignOnlinePaymentsFragment : Fragment() {

    private lateinit var binding: FragmentTestUiRedesignOnlinePaymentsTilesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestUiRedesignOnlinePaymentsTilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onlinePaymentsActivityDataList = getOnlinePaymentsActivityTileDataList()
        binding.onlinePaymentsPaymentCompleted.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(0))
        binding.onlinePaymentsPaymentScheduled.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(1))
        binding.onlinePaymentsPaymentEbillLate.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(2))
        binding.onlinePaymentsPaymentEbillOntime.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(3))
        binding.onlinePaymentsPaymentReminderLate.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(4))
        binding.onlinePaymentsPaymentReminderOntime.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(5))
        binding.onlinePaymentsPaymentRequestLate.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(6))
        binding.onlinePaymentsPaymentRequestOntime.setOnlinePaymentsTileData(onlinePaymentsActivityDataList.get(7))
    }


    private fun getOnlinePaymentsActivityTileDataList() = listOf(
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC1 Completed Payment"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_COMPLETED
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl = ""
            override val onlinePaymentsFromAccount = "Test Biller Pay1"
            override val onlinePaymentsDueDate = "Feb 28, 2023"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC2 Scheduled Payment"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_SCHEDULED
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl =
                "https://www.offside.com/content/dam/offside-com/images/personal/DebitCard/MobileWallet/NKU_DC_Rounded.png"
            override val onlinePaymentsFromAccount = "Test Biller Pay2"
            override val onlinePaymentsDueDate = "Feb 28, 2023"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onEditButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile EditButton Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC3 Online Payments Ebill Late"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_EBILL_LATE
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl =
                "https://www.offside.com/content/dam/offside-com/images/personal/DebitCard/MobileWallet/PittsburghPirates_DC2_Rounded.png"
            override val onlinePaymentsFromAccount = ""
            override val onlinePaymentsDueDate = "Past Due"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Pay Button Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Secondar Button Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC4 Online Payments Ebill OnTime"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_EBILL_ON_TIME
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl = ""
            override val onlinePaymentsFromAccount = ""
            override val onlinePaymentsDueDate = "Feb 28, 2023"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Pay Button Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Secondar Button Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC5 Online Payments Reminder Late"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_REMINDER_LATE
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl = ""
            override val onlinePaymentsFromAccount = ""
            override val onlinePaymentsDueDate = "Past Due"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Pay Button Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Secondar Button Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC6 Online Payments Reminder OnTime"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_REMINDER_ON_TIME
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl = ""
            override val onlinePaymentsFromAccount = ""
            override val onlinePaymentsDueDate = "Feb 28, 2023"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Pay Button Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Secondar Button Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC7 Online Payments Request Late"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_REQUEST_LATE
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl = ""
            override val onlinePaymentsFromAccount = ""
            override val onlinePaymentsDueDate = "Past Due"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Pay Button Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Secondar Button Clicked", Toast.LENGTH_SHORT).show()
            }
        }),
        OnlinePaymentsTileDataModel(requireContext(), object : OnlinePaymentsTileData {
            override val onlinePaymentsDisplayName = "AC8 Online Payments Request OnTime"
            override val onlinePaymentsAmount = 250.00.toBigDecimal()
            override val onlinePaymentsType = OnlinePaymentsTileDataModel.OnlinePaymentsType.ONLINE_PAYMENTS_REQUEST_ON_TIME
            override val onlinePaymentsImageRes = R.drawable.ic_decorative_payee
            override val onlinePaymentsImageUrl = ""
            override val onlinePaymentsFromAccount = ""
            override val onlinePaymentsDueDate = "Feb 28, 2023"
            override fun onTileClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Tile ${tileData.onlinePaymentsType.type} Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Pay Button Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {
                Toast.makeText(requireContext(), "Secondar Button Clicked", Toast.LENGTH_SHORT).show()
            }
        })
    )
}