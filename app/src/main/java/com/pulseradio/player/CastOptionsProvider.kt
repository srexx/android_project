package com.pulseradio.player

import android.content.Context
import com.google.android.gms.cast.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider

/**
 * Proveedor de opciones para Google Cast SDK.
 * Requerido para inicializar CastContext en el AndroidManifest.
 */
class CastOptionsProvider : OptionsProvider {

    override fun getCastOptions(context: Context): CastOptions {
        return CastOptions.Builder()
            .setReceiverApplicationId(ChromecastManager.CAST_APP_ID)
            .build()
    }

    override fun getAdditionalSessionProviders(context: Context): List<SessionProvider>? {
        return null
    }
}
