package ai.offside.mobile.android.component.ui.tile.ecommerce.data

/**
 * Internal Interface class for [EcommerceTileDataModel]
 */
internal interface EcommerceTileData {
    /** [EcommerceTileButton] for Send button */
    val sendButton: EcommerceTileButton?

    /** [EcommerceTileButton] for Decline button */
    val declineButton: EcommerceTileButton?

    /** [EcommerceTileButton] for Cancel Request button */
    val cancelRequestButton: EcommerceTileButton?

    /** [EcommerceTileButton] for part of group request value */
    val partOfGroupRequest: EcommerceTileButton?

    /** [EcommerceTileComponents] for memo value */
    val memo: EcommerceTileComponents?

    /** [EcommerceTileComponents] for remaining days to complete transaction value */
    val daysRemaining: EcommerceTileComponents?

    /** [String] for User name */
    val userName: String

    /** [String] for amount of transaction */
    val paymentAmount: String

    /** [String] for date and confirmation number */
    val paymentDate: String

    /** [Pair] for status of payment */
    val paymentStatus: String

    /** [String] initials displayed on ecommerce icon */
    val userInitials: String

    /** [BadgeDisplayStatus] badge to display */
    val ecommerceBadge: BadgeDisplayStatus

}

/**
 * Enum class for various Ecommerce badges
 */
enum class BadgeDisplayStatus {
    SMALL_REGISTERED_BUSINESS_ICON,
    SMALL_REGISTERED_PERSON_ICON,
    SMALL_UNREGISTERED_BUSINESS_ICON,
    SMALL_UNREGISTERED_PERSON_ICON,
    SMALL_SELECTED_ICON
}

/**
 * Enum class for various Button type
 */
enum class ButtonType {
    SEND,
    DECLINE,
    CANCEL_REQUEST,
    CANCEL_PAYMENT
}