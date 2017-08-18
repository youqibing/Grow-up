package com.example.dell.raisingpets.Module.ModuleFood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.NetWork.Result.FoodResult;
import com.example.dell.raisingpets.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by root on 16-12-18.
 */

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Vector<Boolean> vector = new Vector<>();
    private List<FoodResult.FoodList> foodLists;
    private Context context;
    //private int item;
    private ThreadLocal threadLocal = new ThreadLocal();
    private Thread thread  = new Thread();

    public FoodAdapter(Context context,List<FoodResult.FoodList> foodLists){
        this.context = context;
        this.foodLists = foodLists;
        for(FoodResult.FoodList foodList :foodLists){
            vector.add(false);
        }
        vector.set(0,true);
        /*
        vector.set(0,true);
        for (int i=1; i<foodLists.size(); i++){
            vector.set(i,false);
        }
        */

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_food,parent,false);

        return new FoodAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof FoodAdapter.ItemViewHolder){
            ((FoodAdapter.ItemViewHolder) holder).food_title_tx.setText(foodLists.get(position).getName());
            ((FoodAdapter.ItemViewHolder) holder).food_description.setText(foodLists.get(position).getDescribe());
            ((FoodAdapter.ItemViewHolder) holder).food_booled_tx.setText(foodLists.get(position).getHP()+"");
            ((FoodAdapter.ItemViewHolder) holder).food_hp_tx.setText(foodLists.get(position).getHungry_point()+"");
            ((FoodAdapter.ItemViewHolder) holder).food_money.setText(foodLists.get(position).getPrice()+"");
            Glide.with(context)
                    .load(foodLists.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().into(((FoodAdapter.ItemViewHolder) holder).food_pic);

            holder.itemView.setClickable(true);
            holder.itemView.setSelected(true);
            holder.itemView.setTag(position);

            if(vector.get(position)){
                holder.itemView.findViewById(R.id.food_relative).setBackgroundResource(R.mipmap.food_dialog_click);
                holder.itemView.findViewById(R.id.food_pic).setBackgroundResource(R.mipmap.food_pic_click);
                holder.itemView.findViewById(R.id.hp).setBackgroundResource(R.mipmap.hp_click);
                holder.itemView.findViewById(R.id.food_booled).setBackgroundResource(R.mipmap.food_booled_click);
            }else{
                holder.itemView.findViewById(R.id.food_relative).setBackgroundResource(R.mipmap.food_dialog);
                holder.itemView.findViewById(R.id.food_pic).setBackgroundResource(R.mipmap.food_pic);
                holder.itemView.findViewById(R.id.hp).setBackgroundResource(R.mipmap.hp);
                holder.itemView.findViewById(R.id.food_booled).setBackgroundResource(R.mipmap.food_booled);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int)v.getTag();
                    vector.set(pos,true);
                    //vector.set(position,!vector.get(position)); //这个是多选的逻辑

                    for (int i = 0; i <foodLists.size(); i++){
                        if(i != pos){
                            vector.set(i,false);
                        }
                    }
                    notifyDataSetChanged();

                    ToolPerference.storeFoodIdentifier(foodLists.get(position).getIdentifier());
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return foodLists.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView food_pic;
        TextView food_title_tx;
        TextView food_description;
        TextView food_booled_tx;
        TextView food_hp_tx;
        TextView food_money;

        ItemViewHolder(View itemView) {
            super(itemView);

            food_pic = (ImageView)itemView.findViewById(R.id.food_avatar);
            food_title_tx = (TextView)itemView.findViewById(R.id.food_tv_title);
            food_description = (TextView)itemView.findViewById(R.id.food_tv_description);
            food_booled_tx =(TextView)itemView.findViewById(R.id.food_booled_tx);
            food_hp_tx =(TextView)itemView.findViewById(R.id.food_hp_tx);
            food_money = (TextView)itemView.findViewById(R.id.food_money);
        }

    }


}
