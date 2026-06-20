# Consumer R8/ProGuard rules bundled into the NativePlatformKit AAR.
# These rules are applied automatically in any app that depends on this library.
#
# NativePlatformKit is a pure-Kotlin Jetpack Compose UI library with no reflection,
# no JNI, and no Service/Manifest-discovered entry points, so it requires no special
# keep rules of its own. Compose's own consumer rules (shipped with the Compose
# runtime/ui AARs) handle everything needed for composables.
#
# Add library-specific keep rules below as the component catalog grows — for example,
# if a component is ever driven by reflection or referenced from XML.
