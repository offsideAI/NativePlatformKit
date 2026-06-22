package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.BadgeDisplayStatus
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.EcommerceTileButton
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.EcommerceTileComponents
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.EcommerceTileDataModel
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignEcommerceTileBinding

class TestUIRedesignEcommerceTileFragment : Fragment(R.layout.fragment_test_ui_redesign_ecommerce_tile) {
    private var _binding: FragmentTestUiRedesignEcommerceTileBinding? = null
    private val binding: FragmentTestUiRedesignEcommerceTileBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiRedesignEcommerceTileBinding.bind(view)

        showStandardRequest()
        showStandardRequestWithMemo()

        showRequestReceived()
        showRequestReceivedWithMemo()

        showSplitRequestPaid()
        showSplitRequestPaidWithMemo()

        showGroupRequest()
        showGroupRequestWithMemo()

        showSplitGroupSent()
        showSplitGroupPaid()

        showPendingPayment()
        showPendingPaymentWithMemo()

        showCancelPayment()

        initUI()
    }


    private fun showStandardRequest() {

        binding.ecommerceTileScenarioStandardRequest.setAccountData(tileData = EcommerceTileDataModel(
            userName = "Standard Request",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Payment Received",
            userInitials = "SR",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showStandardRequestWithMemo() {
        binding.ecommerceTileScenarioStandardRequestWithMemo.setAccountData(tileData = EcommerceTileDataModel(
            memo = EcommerceTileComponents.EcommerceComponentModel(label = "had a great time", visibility = true),
            userName = "Standard RequestMemo",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Payment Received",
            userInitials = "SR",
            ecommerceBadge = BadgeDisplayStatus.SMALL_UNREGISTERED_PERSON_ICON
        )
        )
    }

    private fun showRequestReceived() {
        binding.ecommerceTileScenarioRequestReceived.setAccountData(tileData = EcommerceTileDataModel(
            sendButton = EcommerceTileButton.EcommerceButtonModel(label = "Send", visibility = true, {}),
            declineButton = EcommerceTileButton.EcommerceButtonModel(label = "Decline", visibility = true, {}),
            daysRemaining = EcommerceTileComponents.EcommerceComponentModel(label = "You have 9 days to respond", visibility = true),
            userName = "Request Received",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Received",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_BUSINESS_ICON
        )
        )
    }

    private fun showRequestReceivedWithMemo() {
        binding.ecommerceTileScenarioRequestReceivedWithMemo.setAccountData(tileData = EcommerceTileDataModel(
            sendButton = EcommerceTileButton.EcommerceButtonModel(label = "Send", visibility = true, {}),
            declineButton = EcommerceTileButton.EcommerceButtonModel(label = "Decline", visibility = true, {}),
            memo = EcommerceTileComponents.EcommerceComponentModel(label = "had a great time", visibility = true),
            daysRemaining = EcommerceTileComponents.EcommerceComponentModel(label = "You have 9 days to respond.", visibility = true),
            userName = "Request ReceivedMemo",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Received",
            ecommerceBadge = BadgeDisplayStatus.SMALL_UNREGISTERED_BUSINESS_ICON
        )
        )
    }

    private fun showSplitRequestPaid() {
        binding.ecommerceTileScenarioSplitRequestPaid.setAccountData(tileData = EcommerceTileDataModel(
            partOfGroupRequest = EcommerceTileButton.EcommerceButtonModel(label = "Part of Group Request", visibility = true, {}),
            userName = "Split RequestPaid",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Paid",
            userInitials = "SP",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showSplitRequestPaidWithMemo(){
    binding.ecommerceTileScenarioSplitRequestPaidWithMemo.setAccountData(tileData = EcommerceTileDataModel(
            partOfGroupRequest = EcommerceTileButton.EcommerceButtonModel(label = "Part of Group Request", visibility = true, {}),
            memo = EcommerceTileComponents.EcommerceComponentModel(label = "Good show", visibility = true),
            userName = "Split RequestMemo",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Paid",
            userInitials = "SP",
        ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
    )
    }

    private fun showGroupRequest() {
        binding.ecommerceTileScenarioSplitRequestGroup.setAccountData(tileData = EcommerceTileDataModel(
            userName = "You and 3 People",
            paymentAmount = "$125,000.00",
            paymentDate = "October 24, 2024",
            paymentStatus = "Split Request Sent",
            userInitials = "+3",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showGroupRequestWithMemo() {
        binding.ecommerceTileScenarioSplitRequestGroupWithMemo.setAccountData(tileData = EcommerceTileDataModel(
            memo = EcommerceTileComponents.EcommerceComponentModel(label = "had a great time", visibility = true),
            userName = "You and 3 People",
            paymentAmount = "$125,000.00",
            paymentDate = "October 24, 2024",
            paymentStatus = "Split Request Sent",
            userInitials = "+3",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showSplitGroupSent(){
        binding.ecommerceTileScenarioSplitGroupSent.setAccountData(tileData = EcommerceTileDataModel(
            cancelRequestButton = EcommerceTileButton.EcommerceButtonModel(label = "Cancel Request", visibility = true, {}),
            userName = "Split GroupSent",
            paymentAmount = "$125,000.00",
            paymentDate = "Confirmation: ACME2376123945",
            paymentStatus = "Request Sent",
            userInitials = "SG",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showSplitGroupPaid(){
        binding.ecommerceTileScenarioSplitGroupPaid.setAccountData(tileData = EcommerceTileDataModel(
            userName = "Split GroupPaid",
            paymentAmount = "$125,000.00",
            paymentDate = "Confirmation: ACME2376123945",
            paymentStatus = "Request Paid",
            userInitials = "SG",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showPendingPayment() {
        binding.ecommerceTileScenarioPendingPayment.setAccountData(tileData = EcommerceTileDataModel(
            cancelRequestButton = EcommerceTileButton.EcommerceButtonModel(label = "Cancel Request", visibility = true, {}),
            daysRemaining = EcommerceTileComponents.EcommerceComponentModel(label ="They have 4 days left to respond", visibility = true),
            userName = "Pending Payment",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Sent",
            userInitials = "PP",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showPendingPaymentWithMemo() {
        binding.ecommerceTileScenarioPendingPaymentWithMemo.setAccountData(tileData = EcommerceTileDataModel(
            cancelRequestButton = EcommerceTileButton.EcommerceButtonModel(label = "Cancel Request", visibility = true) {
                Toast.makeText(context, "Standard Request: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                        .show()
            },
            memo = EcommerceTileComponents.EcommerceComponentModel(label ="had a great time at the concert!", visibility = true),
            daysRemaining = EcommerceTileComponents.EcommerceComponentModel(label ="They have 4 days left to respond", visibility = true),
            userName = "Pending PaymentMemo",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Sent",
            userInitials = "PP",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun showCancelPayment() {
        binding.ecommerceTileScenarioCancelPayment.setAccountData(tileData = EcommerceTileDataModel(
            cancelRequestButton = EcommerceTileButton.EcommerceButtonModel(label = "Cancel Payment", visibility = true) {
                Toast.makeText(context, "Cancel Payment: Button Clicked !", Toast.LENGTH_SHORT)
                    .show()
            },
            daysRemaining = EcommerceTileComponents.EcommerceComponentModel(label ="They have 4 days left to enroll", visibility = true),
            userName = "Cancel Payment",
            paymentAmount = "$125,000.00",
            paymentDate = "Oct 24, 2024",
            paymentStatus = "Request Sent",
            userInitials = "CP",
            ecommerceBadge = BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON
        )
        )
    }

    private fun initUI() {
        binding.ecommerceTileScenarioStandardRequest.setEcommerceTileInfo(
            tileClickListener = {
                Toast.makeText(context, "Standard Request: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                    .show()
            }, actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { }
        )
        binding.ecommerceTileScenarioStandardRequestWithMemo.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Standard Request: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()}, actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { }
        )
        binding.ecommerceTileScenarioRequestReceived.setEcommerceTileInfo(
            tileClickListener = {
                Toast.makeText(context, "RequestReceived: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                    .show()
            }, actionButtonDeclineClickListener = {
                Toast.makeText(context, "RequestReceived: Decline Button Clicked !", Toast.LENGTH_SHORT)
                    .show()},
            actionButtonSendClickListener = {
                Toast.makeText(context, "RequestReceived: Send Button Clicked !", Toast.LENGTH_SHORT)
                    .show()},
            actionButtonCancelClickListener = { }
        )

        binding.ecommerceTileScenarioRequestReceivedWithMemo.setEcommerceTileInfo(
            tileClickListener = {
                Toast.makeText(context, "RequestReceived: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                    .show()
            }, actionButtonDeclineClickListener = {
                Toast.makeText(context, "RequestReceived: Decline Button Clicked !", Toast.LENGTH_SHORT)
                    .show()},
            actionButtonSendClickListener = {
                Toast.makeText(context, "RequestReceived: Send Button Clicked !", Toast.LENGTH_SHORT)
                    .show()},
            actionButtonCancelClickListener = { }
        )

        binding.ecommerceTileScenarioSplitRequestPaid.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Split Request Paid: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { },
            actionGroupTextClickListener = {
                Toast.makeText(context, "Split Request Paid: Group Text Clicked !", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        binding.ecommerceTileScenarioSplitRequestPaidWithMemo.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Split Request Paid Memo: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { },
            actionGroupTextClickListener = {
                Toast.makeText(context, "Split Request Paid: Group Text With Memo Clicked !", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        binding.ecommerceTileScenarioSplitRequestGroup.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Group Split Request: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { }
        )
        binding.ecommerceTileScenarioSplitRequestGroupWithMemo.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Group Split Request Memo: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { }
        )

        binding.ecommerceTileScenarioSplitGroupSent.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Split Group Sent: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { Toast.makeText(context, "SplitGroup Sent: cancel request!", Toast.LENGTH_SHORT)
                .show()}
        )

        binding.ecommerceTileScenarioSplitGroupPaid.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Split Group Paid: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { }
        )

        binding.ecommerceTileScenarioPendingPayment.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "RequestReceived: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { Toast.makeText(context, "Pending Payment: cancel request!", Toast.LENGTH_SHORT)
                .show()}        )
        binding.ecommerceTileScenarioPendingPaymentWithMemo.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "RequestReceived: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { Toast.makeText(context, "Pending Payment Memo: cancel request!", Toast.LENGTH_SHORT)
                .show()}        )
        binding.ecommerceTileScenarioCancelPayment.setEcommerceTileInfo(
            tileClickListener = {Toast.makeText(context, "Cancel Payment: ecommerce tile Clicked !", Toast.LENGTH_SHORT)
                .show()},
            actionButtonDeclineClickListener = {},
            actionButtonSendClickListener = {},
            actionButtonCancelClickListener = { Toast.makeText(context, "Cancel Payment: cancel payment!", Toast.LENGTH_SHORT)
                .show()}        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}