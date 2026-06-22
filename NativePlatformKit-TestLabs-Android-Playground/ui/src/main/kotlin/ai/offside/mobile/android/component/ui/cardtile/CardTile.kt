package ai.offside.mobile.android.component.ui.cardtile

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import androidx.annotation.StyleRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.CheckBoxA11y
import ai.offside.mobile.android.component.ui.a11y.RadioButtonA11y
import ai.offside.mobile.android.component.ui.databinding.CardTileBinding
import ai.offside.mobile.android.component.ui.extensions.setTextWithMiddleEllipses
import ai.offside.mobile.android.component.ui.extensions.toEnum


/**
 * Card Tile with Specific Input Field:
 * Card Art Image, Action Button, Radio-Button / Check-box, Link Status, Actionable caret.
 * @param context
 * @param attrs
 * @param defaultStyleAttr
 */
class CardTile @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyleAttr: Int = com.google.android.material.R.attr.materialCardViewFilledStyle
) : MaterialCardView(context, attrs, defaultStyleAttr) {

    enum class DisplayState {
        SINGLE_LINE_CARD_TILE,
        CARD_TILE_WITH_BUTTON,
        CARD_TILE_WITH_STATUS,
        TWO_LINE_CARD_TILE,
        CARD_TILE_WITH_CHECKBOX,
        CARD_TILE_WITH_RADIO_BUTTON,
        CARD_TILE_REVIEW,
        CARD_TILE_WITH_CHECKBOX_WRAPPING_TEXT,
        CARD_TILE_WITH_RADIO_BUTTON_WRAPPING_TEXT,
        UNDEFINED
    }

    private val binding = CardTileBinding.inflate(LayoutInflater.from(context), this, true)
    private val defaultDisplayState: DisplayState = DisplayState.UNDEFINED
    private var displayState = defaultDisplayState

    init {
        val cardTileAttributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CardTile)
        val cardBoxRadius = cardTileAttributes.getDimension(R.styleable.CardTile_cardBoxRadius, 0f)
        val isShowDivider = cardTileAttributes.getBoolean(R.styleable.CardTile_cardBoxShowDividers, true)
        cardTileAttributes.getInt(R.styleable.CardTile_displayState, defaultDisplayState.ordinal)
            .toEnum<DisplayState>()?.let {
            displayState = it
        }
        this.setCardTileBackgroundColor(R.style.Offside_Redesign_CardTile_BoxSquareStyle_White)
        initCardTileUI(displayState)
        setCardTileBoxBorder(cardBoxRadius)
        setDividerVisibility(isShowDivider)
        cardTileAttributes.recycle()
    }

    /**
     * Initialize card tile UI based on display state.
     *
     * @param state display state.
     */
    private fun initCardTileUI(state: DisplayState) {
        when (state) {
            DisplayState.SINGLE_LINE_CARD_TILE -> initSingleLineCardTile()
            DisplayState.CARD_TILE_WITH_BUTTON -> initCardTileWithButton()
            DisplayState.CARD_TILE_WITH_STATUS -> initCardTileWithStatusLink()
            DisplayState.TWO_LINE_CARD_TILE -> initTwoLineCardTile()
            DisplayState.CARD_TILE_WITH_CHECKBOX -> initCardTileWithCheckbox()
            DisplayState.CARD_TILE_WITH_RADIO_BUTTON -> initCardTileWithRadioButton()
            DisplayState.CARD_TILE_REVIEW -> initCardTileReview()
            DisplayState.CARD_TILE_WITH_CHECKBOX_WRAPPING_TEXT -> initCardTileWithCheckboxWithWrappingText()
            DisplayState.CARD_TILE_WITH_RADIO_BUTTON_WRAPPING_TEXT -> initCardTileWithRadioButtonWithWrappingText()
            DisplayState.UNDEFINED -> {
                Log.i(CardTile::class.simpleName, "DisplayState for card tile is undefined")
            }
            else -> {}
        }
    }

    /**
     * Set display state of card tile.
     *
     * @param state display state - [DisplayState]
     */
    fun setDisplayState(state: DisplayState) {
        displayState = state
        initCardTileUI(displayState)
    }

    /**
     * Function to set background card tile color
     */
    @SuppressWarnings("unused")
    fun setCardTileBackgroundColor(@StyleRes styleRes: Int) {
        val cardStyleAttributes = context.obtainStyledAttributes(styleRes, R.styleable.CardTile)
        setCardBackgroundColor(
            cardStyleAttributes.getColor(
                R.styleable.CardTile_cardBoxBackgroundColor, 0
            )
        )
        cardStyleAttributes.recycle()
    }

    /**
     * Function to set divider visibility
     *
     * @param isShowDivider true to make it visible otherwise gone
     */
    @SuppressWarnings("unused")
    fun setDividerVisibility(isShowDivider: Boolean) {
        when {
            isShowDivider -> binding.cardTileDivider.apply { visibility = VISIBLE }
            else -> binding.cardTileDivider.apply { visibility = GONE }
        }
    }

    /**
     * Function to set card tile box border radius.
     *
     * @param cardBoxRadius radius to set to card tile box.
     */
    @SuppressWarnings("unused")
    fun setCardTileBoxBorder(cardBoxRadius: Float) {
        shapeAppearanceModel = shapeAppearanceModel.toBuilder()
            .setTopLeftCornerSize(cardBoxRadius)
            .setTopRightCornerSize(cardBoxRadius)
            .setBottomLeftCornerSize(cardBoxRadius)
            .setBottomRightCornerSize(cardBoxRadius)
            .build()
    }

    /**
     * Initialize single line card tile.
     */
    private fun initSingleLineCardTile() {
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleMediumTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimaryMediumText, this)
        }
    }

    /**
     * Initialize card tile with action button.
     */
    private fun initCardTileWithButton() {
        binding.cardTileActionButton.apply {
            visibility = VISIBLE
        }
        binding.cardTileViewActionImage.apply {
            visibility = GONE
        }
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleMediumTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimaryMediumText, this)
        }
    }

    /**
     * Initialize card tile with status link.
     */
    private fun initCardTileWithStatusLink() {
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleMediumTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimaryMediumText, this)
        }
        binding.cardTileLinkStatus.apply {
            visibility = VISIBLE
            setTextAppearance(R.style.Offside_Redesign_CardTile_LinkStatus)
        }
    }

    /**
     * Initialize two line card tile.
     */
    private fun initTwoLineCardTile() {
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleSmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimarySmallText, this)
        }
        binding.cardTileSecondaryInfo.apply {
            visibility = VISIBLE
            setTextAppearance(R.style.Offside_Redesign_BodySmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_SecondarySmallText, this)
        }
    }

    /**
     * Initialize card tile with checkbox.
     */
    private fun initCardTileWithCheckbox() {
        binding.cardTileSelectCheckBox.apply {
            visibility = VISIBLE
        }
        binding.actionStatusParent.apply {
            visibility = GONE
        }
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleSmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimarySmallText, this)
        }
        binding.cardTileSecondaryInfo.apply {
            visibility = VISIBLE
            setTextAppearance(R.style.Offside_Redesign_BodySmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_SecondarySmallText, this)
        }
    }

    /**
     * Initialize card tile with radio button.
     */
    private fun initCardTileWithRadioButton() {
        binding.cardTileSelectRadioButton.apply {
            visibility = VISIBLE
        }
        binding.actionStatusParent.apply {
            visibility = GONE
        }
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleSmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimarySmallText, this)
        }
        binding.cardTileSecondaryInfo.apply {
            visibility = VISIBLE
            setTextAppearance(R.style.Offside_Redesign_BodySmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_SecondarySmallText, this)
        }
    }

    /**
     * Initialize card tile review.
     */
    private fun initCardTileReview() {
        binding.actionStatusParent.apply {
            visibility = GONE
        }
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_BodyLargeTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimaryLargeText, this)
        }
    }

    /**
     * Initialize card tile with checkbox and wrapping text
     */
    private fun initCardTileWithCheckboxWithWrappingText() {
        binding.cardTileSelectCheckBox.apply {
            visibility = VISIBLE
        }
        binding.actionStatusParent.apply {
            visibility = GONE
        }
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleSmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimarySmallText, this)
        }


    }

    /**
     * Initialize card tile with radio button and wrapping text
     */
    private fun initCardTileWithRadioButtonWithWrappingText() {
        binding.cardTileSelectRadioButton.apply {
            visibility = VISIBLE
        }
        binding.actionStatusParent.apply {
            visibility = GONE
        }
        binding.cardTilePrimaryInfo.apply {
            setTextAppearance(R.style.Offside_Redesign_TitleSmallTextStyle)
            applyTextColor(R.style.Offside_Redesign_CardTile_Title_PrimarySmallText, this)
        }

    }
    /**
     * Function to apply text color
     *
     * @param styleRes resource style
     * @param textView textView
     */
    private fun applyTextColor(@StyleRes styleRes: Int, textView: MaterialTextView) {
        val attributes = context.obtainStyledAttributes(styleRes, R.styleable.CardTile)
        textView.setTextColor(attributes.getColor(R.styleable.CardTile_tileInfoTextColor, 0))
        attributes.recycle()
    }

    /**
     * Function to set card tile info.
     *
     * @param tileData card tile details [CardTileData].
     * @param checkedListener checkedChangeListener for compound button [CompoundButton] (radio/checkbox).
     * @param tileClickListener click listener to card tile view as well as actionable caret.
     * @param actionButtonClickListener click listener for card action button.
     */
    fun setCardTileInfo(
        tileData: CardTileData,
        checkedListener: (CompoundButton, Boolean) -> Unit = { _: CompoundButton, _: Boolean -> },
        tileClickListener: (View) -> Unit = {},
        actionButtonClickListener: (View) -> Unit = {}
    ) {
        binding.cardTileViewActionImage.setOnClickListener {
            tileClickListener(it)
        }

        binding.cardTileSelectRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            checkedListener(buttonView, isChecked)
        }
        binding.cardTileSelectCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            checkedListener(buttonView, isChecked)
        }

        binding.cardTileActionButton.setOnClickListener {
            actionButtonClickListener(it)
        }

        binding.cardTilePrimaryInfo.apply {
            when {
                tileData.primaryDisplayInfo.isShowMiddleEllipses -> {
                    setTextWithMiddleEllipses(
                        tileData.primaryDisplayInfo.primaryInfo,
                        tileData.primaryDisplayInfo.primarySuffixInfo
                    )
                }

                else -> {
                    "${tileData.primaryDisplayInfo.primaryInfo} ${tileData.primaryDisplayInfo.primarySuffixInfo}".also {
                        text = it
                    }

                }
            }
        }
        binding.cardTileSecondaryInfo.apply {
            text = tileData.secondaryDisplayInfo
        }
        binding.cardTileActionButton.apply {
            text = tileData.cardActionLabel
        }
        binding.cardTileLinkStatus.apply {
            text = tileData.cardStatus
        }
        binding.cardTileArtImage.setImageUrl(tileData.cardArtUrl)
        setAccessibleContent(displayState, tileData, tileClickListener)
    }

    /**
     * Function to get card ending in text.
     *
     * @param inputText input text
     * @return [String] card ending in text with space's for talk back announcement
     */
    private fun getCardEndingInText(inputText: String) = inputText.let {
        val endingTextLength = 4
        val cardEndingInText = if (it.length > endingTextLength) {
            it.substring(it.length - endingTextLength)
        } else {
            it
        }
        // Add space in each character
        cardEndingInText.replace("", " ")
    }

    /**
     * Set accessible content to card-tile.
     *
     * @param displayState state of card-tile [DisplayState]
     * @param data card tile details [CardTileData].
     * @param tileClickListener click listener to card tile view as well as actionable caret.
     */
    private fun setAccessibleContent(
        displayState: DisplayState,
        data: CardTileData,
        tileClickListener: (View) -> Unit = {}
    ) {
        val cardEndingInText = String.format(
            context.getString(R.string.card_ending_in_des),
            getCardEndingInText(data.primaryDisplayInfo.primarySuffixInfo)
        )
        val twoLineAccessibleText = String.format(
            context.getString(R.string.card_tile_select_content_description_three_str),
            data.primaryDisplayInfo.primaryInfo,
            cardEndingInText,
            data.secondaryDisplayInfo,
        )
        when (displayState) {
            DisplayState.SINGLE_LINE_CARD_TILE -> {
                binding.cardTileItemRoot.setOnClickListener {
                    tileClickListener(it)
                }
                setRootViewAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    context.getString(R.string.card_tile_default_action_label),
                    data.primaryDisplayInfo.primaryInfo,
                    context.getString(R.string.card_tile_role_desc_button)
                )
            }

            DisplayState.CARD_TILE_WITH_BUTTON -> {
                val accessibleText = String.format(
                    context.getString(R.string.card_tile_select_content_description_two_str),
                    data.primaryDisplayInfo.primaryInfo,
                    cardEndingInText,
                )
                binding.cardTileItemRoot.setOnClickListener {
                    binding.cardTileActionButton.performClick()
                }
                setRootViewAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    data.cardActionLabel,
                    accessibleText,
                    context.getString(R.string.card_tile_role_desc_button)
                )
            }

            DisplayState.CARD_TILE_WITH_STATUS -> {
                val accessibleText = String.format(
                    context.getString(R.string.card_tile_select_content_description_three_str),
                    data.primaryDisplayInfo.primaryInfo,
                    cardEndingInText,
                    data.cardStatus
                )
                binding.cardTileItemRoot.setOnClickListener {
                    tileClickListener(it)
                }
                setRootViewAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    context.getString(R.string.card_tile_default_action_label),
                    accessibleText,
                    context.getString(R.string.card_tile_role_desc_button)
                )
            }

            DisplayState.TWO_LINE_CARD_TILE-> {
                binding.cardTileItemRoot.setOnClickListener {
                    tileClickListener(it)
                }
                setRootViewAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    context.getString(R.string.card_tile_default_action_label),
                    twoLineAccessibleText,
                    context.getString(R.string.card_tile_role_desc_button)
                )
            }

            DisplayState.CARD_TILE_WITH_CHECKBOX, DisplayState.CARD_TILE_WITH_CHECKBOX_WRAPPING_TEXT -> {

                binding.cardTileItemRoot.setOnClickListener {
                    binding.cardTileSelectCheckBox.toggle()
                }
                binding.cardTileItemRoot.rippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
                setRootViewWithCheckBoxAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    twoLineAccessibleText,
                    binding.cardTileSelectCheckBox
                )
            }

            DisplayState.CARD_TILE_WITH_RADIO_BUTTON, DisplayState.CARD_TILE_WITH_RADIO_BUTTON_WRAPPING_TEXT -> {
                binding.cardTileItemRoot.setOnClickListener {
                    binding.cardTileSelectRadioButton.toggle()
                }
                binding.cardTileItemRoot.rippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
                setRootViewWithRadioButtonAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    twoLineAccessibleText,
                    binding.cardTileSelectRadioButton
                )
            }

            DisplayState.CARD_TILE_REVIEW -> {
                setRootViewAccessibilityDelegate(
                    binding.cardTileItemRoot,
                    "",
                    data.primaryDisplayInfo.primaryInfo,
                    "",
                    true
                )
            }

            else -> {}
        }
    }

    /**
     * Set accessibility delegate to root view of cart tile
     *
     * @param rootView root view [MaterialCardView]
     * @param customActionLabel custom action label to set root view.
     * @param contentDescription content description
     * @param roleDes role to set root view
     * @param isMuteRoleDescription flag to mute role & action on root view
     */
    private fun setRootViewAccessibilityDelegate(
        rootView: MaterialCardView,
        customActionLabel: String,
        contentDescription: String,
        roleDes: String,
        isMuteRoleDescription: Boolean = false
    ) {
        ViewCompat.setAccessibilityDelegate(rootView, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)

                info.apply {
                    stateDescription = contentDescription
                    roleDescription = roleDes
                    when {
                        isMuteRoleDescription -> {
                            removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK)
                            isClickable = false
                        }

                        else -> {
                            val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                AccessibilityNodeInfoCompat.ACTION_CLICK,
                                customActionLabel
                            )
                            addAction(customAction)
                        }
                    }
                }
            }
        })
    }

    /**
     * Set accessibility delegate to root view consisting of check-box.
     *
     * @param rootView root view [MaterialCardView]
     * @param accessibleText content description
     * @param checkBoxA11y check-box [CheckBoxA11y]
     */
    private fun setRootViewWithCheckBoxAccessibilityDelegate(
        rootView: MaterialCardView,
        accessibleText: String,
        checkBoxA11y: CheckBoxA11y
    ) {
        ViewCompat.setAccessibilityDelegate(rootView,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)

                    val selectedState =
                        if (checkBoxA11y.isChecked)
                            context.getString(R.string.card_tile_checkbox_state_checked)
                        else
                            context.getString(R.string.card_tile_checkbox_state_not_checked)

                    val contentDescription = String.format(
                        context.getString(R.string.card_tile_select_content_description_two_str),
                        selectedState,
                        accessibleText
                    )

                    val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        AccessibilityNodeInfoCompat.ACTION_CLICK,
                        context.getString(R.string.card_tile_checkbox_custom_action_desc)
                    )

                    info.apply {
                        stateDescription = contentDescription
                        roleDescription = context.getString(R.string.card_tile_role_desc_checkbox)
                        addAction(customAction)
                    }
                }
            })
    }

    /**
     * Set accessibility delegate to root view consisting of radio-button.
     *
     * @param rootView root view [MaterialCardView]
     * @param accessibleText content description
     * @param radioButtonA11y radio-button [RadioButtonA11y]
     */
    private fun setRootViewWithRadioButtonAccessibilityDelegate(
        rootView: MaterialCardView,
        accessibleText: String,
        radioButtonA11y: RadioButtonA11y
    ) {
        ViewCompat.setAccessibilityDelegate(rootView,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)

                    val selectedState =
                        if (radioButtonA11y.isChecked)
                            context.getString(R.string.card_tile_radio_button_state_selected)
                        else
                            context.getString(R.string.card_tile_radio_button_state_not_selected)

                    val contentDescription = String.format(
                        context.getString(R.string.card_tile_select_content_description_two_str),
                        accessibleText,
                        selectedState
                    )

                    val actionLabel =
                        if (radioButtonA11y.isChecked)
                            context.getString(R.string.card_tile_radio_button_custom_action_desc_activate)
                        else
                            context.getString(R.string.card_tile_radio_button_custom_action_desc_select)

                    val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            AccessibilityNodeInfoCompat.ACTION_CLICK, actionLabel
                        )

                    info.apply {
                        stateDescription = contentDescription
                        roleDescription = context.getString(R.string.card_tile_role_desc_radio_button)
                        addAction(customAction)
                    }
                }
            })
    }
}