package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestCardTileBinding
import ai.offside.mobile.android.component.ui.cardtile.CardTileData
import ai.offside.mobile.android.component.ui.cardtile.PrimaryDisplayInfo
import ai.offside.mobile.android.helper.testlabs.R

/**
 * Fragment class to test card tile with specific input fields scenarios.
 *
 */
class TestUIRedesignCardTileFragment : Fragment() {

    private lateinit var binding: FragmentTestCardTileBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestCardTileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initUI()
    }

    /**
     * Initialize card tile scenarios
     *
     */
    private fun initUI() {
        binding.cardTileItemScenario1.setCardTileInfo(CardTileData(PrimaryDisplayInfo(primaryInfo = "Manage Debit Card"), cardArtUrl = resources.getString(R.string.async_url)),
            tileClickListener = {
                Toast.makeText(context, "Scenario-1: Tile Clicked !", Toast.LENGTH_SHORT)
                    .show()
            })
        binding.cardTileItemScenario2.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                "Acme Business Options® Visa Signature® Credit Card", "x1225", true
            ),
            cardArtUrl = resources.getString(R.string.async_url),
            cardActionLabel = "Add Card"
        ), actionButtonClickListener = {
            Toast.makeText(context, "Scenario-2: Action Button Clicked !", Toast.LENGTH_SHORT)
                .show()
        })
        binding.cardTileItemScenario3.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                "Acme Business Options® Visa Signature® Credit Card", "x1226", true
            ),
            cardArtUrl = resources.getString(R.string.async_url),
            cardStatus = "Link Status"
        ), tileClickListener = {
            Toast.makeText(context, "Scenario-3: Tile Clicked !", Toast.LENGTH_SHORT).show()
        })
        binding.cardTileItemScenario4.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                "Acme Business Options® Visa Signature® Credit Card", "x1231", true
            ),
            cardArtUrl = resources.getString(R.string.async_url),
            secondaryDisplayInfo = "Available balance: $1,500.35"
        ), tileClickListener = {
            Toast.makeText(context, "Scenario-4: Tile Clicked !", Toast.LENGTH_SHORT).show()
        })
        binding.cardTileItemScenario5.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                "Acme Business Options® Visa Signature® Credit Card", "x1232", true
            ),
            cardArtUrl = resources.getString(R.string.async_url),
            secondaryDisplayInfo = "Available balance: $1,500.68"
        ), checkedListener = { _: CompoundButton, checked: Boolean ->
            Toast.makeText(
                context, "Scenario-5: Check Box is selected = $checked !", Toast.LENGTH_SHORT
            ).show()
        })
        binding.cardTileItemScenario6.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                "Acme Business Options® Visa Signature® Credit Card", "x1233", true
            ),
            cardArtUrl = resources.getString(R.string.async_url),
            secondaryDisplayInfo = "Available balance: $1,500.90"
        ), checkedListener = { _: CompoundButton, checked: Boolean ->
            Toast.makeText(
                context, "Scenario-6: Radio Button is selected = $checked !", Toast.LENGTH_SHORT
            ).show()
        })
        binding.cardTileItemScenario7.setCardTileInfo(
            CardTileData(PrimaryDisplayInfo(primaryInfo = "Card Name"), cardArtUrl = resources.getString(R.string.async_url))
        )
        binding.cardTileItemCheckboxWrap1.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                primaryInfo = "Acme Visa Debit Card",
                primarySuffixInfo = "x1227",
                isShowMiddleEllipses = false
            ),
            cardArtUrl = resources.getString(R.string.async_url)
        ), checkedListener = { _: CompoundButton, checked: Boolean ->
            Toast.makeText(
                context, "Single Line Check Box is selected = $checked !", Toast.LENGTH_SHORT
            ).show()
        })
        binding.cardTileItemRadioWrap2.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                primaryInfo = "Acme Visa Debit Card",
                primarySuffixInfo = "x1227",
                isShowMiddleEllipses = false
            ),
            cardArtUrl = resources.getString(R.string.async_url)
        ), checkedListener = { _: CompoundButton, checked: Boolean ->
            Toast.makeText(
                context, "Single Line Radio Button is selected = $checked !", Toast.LENGTH_SHORT
            ).show()
        })
        binding.cardTileItemCheckboxWrap3.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                primaryInfo = "Acme Business Options® Visa Signature®",
                primarySuffixInfo = "x1227",
                isShowMiddleEllipses = false
            ),
            cardArtUrl = resources.getString(R.string.async_url)
        ), checkedListener = { _: CompoundButton, checked: Boolean ->
            Toast.makeText(
                context, "Wrapping text  Check Box is selected = $checked !", Toast.LENGTH_SHORT
            ).show()
        })
        binding.cardTileItemRadioWrap4.setCardTileInfo(CardTileData(
            PrimaryDisplayInfo(
                primaryInfo = "Acme Business Options® Visa Signature®",
                primarySuffixInfo = "x1227",
                isShowMiddleEllipses = false
            ),
            cardArtUrl = resources.getString(R.string.async_url)
        ), checkedListener = { _: CompoundButton, checked: Boolean ->
            Toast.makeText(
                context,
                "Wrapping text Radio Button is selected = $checked !",
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}