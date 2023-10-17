package org.sopt.doeuijin

import android.app.Application
import org.sopt.doeuijin.container.SharedPreferenceContainer

class EuijinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        euijinApplicationInstance = this
        SharedPreferenceContainer.sharedPreferencesInstance =
            SharedPreferenceContainer.getAppPreferences(
                this,
            )
    }

    companion object {
        lateinit var euijinApplicationInstance: EuijinApplication
    }
}