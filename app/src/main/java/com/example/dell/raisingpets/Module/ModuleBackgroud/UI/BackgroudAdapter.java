package com.example.dell.raisingpets.Module.ModuleBackgroud.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event.ChangeBackgroudJob;
import com.example.dell.raisingpets.NetWork.Result.BackgroudResult;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by dell on 2016/10/23.
 */

public class BackgroudAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BackgroudResult.BackgroudList> backgroudLists;
    private Context context;
    private Dialog dialog;
    private boolean isUser = false;
    private Button is_use_button;

    public BackgroudAdapter(Context context,List<BackgroudResult.BackgroudList> backgroudLists){

        this.context = context;
        this.backgroudLists = backgroudLists;
        dialog = IphoneStyleDialog.BackgroudDialog(context);
        is_use_button = (Button)dialog.findViewById(R.id.is_use_button);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_characters,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).title_tx.setText(backgroudLists.get(position).getName());
            ((ItemViewHolder) holder).description.setText(backgroudLists.get(position).getDescribe());
            Glide.with(context)
                    .load(backgroudLists.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().into(((ItemViewHolder) holder).background_img);
            //((ItemViewHolder) holder).isLocked_img.setImageResource(R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String identifier = backgroudLists.get(position).getIdentifier();

                    switch (identifier) {
                        case "city":
                            ((ImageView) dialog.findViewById(R.id.content_img)).setBackgroundResource(R.mipmap.city_cut);
                            break;
                        case "forest":
                            ((ImageView) dialog.findViewById(R.id.content_img)).setBackgroundResource(R.mipmap.froest_cut);
                            break;
                    }

                    ((TextView) dialog.findViewById(R.id.content_title)).setText(backgroudLists.get(position).getName());
                    ((TextView) dialog.findViewById(R.id.content_tx)).setText(backgroudLists.get(position).getDescribe());

                    if(UserPreference.getBackground().equals(identifier)){
                        is_use_button.setText("使用中");
                        isUser = false;
                        is_use_button.setEnabled(false);
                        is_use_button.setBackgroundResource(R.drawable.corners_button_login_locked);
                    }else {
                        switch (identifier) {
                            case "city":
                                if (UserPreference.getAllPace() <= 0) {
                                    is_use_button.setText("解锁条件:累计10000步");
                                    isUser = false;
                                    is_use_button.setEnabled(false);
                                    is_use_button.setBackgroundResource(R.drawable.corners_button_login_locked);
                                } else {
                                    is_use_button.setText("使用");
                                    isUser = true;
                                    is_use_button.setEnabled(true);
                                    is_use_button.setBackgroundResource(R.drawable.corners_button_backgroud);
                                }

                                break;
                            case "desert":
                                if (UserPreference.getTodayPaceNum() < 18000) {
                                    is_use_button.setText("解锁条件：当日步数大于18000步");
                                    isUser = false;
                                    is_use_button.setEnabled(false);
                                    is_use_button.setBackgroundResource(R.drawable.corners_button_login_locked);
                                } else {
                                    is_use_button.setText("使用");
                                    isUser = true;
                                    is_use_button.setEnabled(true);
                                    is_use_button.setBackgroundResource(R.drawable.corners_button_backgroud);
                                }
                                break;
                            case "forest":
                                is_use_button.setText("使用");
                                isUser = true;
                                is_use_button.setEnabled(true);
                                is_use_button.setBackgroundResource(R.drawable.corners_button_backgroud);
                                break;
                        }
                    }

                    if(isUser || is_use_button.isClickable()){

                        is_use_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RaisingPetsApplication.getInstance().getJobManager()
                                        .addJobInBackground(new ChangeBackgroudJob(identifier,dialog));
                            }
                        });
                    }

                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return backgroudLists.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView background_img;
        TextView title_tx;
        TextView description;
        ImageView isLocked_img;

        ItemViewHolder(View itemView) {
            super(itemView);

            background_img = (ImageView)itemView.findViewById(R.id.iv_background);
            title_tx = (TextView)itemView.findViewById(R.id.tv_title);
            description = (TextView)itemView.findViewById(R.id.tv_description);
            isLocked_img = (ImageView)itemView.findViewById(R.id.isLocked_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

