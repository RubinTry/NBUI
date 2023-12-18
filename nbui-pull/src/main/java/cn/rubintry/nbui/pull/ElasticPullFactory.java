package cn.rubintry.nbui.pull;

public class ElasticPullFactory {
    /**
     * 弹性阻力系数
     */
    public static ElasticPull create(Double elasticCoefficient) {
        return new ElasticPull(elasticCoefficient);
    }
}
