package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignActionableIconsBinding

class TestUIRedesignActionableIconsFragment: Fragment() {

    private lateinit var binding: FragmentTestUiRedesignActionableIconsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestUiRedesignActionableIconsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonRoleString = getString(R.string.debug_ui_redesign_actionable_icons_button_role)
        binding.bottomSheetIconLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_bottom_sheet_icon_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_bottom_sheet_arrow)
            actionableIconsDecorativeLayout.setOnClickListener {
                Toast.makeText(context, "Bottom sheet arrow clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeLayout.modifyRoleDescription(actionableIconsDecorativeTextView.text.toString(), buttonRoleString)
        }
        binding.caretIconLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_caret_icon_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_caret)
            actionableIconsDecorativeLayout.setOnClickListener {
                Toast.makeText(context, "Caret icon clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeLayout.modifyRoleDescription(actionableIconsDecorativeTextView.text.toString(), buttonRoleString)
        }
        binding.gripLinesIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_grip_lines_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_grip_lines)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Grip line icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.gripLinesIconDecorativeLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_grip_lines_decorative_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_grip_lines)
            actionableIconsDecorativeGrayLayout.setOnClickListener {
                Toast.makeText(context, "Grip line icon decorative clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeGrayLayout.modifyRoleDescription(getString(R.string.debug_ui_redesign_grip_lines_content_description), buttonRoleString)
        }
        binding.dotsMoreIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_dots_more_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_dots_more)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_dots_more_content_description, actionableIconsNonDecorativeTextView.text)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Dots more icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.dotsMoreIconDecorativeLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_dots_more_decorative_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_dots_more)
            actionableIconsDecorativeGrayLayout.setOnClickListener {
                Toast.makeText(context, "Dots more icon decorative clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeGrayLayout.modifyRoleDescription(
                getString(R.string.debug_ui_redesign_dots_more_content_description, actionableIconsDecorativeTextView.text), buttonRoleString)
        }
        binding.clearIconIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_clear_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_clear)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_clear_icon_content_description, actionableIconsNonDecorativeTextView.text)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Clear icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.clearIconDecorativeLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_clear_icon_decorative_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_clear)
            actionableIconsDecorativeLayout.setOnClickListener {
                Toast.makeText(context, "Clear Icon decorative clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeLayout.modifyRoleDescription(
                getString(R.string.debug_ui_redesign_clear_icon_content_description, actionableIconsDecorativeTextView.text), buttonRoleString)
        }
        binding.questionIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_question_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_question)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_question_icon_content_description, actionableIconsNonDecorativeTextView.text)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Question icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.calendarIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_calendar_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_calendar)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Calendar icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.calendarIconDecorativeLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_calendar_icon_decorative_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_calendar)
            actionableIconsDecorativeLayout.setOnClickListener {
                Toast.makeText(context, "Calendar Icon decorative clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeLayout.modifyRoleDescription(getString(R.string.debug_ui_redesign_calendar_content_description), buttonRoleString)
        }
        binding.copyPasteIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_copy_paste_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_copy_and_paste)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_copy_paste_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Copy/Paste icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.shareIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_share_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_share)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_share_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Share icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.searchIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_search_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_search)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_search_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Search icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.locatorIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_locator_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_locator)
            actionableIconsNonDecorativeIcon.imageTintList = null
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_locator_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Locator icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.trashIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_trash_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_trash)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_trash_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Trash icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.editIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_edit_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_edit)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_edit_icon_content_description, actionableIconsNonDecorativeTextView.text)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Edit icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.editIconDecorativeLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_edit_icon_decorative_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_edit)
            actionableIconsDecorativeLayout.setOnClickListener {
                Toast.makeText(context, "Edit Icon decorative clicked", Toast.LENGTH_SHORT).show()
            }
            actionableIconsDecorativeLayout.modifyRoleDescription(actionableIconsDecorativeTextView.text.toString(), buttonRoleString)
        }
        binding.editThinIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_edit_thin_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_edit_thin)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_edit_icon_content_description, actionableIconsNonDecorativeTextView.text)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Edit thin icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.closeIconLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_close_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_header_close)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_close_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Close icon clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.backArrowLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_back_arrow_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_header_back_arrow)
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_back_arrow_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                Toast.makeText(context, "Back arrow clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.passwordToggleLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_show_hide_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_show)
            actionableIconsNonDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_show
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_show_icon_content_description, actionableIconsNonDecorativeTextView.text)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                if (actionableIconsNonDecorativeIcon.tag == ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_show) {
                    actionableIconsNonDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_hide
                    actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_hide)
                    actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_hide_icon_content_description, actionableIconsNonDecorativeTextView.text)
                } else {
                    actionableIconsNonDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_show
                    actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_show)
                    actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_show_icon_content_description, actionableIconsNonDecorativeTextView.text)
                }
            }
        }
        binding.pinUnpinLayout.apply {
            actionableIconsNonDecorativeTextView.text = getString(R.string.debug_ui_redesign_pin_unpin_icon_label)
            actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular)
            actionableIconsNonDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular
            actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_pin_icon_content_description)
            actionableIconsNonDecorativeIcon.setOnClickListener {
                if (actionableIconsNonDecorativeIcon.tag == ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular) {
                    actionableIconsNonDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_straight_solid
                    actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_straight_solid)
                    actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_unpin_icon_content_description)
                } else {
                    actionableIconsNonDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular
                    actionableIconsNonDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular)
                    actionableIconsNonDecorativeIcon.contentDescription = getString(R.string.debug_ui_redesign_pin_icon_content_description)
                }
            }
        }
        binding.pinUnpinDecorativeLayout.apply {
            actionableIconsDecorativeTextView.text = getString(R.string.debug_ui_redesign_pin_unpin_decorative_icon_label)
            actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular)
            actionableIconsDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular
            actionableIconsDecorativeLayout.modifyRoleDescription(getString(R.string.debug_ui_redesign_pin_icon_content_description), buttonRoleString)
            actionableIconsDecorativeLayout.setOnClickListener {
                if (actionableIconsDecorativeIcon.tag == ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular) {
                    actionableIconsDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_straight_solid
                    actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_straight_solid)
                    actionableIconsDecorativeLayout.modifyRoleDescription(getString(R.string.debug_ui_redesign_unpin_icon_content_description), buttonRoleString)
                } else {
                    actionableIconsDecorativeIcon.tag = ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular
                    actionableIconsDecorativeIcon.setImageResource(ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_pin_angle_regular)
                    actionableIconsDecorativeLayout.modifyRoleDescription(getString(R.string.debug_ui_redesign_pin_icon_content_description), buttonRoleString)
                }
            }
        }
    }
}