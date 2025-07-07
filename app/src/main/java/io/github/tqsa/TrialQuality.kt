package io.github.tqsa

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.tqsa.Utils.HookMethod

class Hook : IXposedHookLoadPackage {

    val targetPackage = listOf("tv.danmaku.bili", "com.bilibili.app.in", "tv.danmaku.bilibilihd", "com.bilibili.app.blue")

    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam) {
        if (loadPackageParam.packageName in targetPackage) {
            // Iterate through all classes in the package
            XposedHelpers.findClassesFromPackage(
                loadPackageParam.classLoader,
                loadPackageParam.packageName
            ).forEach { clazz ->
                // Hook all declared methods in each class
                clazz.declaredMethods.forEach { method ->
                    try {
                        val methodName = "${clazz.name}.${method.name}"
                        // Hook each method to check for the target strings
                        XposedBridge.hookMethod(method, object : XC_MethodHook() {
                            override fun beforeHookedMethod(param: MethodHookParam) {
                                // Check if the method contains both strings (simplified check)
                                if (HookMethod.containsStrings(
                                        method,
                                        listOf("PlayerSettingHelper", "get free data failed")
                                    )) {
                                    HookMethod.Logger("[Success] Found matching method: $methodName")
                                }
                            }
                        })
                    } catch (e: Exception) {
                        HookMethod.Logger("[Error] Failed to hook method ${method.name} in ${clazz.name}: ${e.message}")
                    }
                }
            }
            HookMethod.Logger("[Success] Hooked ${loadPackageParam.packageName} !")
        } else {
            HookMethod.Logger("[Error] Why you want to hook ${loadPackageParam.packageName} ?")
        }
    }
}