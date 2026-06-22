package ai.offside.mobile.android.helper.testlabs.color.generator

sealed interface ColorGeneratorEvent {
    interface Navigation : ColorGeneratorEvent {
        data object ToResults : Navigation
    }
}