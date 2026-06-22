package ai.offside.mobile.android.helper.testlabs.nav.modals

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ai.offside.mobile.android.component.ui.R as UIR
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignMainPageBinding
import ai.offside.mobile.android.component.ui.modal.ModalFragment
import ai.offside.mobile.android.component.ui.modal.ModalViewModel
import ai.offside.mobile.android.component.ui.modal.model.CheckboxDataModel
import ai.offside.mobile.android.component.ui.modal.model.ModalActionData
import ai.offside.mobile.android.component.ui.modal.model.ModalButtonState
import ai.offside.mobile.android.component.ui.modal.model.ModalButtonType
import ai.offside.mobile.android.component.ui.modal.model.ModalDataState
import ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignThemeListAdapter

class TestUIRedesignModalFragment : Fragment(R.layout.fragment_test_ui_redesign_main_page) {

    private var _binding: FragmentTestUiRedesignMainPageBinding? = null
    private val binding: FragmentTestUiRedesignMainPageBinding get() = _binding!!

    private val modalViewModal: ModalViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiRedesignMainPageBinding.bind(view)
        binding.listview.adapter = RedesignThemeListAdapter(resources.getStringArray(R.array.debug_ui_redesign_dialog_list)) { position ->
            when(position) {
                0 -> showSimpleModal()
                1 -> showSmallModalSubHeader()
                2 -> showMediumModal()
                3 -> showLargeModal()
                4 -> showLargeModalWithSubHeader()
                5 -> showLargeModalScrollUi()
                6 -> showSmallCheckBoxModal()
                7 -> showLargeCheckBoxModal()
                8 -> showLargeCheckBoxScrollModal()
                9 -> showSingleButtonModal()
                10 -> showTwoButtonModal(resources.getString(UIR.string.secondary_action_label), ModalButtonType.SECONDARY)
                11 -> showTwoButtonModal(resources.getString(UIR.string.tertiary_action_label), ModalButtonType.TERTIARY)
            }
        }
    }

    private fun showSimpleModal() {
        modalViewModal.setModalData(
            ModalDataState.SimpleModal(
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_small).toList(),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showSmallModalSubHeader() {
        modalViewModal.setModalData(
            ModalDataState.SimpleSubHeaderModal(
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                subHeader = resources.getString(R.string.debug_ui_redesign_modal_sub_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_small).toList(),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showMediumModal() {
        modalViewModal.setModalData(
            ModalDataState.MediumModal(
                icon = UIR.drawable.ic_actionable_header_help,
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_medium).toList(),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showLargeModal() {
        modalViewModal.setModalData(
            ModalDataState.LargeModal(
                icon = UIR.drawable.ic_actionable_header_help,
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                supplementary = resources.getString(R.string.debug_ui_redesign_modal_supplementary),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_Large).toList(),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showLargeModalWithSubHeader() {
        modalViewModal.setModalData(
            ModalDataState.LargeSubHeaderModal(
                icon = UIR.drawable.ic_actionable_header_help,
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                subHeader = resources.getString(R.string.debug_ui_redesign_modal_sub_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_Large).toList(),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance(

        ).show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showLargeModalScrollUi() {
        modalViewModal.setModalData(
            ModalDataState.LargeModal(
                icon = UIR.drawable.ic_actionable_header_help,
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                supplementary = resources.getString(R.string.debug_ui_redesign_modal_supplementary),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_scroll).toList(),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showSmallCheckBoxModal() {
        modalViewModal.setModalData(
            ModalDataState.SmallCheckboxModal(
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                subHeader = resources.getString(R.string.debug_ui_redesign_modal_sub_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_small).toList(),
                checkboxData = CheckboxDataModel(
                    checkboxLabel = resources.getString(R.string.debug_ui_redesign_modal_checkbox_label),
                    checkboxDescription = resources.getString(R.string.debug_ui_redesign_modal_checkbox_description)
                ),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showLargeCheckBoxModal() {
        modalViewModal.setModalData(
            ModalDataState.MediumCheckboxModal(
                icon = UIR.drawable.ic_actionable_header_help,
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_Large).toList(),
                checkboxData = CheckboxDataModel(
                    checkboxLabel = resources.getString(R.string.debug_ui_redesign_modal_checkbox_label),
                    checkboxDescription = resources.getString(R.string.debug_ui_redesign_modal_checkbox_description)
                ),
                actionData = getModalButtonData()
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showLargeCheckBoxScrollModal() {
        modalViewModal.setModalData(
            ModalDataState.MediumCheckboxModal(
                icon = UIR.drawable.ic_actionable_header_help,
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_scroll).toList(),
                checkboxData = CheckboxDataModel(
                    checkboxLabel = resources.getString(R.string.debug_ui_redesign_modal_checkbox_label),
                    checkboxDescription = resources.getString(R.string.debug_ui_redesign_modal_checkbox_description)
                ),
                actionData = getModalButtonData()
            )
        )

        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showSingleButtonModal() {
        modalViewModal.setModalData(
            ModalDataState.SimpleModal(
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_small).toList(),
                actionData = ModalButtonState.OneButtonModal(
                    primaryAction = ModalActionData(
                        label = resources.getString(UIR.string.primary_action_label)
                    ) {
                        Toast.makeText(context, "Primary Action Clicked", Toast.LENGTH_SHORT).show()
                    })
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }

    private fun showTwoButtonModal(label: String, buttonType: ModalButtonType) {
        modalViewModal.setModalData(
            ModalDataState.SimpleModal(
                title = resources.getString(R.string.debug_ui_redesign_modal_header_title),
                bodyContent = resources.getStringArray(R.array.debug_ui_redesign_modal_description_small).toList(),
                actionData = ModalButtonState.TwoButtonModal(
                    primaryAction = ModalActionData(
                        label = resources.getString(UIR.string.primary_action_label)
                    ) {
                        Toast.makeText(context, "Primary Action Clicked", Toast.LENGTH_SHORT).show()
                    },
                    secondaryAction = ModalActionData(
                        label = label,
                        buttonType = buttonType
                    ) {
                        Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
                    })
            )
        )
        ModalFragment.getInstance().show(childFragmentManager, ModalFragment::class.java.name)
    }
    private fun getModalButtonData(): ModalButtonState {
        return ModalButtonState.ThreeButtonModal(
            primaryAction = ModalActionData(
                label = resources.getString(UIR.string.primary_action_label)
            ) {
                Toast.makeText(context, "Primary Action Clicked", Toast.LENGTH_SHORT).show()
            },
            secondaryAction = ModalActionData(
                label = resources.getString(UIR.string.secondary_action_label),
                buttonType = ModalButtonType.SECONDARY
            ) {
                Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
            },
            tertiaryAction = ModalActionData(
                label = resources.getString(UIR.string.tertiary_action_label),
                buttonType = ModalButtonType.TERTIARY
            ) {
                Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}