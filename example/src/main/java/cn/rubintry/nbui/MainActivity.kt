package cn.rubintry.nbui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
class MainActivity : ComponentActivity(), View.OnClickListener {

    private var tvElasticView: TextView ?= null
    private var tvFloatView: TextView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvElasticView = findViewById(R.id.tvElasticView)
        tvFloatView = findViewById(R.id.tvFloatView)
        tvElasticView?.setOnClickListener(this)
        tvFloatView?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvElasticView -> {
                startActivity(Intent(this , ElasticViewActivity::class.java))
            }
            R.id.tvFloatView -> {
                startActivity(Intent(this , FloatViewActivity::class.java))
            }
        }
    }
}
