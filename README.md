# NBUI
[中文文档](./README_CN.md)

NBUI，As the name suggests, This is a fantastic UI component library , it contains some UI components that are not easily found in other UI libraries


## Introduction
* Support Androidx
* Concise and beautiful and easy to expand
* Support pull down zoom in(NBElasticView)、Inertial rebound
* Support simulated Tiktok of chinese's "Mine" pull-down zoom effect and cross boundary rebound


## Example code
kt
```kotlin
    //set the headerView that will be zoom
    elasticView?.setHeader(imgHeader)
    //set max pull length(pixels)
    elasticView?.maxPullHeight = 1080
    //give a conditions for triggering Zoom
    elasticView?.setOnReadyPullListener(object : OnReadyPullListener{
            override fun isReady(): Boolean {
                return nslScrollView?.scrollY == 0
            }
        })
```
java
```kotlin
    //set the headerView that will be zoom
    elasticView.setHeader(imgHeader)
    //set max pull length(pixels)
    elasticView.setMaxPullHeight(1080)
    //give a conditions for triggering Zoom
    elasticView.setOnReadyPullListener(new OnReadyPullListener() {
            @Override
            public boolean isReady() {
                return nslScrollView.getScrollY() == 0;
            }
        });
```