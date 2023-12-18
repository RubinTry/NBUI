package cn.rubintry.nbui.pull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * recyclerView的适配器，用来实现列表item
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {

    private List<String> dataList;

    public RvAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cn.rubintry.nbui.pull.R.layout.item_nb_rv , parent , false);
        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        if(null == dataList){
            return;
        }
        String item = dataList.get(position);
        holder.tvNbItem.setText(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RvViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNbItem;
        public RvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNbItem = itemView.findViewById(R.id.tvNbItem);
        }

        public TextView getTvNbItem() {
            return tvNbItem;
        }
    }
}
