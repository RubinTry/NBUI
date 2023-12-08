package cn.rubintry.nbui.pull.config

import cn.rubintry.nbui.core.INBUIConfig
import cn.rubintry.nbui.core.NBUI
import cn.rubintry.nbui.pull.NBElasticView


/**
 * [cn.rubintry.nbui.pull.NBElasticView]的全局配置，优先级低于[cn.rubintry.nbui.pull.NBElasticView]中配置
 */
class NBElasticPullConfig : INBUIConfig {

    private var elasticCoefficient: Double = NBElasticView.DEFAULT_ELASTIC_COEFFCIENT

    override fun init(): NBUI {
        return NBUI.getInstance()
    }


    /**
     * 设置下拉放大阻力系数，取值范围[0,1]，默认值[0.95]，数值越大，越有弹性
     */
    fun setElasticCoefficient(elasticCoefficient: Double): NBElasticPullConfig {
        this.elasticCoefficient = elasticCoefficient
        return this
    }

    fun getElasticCoefficient(): Double {
        return elasticCoefficient
    }
}