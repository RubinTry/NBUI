package cn.rubintry.nbui.pull

internal object ElasticPullFactory {

    /**
     * 弹性阻力系数
     */
    @JvmStatic
    fun create(elasticCoefficient: Double): ElasticPull{
        return ElasticPull(elasticCoefficient)
    }
}