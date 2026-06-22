package ai.offside.mobile.android.component.ui.tile.online_payments

import android.content.Context
import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.tile.online_payments.OnlinePaymentsTileDataModel.OnlinePaymentsType.*

/**
 * Data Model class for [OnlinePaymentsTile]
 *
 * @param onlinePaymentsTileData
 * @param context
 */
class OnlinePaymentsTileDataModel(
    val context: Context,
    val onlinePaymentsTileData: OnlinePaymentsTileData
) : OnlinePaymentsTileData by onlinePaymentsTileData {

    /**Exposes primary button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val primaryButtonVisibility: Int
        get() = when(onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_COMPLETED, ONLINE_PAYMENTS_SCHEDULED -> View.GONE
            else -> View.VISIBLE
        }

    /**Exposes secondary button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondaryButtonVisibility: Int
        get() = when(onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_COMPLETED, ONLINE_PAYMENTS_SCHEDULED -> View.GONE
            else -> View.VISIBLE
        }

    /**Exposes edit button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val editButtonVisibility: Int
        get() = when(onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_SCHEDULED -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes online_payments type textview visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val onlinePaymentsTypeVisibility: Int
        get() = when(onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_SCHEDULED -> View.GONE
            else -> View.VISIBLE
        }

    /**Exposes from account textview visibility based on online_payments type*/
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val fromAccountViewVisibility: Int
        get() = when(onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_COMPLETED, ONLINE_PAYMENTS_SCHEDULED -> View.VISIBLE
            else -> View.GONE
        }

    /**Get secondary button text for online_payments types*/
    val secondaryButtonText: String
        get() = when(onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_EBILL_LATE, ONLINE_PAYMENTS_EBILL_ON_TIME -> context.getString(R.string.online_payments_Secondary_ebill_button_text)
            ONLINE_PAYMENTS_REMINDER_LATE, ONLINE_PAYMENTS_REMINDER_ON_TIME -> context.getString(R.string.online_payments_Secondary_reminder_button_text)
            ONLINE_PAYMENTS_REQUEST_LATE, ONLINE_PAYMENTS_REQUEST_ON_TIME -> context.getString(R.string.online_payments_Secondary_request_button_text)
            else -> ""
        }

    /**
     * Different online_payments payment types
     * @param type
     */
    enum class OnlinePaymentsType(val type: String, val subType: String) {
        ONLINE_PAYMENTS_COMPLETED("Completed", "Completed"),
        ONLINE_PAYMENTS_SCHEDULED("Scheduled", "Completed"),
        ONLINE_PAYMENTS_EBILL_LATE("Ebill", "Late"),
        ONLINE_PAYMENTS_EBILL_ON_TIME("Ebill", "On-Time"),
        ONLINE_PAYMENTS_REMINDER_LATE("Reminder", "Late"),
        ONLINE_PAYMENTS_REMINDER_ON_TIME("Reminder", "On-Time"),
        ONLINE_PAYMENTS_REQUEST_LATE("Request", "Late"),
        ONLINE_PAYMENTS_REQUEST_ON_TIME("Request", "On-Time");
    }
}
