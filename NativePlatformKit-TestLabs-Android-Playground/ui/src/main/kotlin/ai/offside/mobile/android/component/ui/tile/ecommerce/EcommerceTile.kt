package ai.offside.mobile.android.component.ui.tile.ecommerce
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StyleRes
import com.google.android.material.card.MaterialCardView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.TileEcommerceBinding
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.BadgeDisplayStatus
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.ButtonType
import ai.offside.mobile.android.component.ui.tile.ecommerce.data.EcommerceTileDataModel


/**
 * Dynamic Ecommerce Tile display
 * @param context
 * @param attrs
 * @param defaultStyleAttr
 */
class EcommerceTile @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyleAttr: Int = com.google.android.material.R.attr.materialCardViewFilledStyle
) : MaterialCardView(context, attrs, defaultStyleAttr) {

    private val binding = TileEcommerceBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.ecommerceTileActionImageView.setImageResource(R.drawable.ic_actionable_caret)
        binding.ecommerceTileDecorativeClockImageView.setImageResource(R.drawable.ic_decorative_clock_thin)
        binding.ecommerceTileDecorativeGroupImageView.setImageResource(R.drawable.ic_decorative_group_solid)

        this.setCardTileBackgroundColor(R.style.Offside_Redesign_EcommerceBadge_transparent)
    }


    fun setAccountData(tileData: EcommerceTileDataModel) {
        binding.model = tileData

        when(tileData.ecommerceBadge){
            BadgeDisplayStatus.SMALL_REGISTERED_BUSINESS_ICON ->
                binding.ecommerceTileBadge.binding.ecommerceIcon.setImageResource(R.drawable.ic_feature_ecommerce_small_business_neutral)
            BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON ->
                binding.ecommerceTileBadge.binding.ecommerceIcon.setImageResource(R.drawable.ic_feature_ecommerce_small_person_neutral)
            BadgeDisplayStatus.SMALL_UNREGISTERED_BUSINESS_ICON ->
                binding.ecommerceTileBadge.binding.ecommerceIcon.setImageResource(R.drawable.ic_feature_ecommerce_small_business_neutral_unregistered)
            BadgeDisplayStatus.SMALL_UNREGISTERED_PERSON_ICON ->
                binding.ecommerceTileBadge.binding.ecommerceIcon.setImageResource(R.drawable.ic_feature_ecommerce_small_person_neutral_unregistered)
            BadgeDisplayStatus.SMALL_SELECTED_ICON ->
                binding.ecommerceTileBadge.binding.ecommerceIcon.setImageResource(R.drawable.ic_feature_ecommerce_small_selected_split_blue)
        }

        binding.ecommerceTileBadge.binding.ecommerceIconText.text = tileData.userInitials
        setCustomTileDescription(tileData)
        if (tileData.declineButton?.visibility == true) {
            setCustomButtonDescription(tileData, ButtonType.DECLINE)
        }
        if (tileData.sendButton?.visibility == true) {
            setCustomButtonDescription(tileData, ButtonType.SEND)
        }
        if (tileData.cancelRequestButton?.visibility == true) {
            if(tileData.cancelRequestButton.label == resources.getString(R.string.cancel_request_label)) {
                setCustomButtonDescription(tileData, ButtonType.CANCEL_REQUEST)
            }
            else {
                setCustomButtonDescription(tileData, ButtonType.CANCEL_PAYMENT)
            }
        }
        if (tileData.partOfGroupRequest?.visibility == true) {
            setCustomPartOfGroupDescription(tileData)
        }
    }

    /**
     * A11Y will read the whole tile as "{User Name}, ecommerce user, {Payment Status} {Payment Amount}, {Days Remaining, button"
     *
     * @param : tileData : EcommerceTileDataModel -> A structure of User defined data about the Ecommerce transaction.
     */
    private fun setCustomTileDescription(tileData: EcommerceTileDataModel) {
        val userName = tileData.userName
        val paymentStatus = tileData.paymentStatus
        val paymentAmount = tileData.paymentAmount
        var daysRemaining = tileData.daysRemaining?.label ?: ""
        val paymentDate = tileData.paymentDate
        var memoText = tileData.memo?.label ?: ""
        var registeredUser = ""
        val buttonLabel = "button"

        when(tileData.ecommerceBadge){
            BadgeDisplayStatus.SMALL_REGISTERED_BUSINESS_ICON, BadgeDisplayStatus.SMALL_REGISTERED_PERSON_ICON -> {
                registeredUser = resources.getString(R.string.ecommerce_user_label) + ","
            }

            else -> {}
        }

        if (daysRemaining.isNotEmpty()){
            daysRemaining = "$daysRemaining,"
        }

        if (memoText.isNotEmpty()){
            memoText = "$memoText,"
        }

        binding.ecommerceTileLayout.setContentDescription(String.format("%s, %s %s %s, %s, %s %s  %s", userName,
            registeredUser, paymentStatus, paymentAmount, paymentDate, daysRemaining, memoText, buttonLabel))
    }

    /**
     * A11Y will read the whole tile as "{Button Type} {Payment Amount} to {User Name}, button"
     *
     * @param : tileData : EcommerceTileDataModel -> A structure of User defined data about the Ecommerce transaction.
     * @param : buttonType : String -> Type of the button, either Decline, Send, or Cancel Request.
     */
    private fun setCustomButtonDescription(tileData: EcommerceTileDataModel, buttonType: ButtonType) {
        val userName = tileData.userName

        val paymentAmount = tileData.paymentAmount

        val formattedString: String

        when(buttonType){
            ButtonType.DECLINE -> {
                formattedString = resources.getString(R.string.formatted_button_string, resources.getString(R.string.ecommerce_tile_decline_label),
                    paymentAmount, userName)
                binding.ecommerceTileDeclineButton.setContentDescription(String.format("%s", formattedString))
            }
            ButtonType.SEND -> {
                formattedString = resources.getString(R.string.formatted_button_string, resources.getString(R.string.ecommerce_tile_send_label),
                    paymentAmount, userName)
                binding.ecommerceTileSendButton.setContentDescription(String.format("%s", formattedString))
            }
            ButtonType.CANCEL_REQUEST -> {
                formattedString = resources.getString(R.string.formatted_button_string, resources.getString(R.string.cancel_request_label),
                    paymentAmount, userName)
                binding.ecommerceTileCancelRequestButton.setContentDescription(String.format("%s", formattedString))
            }

            ButtonType.CANCEL_PAYMENT -> {
                formattedString = resources.getString(R.string.formatted_button_string, resources.getString(R.string.cancel_payment_label),
                    paymentAmount, userName)
                binding.ecommerceTileCancelRequestButton.setContentDescription(String.format("%s", formattedString))
            }
        }

    }

    /**
     * A11Y will read the whole tile as "Part of Group Request for {Payment Amount} from {User Name}, button"
     *
     * @param : tileData : EcommerceTileDataModel -> A structure of User defined data about the Ecommerce transaction.
     */
    private fun setCustomPartOfGroupDescription(tileData: EcommerceTileDataModel) {
        val userName = tileData.userName
        val paymentAmount = tileData.paymentAmount

        val formattedString = resources.getString(R.string.formatted_part_of_group_string, resources.getString(R.string.part_of_a_group_request),
            paymentAmount, userName)
        binding.ecommerceTileGroupRequestText.setContentDescription(String.format("%s", formattedString))

    }

    /**
     * Function to set background ecommerce tile color
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
     * Function to set card tile info.
     *
     * @param tileClickListener click listener to card tile view.
     * @param actionButtonDeclineClickListener click listener for ecommerce tile decline action button.
     * @param actionButtonSendClickListener click listener for ecommerce tile send action button.
     * @param actionButtonCancelClickListener click listener for ecommerce tile cancel request action button.
     * @param actionGroupTextClickListener click listener for ecommerce tile part of group request action button.
     *
     */
    fun setEcommerceTileInfo(
        tileClickListener: (View) -> Unit = {},
        actionButtonDeclineClickListener: (View) -> Unit = {},
        actionButtonSendClickListener: (View) -> Unit = {},
        actionButtonCancelClickListener: (View) -> Unit = {},
        actionGroupTextClickListener: (View) -> Unit = {}

    ) {

        binding.ecommerceTileLayout.setOnClickListener{
            tileClickListener(it)
        }

        binding.ecommerceTileDeclineButton.setOnClickListener {
            actionButtonDeclineClickListener(it)
        }

        binding.ecommerceTileSendButton.setOnClickListener {
            actionButtonSendClickListener(it)
        }

        binding.ecommerceTileCancelRequestButton.setOnClickListener{
            actionButtonCancelClickListener(it)
        }

        binding.ecommerceTileGroupRequestText.setOnClickListener{
            actionGroupTextClickListener(it)
        }

    }
}