package cn.rubintry.nbui.normal

import android.content.Context
import android.util.AttributeSet
import android.view.View
import cn.rubintry.nbui.core.INBUIInterface



/**
 * Created by RubinTry on 2023/11/24.
 * NB的ImageView
 */
class NBImageView : View , INBUIInterface {
    constructor(context: Context?) : super(context){
        preInit(context , null)
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        preInit(context , attrs)
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        preInit(context , attrs)
        init()
    }

    override fun preInit(context: Context?, attrs: AttributeSet?) {

    }

    override fun init() {

    }
}