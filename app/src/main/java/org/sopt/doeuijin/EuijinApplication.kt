package org.sopt.doeuijin

import android.app.Application
import org.sopt.doeuijin.container.SharedPreferenceContainer
import org.sopt.doeuijin.container.SharedPreferenceContainer.sharedPreferencesInstance
import org.sopt.doeuijin.data.auth.api.ApiFactory

class EuijinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        euijinApplicationInstance = this
        sharedPreferencesInstance = SharedPreferenceContainer.getAppPreferences(this)
        ApiFactory.retrofit = ApiFactory.getRetrofit(BuildConfig.AUTH_BASE_URL)
    }

    companion object {
        lateinit var euijinApplicationInstance: EuijinApplication
    }
}