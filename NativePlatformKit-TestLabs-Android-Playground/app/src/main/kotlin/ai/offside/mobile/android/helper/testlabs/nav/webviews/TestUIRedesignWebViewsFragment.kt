package ai.offside.mobile.android.helper.testlabs.nav.webviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignMainPageBinding
import ai.offside.mobile.android.helper.testlabs.nav.adapter.RedesignThemeListAdapter

class TestUIRedesignWebViewsFragment : Fragment(R.layout.fragment_test_ui_redesign_main_page) {

    private var _binding: FragmentTestUiRedesignMainPageBinding? = null
    private val binding: FragmentTestUiRedesignMainPageBinding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestUiRedesignMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.listview.adapter = RedesignThemeListAdapter(resources.getStringArray(R.array.debug_ui_redesign_webviews_list)) { position ->
            when(position) {
                0 -> navController.navigate(
                    resId = R.id.action_testUIRedesignWebViewsFragment_to_testUIRedesignWebViewFragment,
                    args = bundleOf(
                        "webViewUrl" to  WEB_VIEW_URL,
                        "ssoCookie" to SSO_COOKIE
                    )
                )
                1 -> navController.navigate(
                    resId = R.id.action_testUIRedesignWebViewsFragment_to_testUIRedesignWebViewFragment,
                    args = bundleOf(
                        "webViewUrl" to  WEB_VIEW_URL,
                        "ssoCookie" to SSO_COOKIE
                    )
                )
                2 -> navController.navigate(
                    resId = R.id.action_testUIRedesignWebViewsFragment_to_testUIRedesignWebViewFragment,
                    args = bundleOf(
                        "webViewUrl" to  WEB_VIEW_URL,
                        "ssoCookie" to SSO_COOKIE
                    )
                )
                3 -> navController.navigate(
                    resId = R.id.action_testUIRedesignWebViewsFragment_to_testUIRedesignWebViewFragment,
                    args = bundleOf(
                        "webViewUrl" to  WEB_VIEW_URL,
                        "ssoCookie" to SSO_COOKIE
                    )
                )
                4 -> navController.navigate(
                    resId = R.id.action_testUIRedesignWebViewsFragment_to_testUIRedesignWebViewFragment,
                    args = bundleOf(
                        "webViewUrl" to  WEB_VIEW_URL,
                        "ssoCookie" to SSO_COOKIE
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val WEB_VIEW_URL = "https://securemobile-qa.offside.com"
        const val SSO_COOKIE = "__Secure.rtl.sso.session.qa=MGVkYmJmNWQtYjc2Ny00NzVjLThhNzktNjZiMmU0NzA0Y2Zk;domain=.offside.com;path=/;secure"
        const val JS_INTERFACE_NAME = "Android"
    }

}