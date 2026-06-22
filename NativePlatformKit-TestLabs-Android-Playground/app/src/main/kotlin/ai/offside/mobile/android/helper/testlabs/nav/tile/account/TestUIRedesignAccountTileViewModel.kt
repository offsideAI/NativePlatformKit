package ai.offside.mobile.android.helper.testlabs.nav.tile.account

import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ai.offside.mobile.android.component.ui.slider.data.SliderButtonData
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentData
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataModel
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataProvider
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileButton
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileData
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileDataModel
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelBigDecimalValuePair
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTilePaymentStatus
import java.math.BigDecimal

class TestUIRedesignAccountTileViewModel : ViewModel() {
    private val _accounts = MutableLiveData<List<AccountTileDataModel>>().apply {
        value = listOf(
            AccountTileDataModel(object : AccountTileData.AccountTileWithPaymentDueAndButton {
                override val accountName = "AC1&2 Credit Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {
                    Log.d("AccountTile", tile.displayName)
                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val paymentDueAmountLabel = ""
                override val paymentDueDateLabel = ""
                override val paymentStatus: AccountTilePaymentStatus =
                    AccountTilePaymentStatus.NO_DUE
                override val primaryButtonData = object : AccountTileButton {
                    override val label = "Make a Payment"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithPaymentDueAndButton {
                override val accountName = "AC3 Credit Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {
                    Log.d("AccountTile", tile.displayName)
                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val paymentDueAmountLabel = "Min. Payment $100"
                override val paymentDueDateLabel = "due May 20, 2024"
                override val paymentStatus: AccountTilePaymentStatus =
                    AccountTilePaymentStatus.HAS_DUE
                override val primaryButtonData = object : AccountTileButton {
                    override val label = "Make a Payment"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithPaymentDueAndButton {
                override val accountName = "AC3 Credit Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val paymentDueAmountLabel = "Min. Payment $100"
                override val paymentDueDateLabel = "due May 20, 2024"
                override val paymentStatus: AccountTilePaymentStatus =
                    AccountTilePaymentStatus.OVERDUE
                override val primaryButtonData = object : AccountTileButton {
                    override val label = "Make a Payment"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithPrimaryMicroButton {
                override val accountName = "AC4 Lending Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val primaryButtonData = object : AccountTileButton {
                    override val label = "Test Button"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData,
                AccountTileData.AccountTileWidgetMoreInfoButton {
                override val displayName = "AC5 Reserve x5344"
                override val accountName = "AC5 Reserve"
                override val maskedAccountNumber = "x5344"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData,
                AccountTileData.AccountTileWidgetMoreInfoButton {
                override val accountName = "AC6 Acme Performance"
                override val maskedAccountNumber = "x1123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData,
                AccountTileData.AccountTileWidgetMoreInfoButton {
                override val accountName = "AC7 Acme Business Checking Plus"
                override val maskedAccountNumber = "x4048"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData,
                AccountTileData.AccountTileWidgetMoreInfoButton {
                override val accountName = "AC8 Reserve"
                override val maskedAccountNumber = "x5344"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.valueOf(888888888888888)
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithRateChange {
                override val accountName = "AC9 Investment Account"
                override val maskedAccountNumber = "x5344"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val rateAmount = BigDecimal.valueOf(123.44)
                override val ratePercentage = 4.0
                override val changeLabel = "Day Change"
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithRateChange {
                override val accountName = "AC10 Investment Account"
                override val maskedAccountNumber = "x5344"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val rateAmount = BigDecimal.valueOf(-10)
                override val ratePercentage = -4.0
                override val changeLabel = "Day Change"
            }, sliderComponentData),
            AccountTileDataModel(object :
                AccountTileData.AccountTileWithTwoLabelValuePairAndPricingUpdates {
                override val accountName = "AC11 Net Worth"
                override val maskedAccountNumber = "x5344"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Total"
                override fun onClick(tile: AccountTileData) {

                }

                override val secondLabelValuePair =
                    AccountTileLabelBigDecimalValuePair(
                        "Liabilities Total",
                        BigDecimal.valueOf(100.12)
                    )
                override val labelValuePair =
                    AccountTileLabelBigDecimalValuePair("Assets Total", BigDecimal.valueOf(54.12))
                override val pricingUpdateLabel = "As of Dec. 16, 2023 at 11:20am ET"
                override val learnMoreButton = object : AccountTileButton {
                    override val label = "Learn More"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object :
                AccountTileData.AccountTileWithRateChangeAndPricingUpdates {
                override val accountName = "AC12 Total"
                override val maskedAccountNumber = "x5344"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Total"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val rateAmount = BigDecimal.valueOf(-10)
                override val ratePercentage = -4.0
                override val changeLabel = "Day Change"
                override val pricingUpdateLabel = "As of Dec. 16, 2023 at 11:20am ET"
                override val learnMoreButton = object : AccountTileButton {
                    override val label = "Learn More"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithPrimaryMicroButton {
                override val accountName = "AC13 HYS Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val primaryButtonData = object : AccountTileButton {
                    override val label = "Fund"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithLabelValuePair {
                override val accountName = "AC14 HYS Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val labelValuePair = AccountTileLabelBigDecimalValuePair(
                    "Liabilities Total",
                    BigDecimal.valueOf(120.12)
                )
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithAlternateMicroButton {
                override val accountName = "AC15 Commercial Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Current Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val alternateButton = object : AccountTileButton {
                    override val label = "Test Button"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData.AccountTileWithLabelValuePair {
                override val accountName = "AC16 Spend Account"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Free Balance Until Apr. 23"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val labelValuePair = AccountTileLabelBigDecimalValuePair(
                    "Available Balance",
                    BigDecimal.valueOf(12.12)
                )
            }, sliderComponentData),
            AccountTileDataModel(object :
                AccountTileData.AccountTileWithTwoLabelValuePairButtonAndButtonHint {
                override val hintText = "Depositing to Business Checking x1234"
                override val accountName = "AC17 Mobile Accept"
                override val maskedAccountNumber = "x123"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.ZERO
                override val balanceLabel = "Daily Net Sales"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
                override val labelValuePair =
                    AccountTileLabelBigDecimalValuePair("Gross Sales", BigDecimal.valueOf(123.12))
                override val secondLabelValuePair =
                    AccountTileLabelBigDecimalValuePair("Fees", BigDecimal.valueOf(32.12))
                override val primaryButtonData = object : AccountTileButton {
                    override val label = "Test Button"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData),
            AccountTileDataModel(object : AccountTileData,
                AccountTileData.AccountTileWidgetMoreInfoButton {
                override val accountName = "AC5 Reserve"
                override val maskedAccountNumber = "x5344"
                override val displayName = "$accountName $maskedAccountNumber"
                override val balance = BigDecimal.valueOf(-90)
                override val balanceLabel = "Available Balance"
                override fun onClick(tile: AccountTileData) {

                }

                override val moreButton = object : AccountTileButton {
                    override val label = "More Menu"
                    override fun onButtonClick(view: View) {
                    }
                }
            }, sliderComponentData)
        )
    }

    val accounts: LiveData<List<AccountTileDataModel>> = _accounts

    private val sliderComponentData
        get() = SliderComponentDataModel(
            SliderComponentDataProvider.getSliderComponentDataWithLeftButton(
                getSliderButtonData(
                    "Pin Account",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_solid
                ),
                getSliderButtonData(
                    "Make Transfer",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_transfer
                ),
                getSliderButtonData(
                    "Deposit Check",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_informative_check
                ),
                getSliderButtonData(
                    "Manage Card",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_card_regular
                )
            )
        )


    private fun getSliderButtonData(label: String, @DrawableRes iconRes: Int): SliderButtonData {
        return object : SliderButtonData {
            override val label = label
            override val icon = iconRes
            override fun onButtonClick(button: SliderButtonData) {
                Log.d("TEST", label)
            }
        }
    }
}