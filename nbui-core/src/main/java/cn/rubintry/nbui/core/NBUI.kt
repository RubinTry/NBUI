package cn.rubintry.nbui.core

import java.util.concurrent.ConcurrentHashMap


/**
 * NBUI全局操作类
 */
class NBUI private constructor(){

//    private val uiConfigList = mutableListOf<INBUIConfig>()

    private val uiGlobalConfigMap = ConcurrentHashMap<Class<out INBUIConfig> , INBUIConfig>()

    companion object{
        @Volatile
        private var instance : NBUI ?= null
        @JvmStatic
        fun getInstance(): NBUI {
            if(null == instance){
                synchronized(NBUI::class.java){
                    if(null == instance){
                        instance = NBUI()
                    }
                }
            }
            return instance!!
        }
    }


    /**
     * 设置UI全局配置，优先级低于直接在view中配置
     */
    fun <T : INBUIConfig> config(clazz: Class<T>) : T{
        if(uiGlobalConfigMap.containsKey(clazz)){
            return uiGlobalConfigMap[clazz] as T
        }
        val obj = clazz.newInstance()
        uiGlobalConfigMap[clazz] = obj
        return obj
    }
}