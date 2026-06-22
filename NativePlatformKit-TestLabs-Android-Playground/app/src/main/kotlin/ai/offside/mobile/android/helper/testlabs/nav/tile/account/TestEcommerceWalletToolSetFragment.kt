package ai.offside.mobile.android.helper.testlabs.nav.tile.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.listitem.data.getListItemWithIcon
import ai.offside.mobile.android.component.ui.listitem.data.getSimpleListItem
import ai.offside.mobile.android.component.ui.tile.ecommercewallettoolset.data.EcommerceWalletToolSetData
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestEcommerceWalletToolSetBinding

class TestEcommerceWalletToolSetFragment: Fragment(R.layout.fragment_test_ecommerce_wallet_tool_set) {

    private var _binding: FragmentTestEcommerceWalletToolSetBinding? = null
    private val binding: FragmentTestEcommerceWalletToolSetBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestEcommerceWalletToolSetBinding.bind(view)
        binding.ecommerceWalletToolSetOne.setFeatureList(getFeatureList(2, true))
        binding.ecommerceWalletToolSetTwo.setFeatureList(getFeatureList(4, false))
        binding.ecommerceWalletToolSetThree.setFeatureList(getFeatureList(4, true))
    }

    private fun getFeatureList(featureCount: Int, isCustomizable: Boolean): EcommerceWalletToolSetData {
        val featureList = arrayListOf(
            getListItemWithIcon(
                "Spending & Budgets",
                ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_spending_and_budget
            ) {
                Toast.makeText(context, "Spending & Budgets Clicked", Toast.LENGTH_SHORT).show()
            },
            getListItemWithIcon(
                "Low Cash Mode",
                ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_cash_mode
            ) {
                Toast.makeText(context, "Low Cash Mode Clicked", Toast.LENGTH_SHORT).show()
            }
        )
        if (featureCount > 2) {
            featureList.add(
                getListItemWithIcon(
                    "Savings Goals",
                ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_savings_goals
                ){
                    Toast.makeText(context, "Savings Goals Clicked", Toast.LENGTH_SHORT).show()
                }
            )
        }
        if (featureCount > 3) {
            featureList.add(
                getListItemWithIcon(
                        "Savings Rules",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_feature_ecommerce_wallet_savings_rules
                ) {
                    Toast.makeText(context, "Savings Rules Clicked", Toast.LENGTH_SHORT).show()
                }
            )
        }
        return EcommerceWalletToolSetData(
            featureList,
            getSimpleListItem(label = "Customize Tools", enabled = isCustomizable ) {
                Toast.makeText(context, "Customize Tools Clicked", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}