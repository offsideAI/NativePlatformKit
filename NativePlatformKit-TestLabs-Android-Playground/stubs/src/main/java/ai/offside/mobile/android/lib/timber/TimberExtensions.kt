package ai.offside.mobile.android.lib.timber

import android.util.Log
import timber.log.Timber
import java.util.function.Supplier

/**
 * Acts on any [T] to give a SAM-like syntax for logging
 * @receiver Anything
 * @param level The [Int] levels defined in [android.util.Log]
 * @param tag An optional [String] used to override reflection on [T]
 * @param contentSupplier A [Supplier] of a [String], the result of which is the desired log
 */
@JvmOverloads
inline fun <reified T : Any> T.log(
    level: Int = Log.DEBUG,
    tag: String? = null,
    contentSupplier: Supplier<String>
) { Timber.tag(tag ?: T::class.simpleName ?: T::class.java.name).log(level, contentSupplier.get()) }

/**
 * Exposes a more Java-friendly [log] method
 * @param level The [Int] levels defined in [android.util.Log]
 * @param clazz The [Class] of [T] for the [Timber.tag] configuration
 * @param contentSupplier A [Supplier] of a [String], the result of which is the desired log
 */
@JvmOverloads
fun <T : Any> log(
    level: Int = Log.DEBUG,
    clazz: Class<T>,
    contentSupplier: Supplier<String>
) { Timber.tag(clazz.simpleName).log(level, contentSupplier.get()) }

/**
 * Exposes a simple [log] method
 * @param level The [Int] levels defined in [android.util.Log]
 * @param tag A [String] to use as the [Timber.tag].  Values are held to the same restrictions
 * @param contentSupplier A [Supplier] of a [String], the result of which is the desired log
 */
@JvmOverloads
fun log(
    level: Int = Log.DEBUG,
    tag: String = "INLINE_TAG",
    contentSupplier: Supplier<String>,
) { Timber.tag(tag).log(level, contentSupplier.get()) }