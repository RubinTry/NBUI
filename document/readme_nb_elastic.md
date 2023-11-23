
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