package ai.offside.mobile.android.component.security.ui

import android.webkit.WebViewClient

/**
 * Stub for the monorepo `component.security` SecureWebViewClient (which layered cert pinning /
 * URL allow-listing on top of [WebViewClient]). The testlabs only needs a plain client; this
 * preserves the type and its open-ness so `object : SecureWebViewClient() { ... }` still compiles.
 */
open class SecureWebViewClient : WebViewClient()
