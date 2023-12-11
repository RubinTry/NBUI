package cn.rubintry.nbui.core



/**
 * Created by RubinTry on 2023/12/11.
 * NBUI的config的标准接口
 */
interface INBUIConfig {

    /**
     * 结束掉当前配置项设置
     */
    fun endConfig() : NBUI
}