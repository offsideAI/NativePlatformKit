package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignFeatureBasedIconsBinding

class TestUIRedesignFeatureBasedIconsFragment: Fragment() {

    private lateinit var binding: FragmentTestUiRedesignFeatureBasedIconsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignFeatureBasedIconsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ecommerceSmallSelectedSplitIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_selected_split_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_small_selected_split_blue)
        }
        binding.ecommerceLargeSelectedSplitIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_selected_split_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_large_selected_split_blue)
        }
        binding.ecommerceSmallPersonIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_person_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_small_person_neutral)
        }
        binding.ecommerceLargePersonIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_person_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_large_person_neutral)
        }
        binding.ecommerceSmallBusinessIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_business_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_small_business_neutral)
        }
        binding.ecommerceLargeBusinessIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_business_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_large_business_neutral)
        }
        binding.paymentSendIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_payment_send_icon_label)
            featureBasedIconsDecorativeIcon.setBackgroundColor(
                MaterialColors.getColor(
                    featureBasedIconsDecorativeIcon,
                    ai.offside.mobile.android.component.ui.R.attr.offside_primary
                )
            )
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_payment_send_blue)
        }
        binding.paymentRequestIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_payment_request_icon_label)
            featureBasedIconsDecorativeIcon.setBackgroundColor(
                MaterialColors.getColor(
                    featureBasedIconsDecorativeIcon,
                    ai.offside.mobile.android.component.ui.R.attr.offside_primary
                )
            )
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_request_blue)
        }
        binding.ecommerceBadgeIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_badge_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_z_badge)
        }
        binding.ecommerceWalletCalendarIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_calendar_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_calendar)
        }
        binding.ecommerceWalletMoneyBarIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_money_bar_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_money_bar)
        }
        binding.ecommerceWalletCashModeIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_cash_mode_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_cash_mode)
        }
        binding.ecommerceWalletSpendingBudgetsIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_spending_and_budgets_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_spending_and_budget)
        }
        binding.ecommerceWalletSavingRulesIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_saving_rules_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_savings_rules)
        }
        binding.ecommerceWalletSaveNowIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_save_now_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_save_now)
        }
        binding.ecommerceWalletSavingGoalsIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_saving_goals_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_savings_goals)
        }
        binding.onlinePaymentsIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_online_payments_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_online_payments)
        }
        binding.offsideAdvancesIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_offside_advances_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_offside_advances)
        }
        binding.offsideTransfersIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_offside_transfers_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_offside_transfers)
        }
        binding.externalTransfersIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_external_transfers_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_external_transfers)
        }
        binding.domesticWiresIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_domestic_wires_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_domestic_wires)
        }
        binding.internationalTransfersIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_international_transfers_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_international_transfer)
        }
        binding.ecommerceWalletAccountToolsIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_wallet_account_tools_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_account_tools_with_label)
        }
        binding.offsidePaymentsIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_offside_payments_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_main_page_offside_payments)
        }
        binding.ecommerceLogoIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_ecommerce_logo_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_logo_purple)
        }
        binding.pazeLogoIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_paze_logo_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_paze_paze_logo)
        }
        binding.pazeLogoIconLayout.apply {
            featureBasedIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_paze_logo_icon_label)
            featureBasedIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_feature_paze_paze_logo)
        }
    }
}