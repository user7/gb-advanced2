object Dependencies {
    private const val KTX_CORE_VERSION = "1.7.0"
    const val KTX_CORE = "androidx.core:core-ktx:$KTX_CORE_VERSION"

    private const val ROOM_VERSION = "2.4.2"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    const val ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"

    private const val RETROFIT_VERSION = "2.9.0"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"

    // implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    // implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
}