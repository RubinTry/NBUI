package cn.rubintry.nbui.pull

object ElasticPullFactory {

    /**
     * 弹性阻力系数
     */
    @JvmStatic
    fun create(elasticCoefficient: Double): ElasticPull{
        return ElasticPull(elasticCoefficient)
    }
}