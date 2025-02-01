package io.github.tqsa

import android.util.Log

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Hook : IXposedHookLoadPackage {

    val targetPackage = listOf("tv.danmaku.bili", "com.bilibili.app.in", "tv.danmaku.bilibilihd", "com.bilibili.app.blue")

    fun hookMethod(className: String, methodName: String, type: Boolean , classLoader: ClassLoader) {
        XposedHelpers.findAndHookMethod(
            className,
            classLoader,
            methodName,
            XC_MethodReplacement.returnConstant(type)
        )
        Logger("[Success] Hooked $className !")
    }

    fun hookSetTrue(className: String, classLoader: ClassLoader) {
        try {
            XposedHelpers.findAndHookMethod(
                className,
                classLoader,
                "setIsNeedTrial",
                Boolean::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        Logger("[Hooked] Forcing setIsNeedTrial(true)")
                        param.args[0] = true
                    }
                }
            )
            Logger("[Success] Hook applied to $className.setIsNeedTrial")
        } catch (e: NoSuchMethodError) {
            Logger("[Error] Method not found: ${e.message}")
        } catch (e: ClassNotFoundException) {
            Logger("[Error] Class not found: ${e.message}")
        } catch (e: Throwable) {
            Logger("[Fatal] Unexpected error: ${e.stackTraceToString()}")
        }
    }

    fun hookVipFree(className: String, classLoader: ClassLoader) {
        XposedHelpers.findAndHookMethod(
            className,
            classLoader,
            "getVipFree",
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val needVip = XposedHelpers.getBooleanField(param.thisObject, "needVip_")
                    param.result = needVip
                }
            }
        )
    }

    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam) {
        if (loadPackageParam.packageName in targetPackage) {
            XposedBridge.hookAllMethods(
                XposedHelpers.findClass("android.app.ActivityThread", loadPackageParam.classLoader),
                "performLaunchActivity",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        hookSetTrue("com.bapis.bilibili.pgc.gateway.player.v2.SceneControl", loadPackageParam.classLoader)
                        hookSetTrue("com.bapis.bilibili.playershared.VideoVod", loadPackageParam.classLoader)
                        hookMethod("com.bapis.bilibili.pgc.gateway.player.v2.SceneControl", "getIsNeedTrial", true , loadPackageParam.classLoader)
                        hookMethod("com.bapis.bilibili.playershared.VideoVod", "getIsNeedTrial", true , loadPackageParam.classLoader)
                        hookVipFree("com.bapis.bilibili.app.playurl.v1.StreamInfo", loadPackageParam.classLoader)
                        hookVipFree("com.bapis.bilibili.playershared.StreamInfo", loadPackageParam.classLoader)
                        hookMethod("com.bapis.bilibili.app.playurl.v1.StreamInfo", "getNeedVip", false , loadPackageParam.classLoader)
                        hookMethod("com.bapis.bilibili.playershared.StreamInfo", "getNeedVip", false , loadPackageParam.classLoader)
                    }
                }
            )
            Logger("[Success] Hooked ${loadPackageParam.packageName} !")
        } else {
            Logger("[Error] Why you want to hook ${loadPackageParam.packageName} ?")
        }
    }

    private fun Logger(msg: String) {
        Log.v("TrialQualityStandalone: ",msg)
    }
}