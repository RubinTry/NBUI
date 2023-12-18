package cn.rubintry.nbui.core.config;

import cn.rubintry.nbui.core.INBUIConfig;
import cn.rubintry.nbui.core.NBUI;

public class NBElasticPullConfig implements INBUIConfig {

    public static final Double DEFAULT_ELASTIC_COEFFCIENT  = 0.95;

    private Double elasticCoefficient = DEFAULT_ELASTIC_COEFFCIENT;

    /**
     * 设置下拉放大阻力系数，取值范围[0,1]，默认值[0.95]，数值越大，越有弹性
     */
    public NBElasticPullConfig setElasticCoefficient(double elasticCoefficient)  {
        this.elasticCoefficient = elasticCoefficient;
        return this;
    }

    public Double getElasticCoefficient()  {
        return elasticCoefficient;
    }

    @Override
    public NBUI endConfig() {
        return NBUI.getInstance();
    }
}
