package ai.offside.mobile.android.component.ui.webview

import androidx.fragment.app.Fragment
import ai.offside.mobile.android.component.security.ui.SecureWebViewClient
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.WebViewFragmentLayoutBinding
import ai.offside.mobile.android.lib.glassbox.LibGlassbox
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView

/**
 * For any [androidx.navigation.NavGraph]s which include a [WebView], either sub-class
 *   or leverage this [Fragment] for a simplified integration
 */
open class WebViewFragmentComponent : Fragment(R.layout.web_view_fragment_layout) {
    private var _binding: WebViewFragmentLayoutBinding? = null
    protected val binding: WebViewFragmentLayoutBinding
        get() = _binding!!

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        WebViewFragmentLayoutBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    @SuppressLint("SetJavaScriptEnabled")
    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webViewFragmentWebView.apply {
            webViewClient = SecureWebViewClient()
            settings.apply {
                javaScriptEnabled = true
                blockNetworkLoads = false
                allowContentAccess = true
                domStorageEnabled = true
            }
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            webViewConfiguration(this)
        }.also {
            LibGlassbox.getInstance().trackView(view = it)
        }.loadUrl(
            requireArguments().getString("webViewUrl") ?: getString(R.string.default_web_view_url)
        )
        afterOnViewCreated(
            savedInstanceState = savedInstanceState
        )
    }

    final override fun onDestroyView() {
        beforeWebViewDestroyed()
        binding.webViewFragmentWebView.destroy()
        beforeBindingRemoved()
        _binding = null
        beforeOnDestroyView()
        super.onDestroyView()
    }

    /**
     * Provides an opportunity, in [onViewCreated] to apply further configurations to the [WebView]
     *   after app-wide, standard configurations are applied
     */
    open val webViewConfiguration: WebView.() -> Unit = { }

    /**
     * The base [Fragment] leverages the layout-id-accepting constructor, the responsibility
     *   of creating and configuring the associated [WebViewFragmentLayoutBinding] instance
     *   falls to [onViewCreated].  We override this method and forbid instances of [WebViewFragmentComponent]
     *   from further overrides.  In order to provide a consistent API, inheritors are encouraged
     *   to override [afterOnViewCreated] into which the same [Bundle] from [onViewCreated] is
     *   provided.  In this method, invocations on [binding] are safe
     * @param savedInstanceState The same (potentially null) [Bundle] from [onViewCreated]
     */
    open fun afterOnViewCreated(savedInstanceState: Bundle?) {}

    /**
     * This is invoked first in the override to [onDestroyView] and provides direct access to the [android.webkit.WebView]
     *   before the view is removed
     */
    open fun beforeWebViewDestroyed() {}

    /**
     * This is invoked second in the override to [onDestroyView] and provides final access to the [binding] before
     *   it is removed
     * Attempting to invoke [binding] after this method (or [onDestroyView] in general) will result in a crash
     */
    open fun beforeBindingRemoved() {}

    /**
     * This is invoked third in the override to [onDestroyView] and is called before the super method
     * Attempting to leverage [binding] in this method will cause an exception
     */
    open fun beforeOnDestroyView() {}
}