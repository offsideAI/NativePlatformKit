package ai.offside.mobile.android.component.ui.tile.ecommerce

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import com.google.android.material.card.MaterialCardView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.BadgeEcommerceBinding


/**
 * dynamic ecommerce badge display
 * @param context
 * @param attrs
 * @param defaultStyleAttr
 */
class EcommerceBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyleAttr: Int = com.google.android.material.R.attr.materialCardViewFilledStyle
) : MaterialCardView(context, attrs, defaultStyleAttr) {

    enum class DisplayState {
        SMALL_REGISTERED_BUSINESS_ICON,
        LARGE_REGISTERED_BUSINESS_ICON,
        SMALL_REGISTERED_PERSON_ICON,
        LARGE_REGISTERED_PERSON_ICON,
        SMALL_UNREGISTERED_BUSINESS_ICON,
        LARGE_UNREGISTERED_BUSINESS_ICON,
        SMALL_UNREGISTERED_PERSON_ICON,
        LARGE_UNREGISTERED_PERSON_ICON,
        SMALL_SELECTED_ICON,
        LARGE_SELECTED_ICON,
        UNDEFINED
    }
    val binding = BadgeEcommerceBinding.inflate(LayoutInflater.from(context), this, true)
    private val defaultDisplayState = DisplayState.UNDEFINED.ordinal
    private var displayState = defaultDisplayState

    init {
        val ecommerceBadgeAttributes: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.EcommerceBadge)
        val ecommerceInitials = ecommerceBadgeAttributes.getString(R.styleable.EcommerceBadge_ecommerceInitials)
        val ecommerceFontSize = ecommerceBadgeAttributes.getDimension(R.styleable.EcommerceBadge_ecommerceFontSize, 0F)
        binding.ecommerceIconText.setTextSize(TypedValue.COMPLEX_UNIT_PX,ecommerceFontSize)
        binding.ecommerceIconText.setText(ecommerceInitials)
        displayState =
            ecommerceBadgeAttributes.getInt(R.styleable.EcommerceBadge_ecommerceDisplayState, defaultDisplayState)

        initEcommerceBadgeUI(displayState)
        ecommerceBadgeAttributes.recycle()
        this.setCardTileBackgroundColor(R.style.Offside_Redesign_EcommerceBadge_transparent)
    }

    /**
     * Set card badge background.
     * @param styleRes color to set background of ecommerce badge.
     */
    @SuppressWarnings("unused")
    fun setCardTileBackgroundColor(@StyleRes styleRes: Int) {
        val cardStyleAttributes = context.obtainStyledAttributes(styleRes, R.styleable.EcommerceBadge)
        setCardBackgroundColor(
            cardStyleAttributes.getColor(
                R.styleable.EcommerceBadge_badgeBackgroundColor, 0
            )
        )
        cardStyleAttributes.recycle()
    }

    /**
     * Initialize card Ecommerce badge based on display state.
     *
     * @param state display state.
     */
    private fun initEcommerceBadgeUI(state: Int) {
        when (state) {
            DisplayState.SMALL_REGISTERED_BUSINESS_ICON.ordinal -> initSmallRegisteredBusiness()
            DisplayState.LARGE_REGISTERED_BUSINESS_ICON.ordinal -> initLargeRegisteredBusiness()
            DisplayState.SMALL_REGISTERED_PERSON_ICON.ordinal -> initSmallRegisteredPerson()
            DisplayState.LARGE_REGISTERED_PERSON_ICON.ordinal -> initLargeRegisteredPerson()
            DisplayState.SMALL_UNREGISTERED_BUSINESS_ICON.ordinal -> initSmallUnregisteredBusiness()
            DisplayState.LARGE_UNREGISTERED_BUSINESS_ICON.ordinal -> initLargeUnregisteredBusiness()
            DisplayState.SMALL_UNREGISTERED_PERSON_ICON.ordinal -> initSmallUnregisteredPerson()
            DisplayState.LARGE_UNREGISTERED_PERSON_ICON.ordinal -> initLargeUnregisteredPerson()
            DisplayState.SMALL_SELECTED_ICON.ordinal -> initSmallSelected()
            DisplayState.LARGE_SELECTED_ICON.ordinal -> initLargeSelected()
            DisplayState.UNDEFINED.ordinal -> {
                Log.i(EcommerceBadge::class.simpleName, "DisplayState for ecommerce badge is undefined")
            }

            else -> {}
        }
    }

    /**
     * Function to set ecommerce badge to small registered business icon
     */
    private fun initSmallRegisteredBusiness() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_small_business_neutral)
        }
    }

    /**
     * Function to set ecommerce badge to large registered business icon
     */
    private fun initLargeRegisteredBusiness() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_large_business_neutral)
        }
    }

    /**
     * Function to set ecommerce badge to small registered person icon
     */
    private fun initSmallRegisteredPerson() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_small_person_neutral)
        }
    }

    /**
     * Function to set ecommerce badge to large registered person icon
     */
    private fun initLargeRegisteredPerson() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_large_person_neutral)
        }
    }

    /**
     * Function to set ecommerce badge to small unregistered business icon
     */
    private fun initSmallUnregisteredBusiness() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_small_business_neutral_unregistered)
        }
    }

    /**
     * Function to set ecommerce badge to large unregistered business icon
     */
    private fun initLargeUnregisteredBusiness() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_large_business_neutral_unregistered)
        }
    }

    /**
     * Function to set ecommerce badge to small unregistered person icon
     */
    private fun initSmallUnregisteredPerson() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_small_person_neutral_unregistered)
        }
    }

    /**
     * Function to set ecommerce badge to large unregistered person icon
     */
    private fun initLargeUnregisteredPerson() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_large_person_neutral_unregistered)
        }
    }

    /**
     * Function to set ecommerce badge to small selected icon
     */
    private fun initSmallSelected() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_small_selected_split_blue)
        }
    }

    /**
     * Function to set ecommerce badge to large selected icon
     */
    private fun initLargeSelected() {
        binding.ecommerceIcon.apply {
            setImageResource(R.drawable.ic_feature_ecommerce_large_selected_split_blue)
        }
    }

}