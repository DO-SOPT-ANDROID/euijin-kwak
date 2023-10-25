package org.sopt.doeuijin

import android.app.Application
import org.sopt.doeuijin.container.SharedPreferenceContainer
import org.sopt.doeuijin.container.SharedPreferenceContainer.sharedPreferencesInstance

class EuijinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        euijinApplicationInstance = this
        sharedPreferencesInstance = SharedPreferenceContainer.getAppPreferences(this)
    }

    companion object {
        lateinit var euijinApplicationInstance: EuijinApplication
    }
}