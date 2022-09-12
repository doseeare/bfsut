package pro.breez.data.utility.app_manager

import android.content.Context

class ApplicationInfoManagerImpl constructor(private val context: Context) :
    ApplicationInfoManager {

    override fun getAppVersion(): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }
}

interface ApplicationInfoManager {
    fun getAppVersion(): String
}