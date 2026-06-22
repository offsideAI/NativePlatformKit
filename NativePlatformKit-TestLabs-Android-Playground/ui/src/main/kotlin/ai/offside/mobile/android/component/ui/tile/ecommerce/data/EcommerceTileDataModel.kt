package ai.offside.mobile.android.component.ui.tile.ecommerce.data


import android.view.View
import androidx.annotation.IntRange

/**
 * Data Model class for [EcommerceTileLayout]
 *
 * @param sendButton an object for "Send", Boolean for visibility, and OnClickAction
 * @param declineButton an object for "Decline", Boolean for visibility, and OnClickAction
 * @param cancelRequestButton an object for "Cancel Request", Boolean for visibility, and OnClickAction
 * @param partOfGroupRequest an object for "Part of Group Request", Boolean for visibility, and OnClickAction
 * @param memo an object for "Memo Text", Boolean for visibility
 * @param daysRemaining an object for "Days Remaining Text", Boolean for visibility
 * @param userName a String for "User Name"
 * @param paymentAmount a String for "Payment Amount"
 * @param paymentDate a String for "Payment Date"
 * @param paymentStatus a String for "Payment Status"
 * @param userInitials a String for "Ecommerce Badge Initials"
 * @param ecommerceBadge an Enum class for "Ecommerce Badge Icon"
 */
data class EcommerceTileDataModel(
    override val sendButton: EcommerceTileButton? = null,
    override val declineButton: EcommerceTileButton? = null,
    override val cancelRequestButton: EcommerceTileButton? = null,
    override val partOfGroupRequest: EcommerceTileButton? = null,
    override val memo: EcommerceTileComponents? = null,
    override val daysRemaining : EcommerceTileComponents? = null,
    override val userName: String = "",
    override val paymentAmount: String = "",
    override val paymentDate: String = "",
    override val paymentStatus: String = "",
    override val userInitials: String = "",
    override val ecommerceBadge: BadgeDisplayStatus = BadgeDisplayStatus.SMALL_UNREGISTERED_PERSON_ICON,
) : EcommerceTileData {

    /**Exposes [EcommerceTileData.sendButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val sendButtonVisibility: Int
        get() = when {
            sendButton?.visibility == true -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [EcommerceTileData.declineButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val declineButtonVisibility: Int
        get() = when {
            declineButton?.visibility == true -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [EcommerceTileData.cancelRequestButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val cancelRequestButtonVisibility: Int
        get() = when {
            cancelRequestButton?.visibility == true -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [EcommerceTileData.daysRemaining] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val daysRemainingVisibility: Int
        get() = when {
            daysRemaining?.visibility == true -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [EcommerceTileData.memo] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val memoVisibility: Int
        get() = when {
            memo?.visibility == true -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [EcommerceTileData.partOfGroupRequest] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val groupRequestVisibility: Int
        get() = when {
            partOfGroupRequest?.visibility == true -> View.VISIBLE
            else -> View.GONE
        }
}