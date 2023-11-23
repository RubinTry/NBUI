# NBUI
NBUI，顾名思义，牛bi的UI，这里面包含一些不那么容易在其他UI库中找到的UI组件

## 特点
* 支持 Androidx
* 简洁、美观、易扩展
* 支持下拉放大(NBElasticView)、惯性回弹
* 支持仿抖音“我的”页面下拉放大效果、越界回弹

## 代码样例
kt
```kotlin
    //设置需要被缩放的头部view
    elasticView?.setHeader(imgHeader)
    //设置最大下拉高度(像素)
    elasticView?.maxPullHeight = 1080
    //给一个触发下拉缩放的条件
    elasticView?.setOnReadyPullListener(object : OnReadyPullListener{
            override fun isReady(): Boolean {
                return nslScrollView?.scrollY == 0
            }
        })
```
java
```kotlin
    //设置需要被缩放的头部view
    elasticView.setHeader(imgHeader)
    //设置最大下拉高度(像素)
    elasticView.setMaxPullHeight(1080)
    //给一个触发下拉缩放的条件
    elasticView.setOnReadyPullListener(new OnReadyPullListener() {
            @Override
            public boolean isReady() {
                return nslScrollView.getScrollY() == 0;
            }
        });
```