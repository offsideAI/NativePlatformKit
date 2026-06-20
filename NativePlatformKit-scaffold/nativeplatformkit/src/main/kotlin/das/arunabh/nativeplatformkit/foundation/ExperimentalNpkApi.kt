/*
 * Copyright 2026 The NativePlatformKit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package das.arunabh.nativeplatformkit.foundation

/**
 * Marks a NativePlatformKit declaration as **experimental**.
 *
 * Experimental APIs are exposed for early feedback. They may change incompatibly, or be removed
 * entirely, in any release without going through the usual deprecation cycle described by the
 * project's SemVer policy. Callers must explicitly opt in, either by annotating the call site with
 * `@OptIn(ExperimentalNpkApi::class)` or by propagating `@ExperimentalNpkApi`.
 *
 * Once an API has proven stable it will have this annotation removed in a minor release, at which
 * point it becomes part of the stable, SemVer-governed surface.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This NativePlatformKit API is experimental and may change or be removed without notice.",
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
)
public annotation class ExperimentalNpkApi
