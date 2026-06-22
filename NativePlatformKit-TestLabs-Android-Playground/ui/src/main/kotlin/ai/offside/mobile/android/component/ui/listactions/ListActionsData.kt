package ai.offside.mobile.android.component.ui.listactions

/**
 * Interface for list actions component
 */
interface ListActionsData {
    /** [String] textview action text */
    val actionText: String

    /** [Int] textview left drawable */
    val leftDrawable: Int

    /** [Int] textview right drawable */
    val rightDrawable: Int

    /** Onclick action for text view */
    fun onClick()
}