package ai.offside.mobile.android.component.ui.inputfield

import android.widget.CompoundButton

/**
 * Radio group check listener
 *
 * @param allies variable arguments for all other buttons in the same RadioGroup.
 * @param radioGroupCheckListener listener to pass checked state of radio-button back to parent view.
 */
class RadioGroupCheckListener(
    private vararg val allies: CompoundButton,
    private val radioGroupCheckListener: RadioGroupCustomCheckListener
) : CompoundButton.OnCheckedChangeListener {

    private var radioGroupCustomCheckListener: RadioGroupCustomCheckListener = radioGroupCheckListener

    interface RadioGroupCustomCheckListener {
        /**
         * Listener to pass checked state of radio-button back to parent view.
         *
         * @param buttonView checked state radio button
         * @param isChecked checked status of radio button
         */
        fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean)
    }

    /**
     * Listener for a button to turn other allies (radio button) unchecked when it turn checked.
     *
     * @param buttonView change check occur with this button
     * @param isChecked result of changing
     */
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            for (aly in allies) {
                aly.isChecked = false
            }
            radioGroupCustomCheckListener.onCheckedChanged(buttonView, true)
        }
    }

    companion object {

        /**
         * Function to remove one element from Buttons array.
         *
         * @param buttons all the buttons in RadioGroup
         * @param selectedIndex the index of radio button to be filtered out from array
         * @return an array of CompoundButtons except the selected index
         */
        private fun filterButtonList(
            buttons: List<CompoundButton>,
            selectedIndex: Int
        ): List<CompoundButton> {
            return buttons.filterIndexed { index, _ -> index != selectedIndex }
        }

        /**
         * Function to create a group of radio button to be marked as unchecked when filtered radio button is in checked state.
         * If a button turn to checked state, all other radio buttons is group should be turn to unchecked state.
         * @param buttons the buttons that we want to act like RadioGroup
         */
        @JvmStatic
        fun makeGroup(vararg buttons: CompoundButton,
                      radioGroupCustomCheckListener: RadioGroupCustomCheckListener
        ) {
            buttons.forEachIndexed { index, compoundButton ->
                val filteredButtons = filterButtonList(buttons.asList(), index)
                compoundButton.setOnCheckedChangeListener(
                    RadioGroupCheckListener(*filteredButtons.toTypedArray(),
                    radioGroupCheckListener = radioGroupCustomCheckListener)
                )
            }
        }
    }
}