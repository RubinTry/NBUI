
## Example code
xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<cn.rubintry.nbui.pull.NBElasticView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/elasticView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <androidx.core.widget.NestedScrollView
        android:id="@+id/nslScrollView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fillViewport="true">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <ImageView
                android:id="@+id/imgHeader"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:adjustViewBounds="true"
                android:src="@mipmap/a_logo" />

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rvTest"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
        </LinearLayout>
    </androidx.core.widget.NestedScrollView>
</cn.rubintry.nbui.pull.NBElasticView>
```
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