package ai.offside.mobile.android.helper.testlabs.nav

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.component.ui.config.AppTheme
import ai.offside.mobile.android.component.ui.config.UIMode
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.ActivityDebugRedesignMainBinding
import ai.offside.mobile.android.helper.testlabs.nav.util.EdgeToEdgeHelper
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class TestlabsMainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding:ActivityDebugRedesignMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(AppTheme.readFromStorage().styleName)
        UIMode.readFromStorage().applyUiMode()
        super.onCreate(savedInstanceState)
        binding = ActivityDebugRedesignMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        EdgeToEdgeHelper.assistActivity(this)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                navController.graph.startDestinationId,
                ai.offside.mobile.android.component.ui.R.id.component_ui_web_view_nav_graph_destination
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                ai.offside.mobile.android.component.ui.R.id.component_ui_web_view_nav_graph_destination -> binding.toolbar.apply {
                    isTitleCentered = true
                    menu.add(
                        Menu.NONE,
                        ai.offside.mobile.android.component.ui.R.id.web_view_menu_exit,
                        Menu.NONE,
                        ai.offside.mobile.android.component.ui.R.string.web_view_menu_exit
                    ).apply {
                        icon = ResourcesCompat.getDrawable(
                            resources,
                            ai.offside.mobile.android.component.ui.R.drawable.ic_actionable_header_close,
                            theme
                        )
                        setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_IF_ROOM
                        )
                    }

                }
                else -> binding.toolbar.apply {
                    isTitleCentered = false
                    menu.removeItem(ai.offside.mobile.android.component.ui.R.id.web_view_menu_exit)
                }
            }
        }
    }

    /**
     * Icon Tint is supported from API26, So the icon tint updated here
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        menu?.findItem(R.id.component_ui_settings_nav_graph_id)?.let {
            val drawable = it.icon
            drawable?.mutate()
            drawable?.setColorFilter(
                MaterialColors.getColor(
                    this,
                    ai.offside.mobile.android.component.ui.R.attr.offside_surfaceContainer,
                    Color.WHITE
                ),
                PorterDuff.Mode.SRC_ATOP
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            ai.offside.mobile.android.component.ui.R.id.web_view_menu_exit -> true.also { _ -> navController.popBackStack() }
            else -> NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
        }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * @return toolbar
     */
    fun getToolBar() = binding.toolbar
}