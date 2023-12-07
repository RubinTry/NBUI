package cn.rubintry.nbui

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.rubintry.nbui.pull.NBElasticView
import cn.rubintry.nbui.pull.OnReadyPullListener

class ElasticViewActivity : ComponentActivity() {
    private val adapter = RvTestAdapter(mutableListOf())
    private var rvTest: RecyclerView?= null
    private var imgHeader: ImageView?= null
    private var elasticView: NBElasticView?= null
    private var nslScrollView: NestedScrollView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elastic_view)
        nslScrollView = findViewById(R.id.nslScrollView)
        elasticView = findViewById(R.id.elasticView)
        imgHeader = findViewById(R.id.imgHeader)
        rvTest = findViewById(R.id.rvTest)


        elasticView?.setHeader(imgHeader)
        elasticView?.setOnReadyPullListener(object : OnReadyPullListener {
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