package ai.offside.mobile.android.component.storage.read

import ai.offside.mobile.android.component.storage.key.AppStorageKeyDefaultValue
import ai.offside.mobile.android.component.storage.key.AppStorageKeyName

sealed class AppStorageReadRequest {
    data class StringRequest(
        val keyName: AppStorageKeyName,
        val defaultValue: AppStorageKeyDefaultValue.StringValue,
    ) : AppStorageReadRequest()
}
