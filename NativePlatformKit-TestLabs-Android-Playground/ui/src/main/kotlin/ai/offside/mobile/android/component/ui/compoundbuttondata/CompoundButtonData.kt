package ai.offside.mobile.android.component.ui.compoundbuttondata

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import androidx.annotation.StyleRes
import androidx.core.text.toSpannable
import ai.offside.mobile.android.component.ui.R

/**
 * Data class for compound button list item
 *
 * @param primaryText [String] for Compound button label
 * @param secondaryDescription [String] for Compound button secondary description
 * @param tertiaryDescription [String] for Compound button tertiary description
 * @param enabled [Boolean] for Compound enabled/disabled state
 */
data class CompoundButtonData @JvmOverloads constructor(
    val primaryText: String,
    val secondaryDescription: String = "",
    val tertiaryDescription: String = "",
    val enabled: Boolean = true,
) {
    /**
     * Applies respective styles to [primaryText], [secondaryDescription] and [tertiaryDescription] and returns Spannable
     *
     * @param context
     * @param labelStyleRes Text Appearance style resource for label
     * @param descriptionStyleRes Text Appearance style resource for description
     */
    @JvmOverloads
    fun getSpannableText (
        context: Context,
        @StyleRes labelStyleRes: Int = R.style.Offside_Redesign_ListItem_CompoundButton_PrimaryText,
        @StyleRes descriptionStyleRes: Int = R.style.Offside_Redesign_ListItem_CompoundButton_Description,
    ): Spannable {
        val spannableStringBuilder = SpannableStringBuilder(primaryText)
        spannableStringBuilder.setSpan(
            TextAppearanceSpan(context, labelStyleRes),
            0, primaryText.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        applyDescriptionSpan(context, secondaryDescription, spannableStringBuilder, descriptionStyleRes)
        applyDescriptionSpan(context, tertiaryDescription, spannableStringBuilder, descriptionStyleRes)
        return spannableStringBuilder.toSpannable()
    }

    /**
     * Appends description text to [spannableStringBuilder] and applies description styles
     *
     * @param context [Context]
     * @param description [String]
     * @param spannableStringBuilder [SpannableStringBuilder]
     * @param descriptionStyleRes
     */
    private fun applyDescriptionSpan(
        context: Context,
        description: String,
        spannableStringBuilder: SpannableStringBuilder,
        @StyleRes descriptionStyleRes: Int,
    ) {
        if (description.isNotEmpty()) {
            spannableStringBuilder.append("\n$description")
            val index = spannableStringBuilder.indexOf(description)
            val range = index + description.length
            spannableStringBuilder.setSpan(
                TextAppearanceSpan(
                    context,
                    descriptionStyleRes
                ),
                index,
                range,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
    }
}