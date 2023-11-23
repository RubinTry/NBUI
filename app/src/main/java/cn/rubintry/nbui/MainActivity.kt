package cn.rubintry.nbui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.rubintry.nbui.pull.NBElasticView
import cn.rubintry.nbui.pull.OnReadyPullListener
import cn.rubintry.nbui.ui.theme.NBUITheme

class MainActivity : ComponentActivity() {
    private val adapter = RvTestAdapter(mutableListOf())
    private var rvTest: RecyclerView ?= null
    private var imgHeader: ImageView ?= null
    private var elasticView: NBElasticView ?= null
    private var nslScrollView: NestedScrollView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nslScrollView = findViewById(R.id.nslScrollView)
        elasticView = findViewById(R.id.elasticView)
        imgHeader = findViewById(R.id.imgHeader)
        rvTest = findViewById(R.id.rvTest)

        Log.d("NBElasticView", "id: ${nslScrollView?.id}")

        elasticView?.setHeader(imgHeader)
        elasticView?.setOnReadyPullListener(object : OnReadyPullListener{
            override fun isReady(): Boolean {
                return nslScrollView?.scrollY == 0
            }
        })

        rvTest?.isNestedScrollingEnabled = false
        rvTest?.layoutManager = LinearLayoutManager(this)
        rvTest?.adapter = adapter
        val dataList = mutableListOf<String>()
        for ( i in 0 until 100){
            dataList.add(i.toString());
        }
        adapter.setData(dataList);
    }
}
