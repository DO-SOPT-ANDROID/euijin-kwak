package org.sopt.doeuijin

import android.app.Application
import org.sopt.common.api.ApiFactory
import org.sopt.common.api.ApiFactory2
import org.sopt.doeuijin.container.SharedPreferenceContainer
import org.sopt.doeuijin.container.SharedPreferenceContainer.sharedPreferencesInstance

class EuijinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        euijinApplicationInstance = this
        sharedPreferencesInstance = SharedPreferenceContainer.getAppPreferences(this)
        ApiFactory.retrofit = ApiFactory.getRetrofit(BuildConfig.AUTH_BASE_URL)
        ApiFactory2.reqresRetrofit = ApiFactory2.getRetrofit(REQRES_BASE_URL)
    }

    companion object {
        const val REQRES_BASE_URL = "https://reqres.in"

        lateinit var euijinApplicationInstance: EuijinApplication
    }
}