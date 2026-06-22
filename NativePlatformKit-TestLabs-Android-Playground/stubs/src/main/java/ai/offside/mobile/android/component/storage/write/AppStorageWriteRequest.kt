package ai.offside.mobile.android.component.storage.write

import ai.offside.mobile.android.component.storage.key.AppStorageKeyName

sealed class AppStorageWriteRequest {
    data class StringRequest(
        val keyName: AppStorageKeyName,
        val valueToWrite: String,
    ) : AppStorageWriteRequest()
}
