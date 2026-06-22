package ai.offside.mobile.android.component.ui.color.generator

import android.content.Context

interface ColorGenerator {
    companion object {
        fun getInstance(context: Context): ColorGenerator =
            ColorGeneratorImpl
                .initWithContext(context = context)
    }


}