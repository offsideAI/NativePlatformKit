package ai.offside.mobile.android.lib.timber

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

/**
 * @author pl36318
 * Utility on top of Android's normal Log class.
 *
 * Usage - Timber.tag("TAG").d/e/i/v/w("message")
 */
open class OffsideLogger : Timber.Tree() {
    /**
     * This will decide if log method below will be called or ignored, return true only for ERROR
     *
     * @param tag
     * @param priority
     * @return
     */
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.ERROR
    }

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See [Log] for constants.
     * @param tag      Explicit or inferred tag. May be `null`.
     * @param message  Formatted log message. May be `null`, but then `t` will not be.
     * @param t        Accompanying exceptions. May be `null`, but then `message` will not be.
     */
    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        var tag = tag
        tag = tag ?: ""
        when (priority) {
            Log.DEBUG -> Log.d(tag, message)
            Log.ERROR -> Log.e(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.VERBOSE -> Log.v(tag, message)
            Log.WARN -> Log.w(tag, message)
        }

        // Plugin to any crash log handling here
        t?.printStackTrace()
    }
}