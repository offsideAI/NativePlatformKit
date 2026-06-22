package ai.offside.mobile.android.component.ui.tile.transfer

import android.content.Context
import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters.getCurrencyText

class TransferActivityTileDataModel(
    val transferActivityTileData: TransferActivityTileData
) : TransferActivityTileData by transferActivityTileData {

    /** Tile status visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val statusVisibility: Int
        get() = when {
            transferActivityTileData.status.isNullOrEmpty() -> View.GONE
            else -> View.VISIBLE
        }

    /** Tile date visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val dateVisibility: Int
        get() = when {
            transferActivityTileData.date.isNullOrEmpty() -> View.GONE
            else -> View.VISIBLE
        }

    /** Tile recurring icon visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val recurringIconVisibility: Int
        get() = when {
            transferActivityTileData.isRecurring -> View.VISIBLE
            else -> View.GONE
        }

    /** Tile Divider visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val dividerVisibility: Int
        get() = when {
            transferActivityTileData.showDivider -> View.VISIBLE
            else -> View.GONE
        }

    /** Space visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val spaceVisibility: Int
        get() = when {
            transferActivityTileData.singleLineTransaction -> View.VISIBLE
            else -> View.GONE
        }

    /**
     * Get Tile content description
     *
     * @param context to retrieve strings
     */
    fun getTileContentDescription(context: Context): String {
        val recurringText = if (transferActivityTileData.isRecurring) {
            context.getString(R.string.a11y_informative_icon_recurring)
        } else {
            context.getString(R.string.a11y_empty_string)
        }
        return context.getString(
            R.string.a11y_transfer_activity_text,
            transferActivityTileData.toAccount,
            transferActivityTileData.fromAccount,
            recurringText,
            getCurrencyText(transferActivityTileData.amount),
            transferActivityTileData.date,
            transferActivityTileData.status
        )
    }
}