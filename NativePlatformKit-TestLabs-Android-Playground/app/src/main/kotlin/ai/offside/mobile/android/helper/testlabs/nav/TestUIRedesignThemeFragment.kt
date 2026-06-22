package ai.offside.mobile.android.helper.testlabs.nav

import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.ui.config.AppTheme
import ai.offside.mobile.android.component.ui.config.UIMode
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignThemeBinding
import android.os.Bundle
import android.view.View

/**
 * A simple [Fragment] subclass.
 */
class TestUIRedesignThemeFragment : Fragment(R.layout.fragment_test_ui_redesign_theme) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding: FragmentTestUiRedesignThemeBinding = FragmentTestUiRedesignThemeBinding.bind(view)

        binding.appThemeSwitch.apply {
            text = AppTheme.readFromStorage().name
            isChecked = AppTheme.readFromStorage() == AppTheme.PRIVATE_BANK
            setOnCheckedChangeListener{ _, isChecked ->
                if (isChecked) {
                    AppTheme.PRIVATE_BANK
                } else {
                    AppTheme.RETAIL
                }.writeToStorage()
                requireActivity().recreate()
            }
        }

        binding.darkThemeSwitch.apply {
            text = UIMode.readFromStorage().name
            setOnClickListener { v ->
                PopupMenu(requireActivity(), v).apply {
                    inflate(R.menu.ui_mode_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.testlabs_ui_mode_light -> true.also { _ -> UIMode.LIGHT.writeToStorage() }
                            R.id.testlabs_ui_mode_dark -> true.also { _ -> UIMode.DARK.writeToStorage() }
                            R.id.testlabs_ui_mode_system -> true.also { _ -> UIMode.SYSTEM.writeToStorage() }
                            else -> false
                        }.also { handled ->
                            if (handled) {
                                UIMode.readFromStorage().applyUiMode()
                            }
                        }
                    }
                }.show()
            }
        }
    }
}