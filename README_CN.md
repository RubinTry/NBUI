# NBUI
NBUI，顾名思义，牛bi的UI，这里面包含一些不那么容易在其他UI库中找到的UI组件，主打一个<strong>稀有</strong>

## 特点
* 支持 Androidx
* 简洁、美观、易扩展
* 支持下拉放大(NBElasticView)、惯性回弹
* 支持仿抖音“我的”页面下拉放大效果、越界回弹
* 支持悬浮球拖拽效果（典型需求：[类似于Dokit工具包的入口悬浮效果](https://github.com/didi/DoKit)）

全局配置<br>
Application

```java
//UI全局配置,优先级要低于直接对view设置
    NBUI.getInstance()
            //设置NBFloatView相关配置
            .config(NBFloatViewConfig.class)
            .setWidth(150)
            .setHeight(150)
            .setPosition(new Point(100 , 100))
            .init()
            //结束设置
            //设置NBElasticView相关配置
            .config(NBElasticPullConfig.class)
            .setElasticCoefficient(0.9f)
            .init();//结束设置

```

## 控件列表（能点进文档的都是开发完成的）
* pull
  - [NBElasticView](./document/readme_nb_elastic_cn.md)
* drag
  - [NBFloatView](./document/readme_nb_float_cn.md)  
* normalView
  - NBImageView
  - NBProgressBar


