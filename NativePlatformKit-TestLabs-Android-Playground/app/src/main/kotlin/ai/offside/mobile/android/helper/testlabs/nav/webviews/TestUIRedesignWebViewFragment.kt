package ai.offside.mobile.android.helper.testlabs.nav.webviews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ai.offside.mobile.android.component.security.ui.SecureWebViewClient
import ai.offside.mobile.android.component.ui.databinding.WebViewFragmentLayoutBinding
import ai.offside.mobile.android.component.ui.webview.WebViewFragmentComponent
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignWebviewBinding
import ai.offside.mobile.android.helper.testlabs.nav.webviews.TestUIRedesignWebViewsFragment.Companion.JS_INTERFACE_NAME
import org.json.JSONObject


class TestUIRedesignWebViewFragment : Fragment(R.layout.fragment_test_ui_redesign_webview) {

    private var eventHandlers: MutableMap<String, (Any?) -> Unit> = mutableMapOf()

    private var _binding: FragmentTestUiRedesignWebviewBinding? = null
    private val binding: FragmentTestUiRedesignWebviewBinding
        get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentTestUiRedesignWebviewBinding.inflate(
            inflater,
            container,
            false
        ).also { _binding = it }.root

    @SuppressLint("SetJavaScriptEnabled")
    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val url = requireArguments().getString("webViewUrl")

        setDefaultHandlers()

        binding.fragmentWebView.apply {
            webViewClient = object : SecureWebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean = true.also {
                    val redirectedUrl = request.url.toString()
                    if (redirectedUrl.startsWith("tel:")) {
                        view.context.startActivity(
                            Intent(
                                Intent.ACTION_DIAL, Uri.parse(redirectedUrl)
                            )
                        )
                    } else {
                        val intent = Intent(Intent.ACTION_VIEW, request.url)
                        view.context.startActivity(intent)
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                }
            }
            settings.apply {
                javaScriptEnabled = true
                blockNetworkLoads = false
                allowContentAccess = true
                domStorageEnabled = true
            }

            addJavascriptInterface(TestUIRedesignWebViewFragmentInterface(), JS_INTERFACE_NAME)

            CookieManager.getInstance().apply {
                setAcceptCookie(true)
                setCookie(url, requireArguments().getString("ssoCookie"))
                setAcceptThirdPartyCookies(binding.fragmentWebView, true)
            }
            webViewConfiguration(this)

        }.loadUrl(
            url ?: getString(ai.offside.mobile.android.component.ui.R.string.default_web_view_url)
        )

        afterOnViewCreated(
            savedInstanceState = savedInstanceState
        )
    }

    final override fun onDestroyView() {
        beforeWebViewDestroyed()
        binding.fragmentWebView.destroy()
        beforeBindingRemoved()
        beforeOnDestroyView()
        _binding = null
        super.onDestroyView()
    }

    private fun setDefaultHandlers() {
        val defaultHandlers: Map<TestUIWebViewEvents, (Any?) -> Unit> = mapOf(
            TestUIWebViewEvents.CLOSE to { closeWebView() },
            TestUIWebViewEvents.RECEIVE_DATA to { data -> receiveData(data) }
        )

        defaultHandlers.forEach { (event, handler) ->
            eventHandlers.putIfAbsent(event.eventName, handler)
        }
    }

    private fun closeWebView() {
        navController.popBackStack()
    }

    private fun receiveData(data: Any?) {
        binding.fragmentWebView.post {
            //TODO data can be retrieved here
        }
    }

    private fun sendData(data: Any?) {
        try {
            binding.fragmentWebView.evaluateJavascript(
                "javascript: " +"dataFromNative(\"" + data +
                    "\")",null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Provides an opportunity, in [onViewCreated] to apply further configurations to the [WebView]
     *   after app-wide, standard configurations are applied
     */
    val webViewConfiguration: WebView.() -> Unit = { }

    /**
     * The base [Fragment] leverages the layout-id-accepting constructor, the responsibility
     *   of creating and configuring the associated [WebViewFragmentLayoutBinding] instance
     *   falls to [onViewCreated].  We override this method and forbid instances of [WebViewFragmentComponent]
     *   from further overrides.  In order to provide a consistent API, inheritors are encouraged
     *   to override [afterOnViewCreated] into which the same [Bundle] from [onViewCreated] is
     *   provided.  In this method, invocations on [binding] are safe
     * @param savedInstanceState The same (potentially null) [Bundle] from [onViewCreated]
     */
    private fun afterOnViewCreated(savedInstanceState: Bundle?) {}

    /**
     * This is invoked first in the override to [onDestroyView] and provides direct access to the [android.webkit.WebView]
     *   before the view is removed
     */
    private fun beforeWebViewDestroyed() {}

    /**
     * This is invoked second in the override to [onDestroyView] and provides final access to the [binding] before
     *   it is removed
     * Attempting to invoke [binding] after this method (or [onDestroyView] in general) will result in a crash
     */
    private fun beforeBindingRemoved() {}

    /**
     * This is invoked third in the override to [onDestroyView] and is called before the super method
     * Attempting to leverage [binding] in this method will cause an exception
     */
    private fun beforeOnDestroyView() {}

    private inner class TestUIRedesignWebViewFragmentInterface {

        private val mobileDataJson: JSONObject
            get() = JSONObject("{\"platform\":\"android\",\"theme\":{\"name\":\"retail\",\"mode\":{\"app\":\"light\",\"device\":\"light\"}},\"data\":{\"routerPath\":\"credit-card-student-status\",\"mdmContractIdentifier\":\"456369212276221205\",\"token\":\"dummy-token\",\"session\":\"dummy-session\"}}")


        private val javaScriptString: String
            get() = "window.initializeWithData($mobileDataJson)"

        /**
         * Method to communicate between WebView and Native functions to receive data and events
         * @param eventName - name of the triggered event
         * @param data - data received from WebView
         */
        @JavascriptInterface
        fun sendAndReceiveData(eventName: String, data: String) {
            binding.fragmentWebView.post {
                eventHandlers[eventName]?.invoke(data)
            }
        }

        @JavascriptInterface
        fun updateJwtToken() {
            requireActivity().runOnUiThread {
                try {
                    binding.fragmentWebView.evaluateJavascript(javaScriptString, null)
                } catch (e: Exception) {
                }
            }
        }

        @JavascriptInterface
        fun closeWebView() {
            requireActivity().runOnUiThread {
                navController.popBackStack()
            }

        }

        @JavascriptInterface
        fun setExitState(data: String) {
        }

    }

}