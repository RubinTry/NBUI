# NBUI
[中文文档](./README_CN.md)

NBUI，As the name suggests, This is a fantastic UI view library , it contains some UI components that are not easily found in other UI libraries


## Introduction
* Support Androidx
* Concise and beautiful and easy to expand
* Support pull down zoom in(NBElasticView)、Inertial rebound
* Support simulated Tiktok of chinese's "Mine" pull-down zoom effect and cross boundary rebound
* Support floating ball drag effect(typical requirement: [Entrance suspension effect similar to Dokit toolkit](https://github.com/didi/DoKit))

Global config<br>
Application

```java
//UI global config ,priority is lower than directly setting view
    NBUI.getInstance()
            //set the config of NBFloatView
            .config(NBFloatViewConfig.class)
            .setWidth(150)
            .setHeight(150)
            .setPosition(new Point(100 , 100))
            .endConfig()
            //end setting
            //set the config of NBElasticView
            .config(NBElasticPullConfig.class)
            .setElasticCoefficient(0.9f)
            .endConfig();//end setting

```

## View List（When you can click it , it's usable）
* pull
  - [NBElasticView](./document/readme_nb_elastic.md)
* drag
  - [NBFloatView](./document/readme_nb_float.md)  
* normalView
  - NBImageView
  - NBProgressBar

