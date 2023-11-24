package cn.rubintry.nbui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RvTestAdapter(private var list: MutableList<String>) : Adapter<RvTestAdapter.RvVH>() {



    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: MutableList<String>){
        this.list = newList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_test , parent , false)
        return RvVH(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RvVH, position: Int) {
        holder.tvTxt?.text = list[position]
        holder.mItemView.setOnClickListener {

        }
    }

    class RvVH(val mItemView: View) : ViewHolder(mItemView) {
        var tvTxt: TextView ?= null
        init {
            tvTxt = itemView.findViewById<TextView>(R.id.item_txt)
        }
    }
}