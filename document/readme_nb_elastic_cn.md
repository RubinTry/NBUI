
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