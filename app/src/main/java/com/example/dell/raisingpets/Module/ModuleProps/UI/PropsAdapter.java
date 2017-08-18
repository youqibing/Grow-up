package com.example.dell.raisingpets.Module.ModuleProps.UI;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Module.ModuleCharacters.ui.CharacterAdapter;
import com.example.dell.raisingpets.NetWork.Result.PropsResult;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.View.IphoneStyleDialog;

import java.util.List;

/**
 * Created by root on 16-11-20.
 */

public class PropsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PropsResult.PropsList> propsLists;
    private Context context;
    private Dialog dialog;
    private Button is_use_button;

    public PropsAdapter(Context context,List<PropsResult.PropsList> propsLists){
        this.context = context;
        this.propsLists = propsLists;
        dialog = IphoneStyleDialog.CharacterDialog(context);
        is_use_button = (Button)dialog.findViewById(R.id.is_use_button);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_characters,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof PropsAdapter.ItemViewHolder){
            ((PropsAdapter.ItemViewHolder) holder).title_tx.setText(propsLists.get(position).getName());
            ((PropsAdapter.ItemViewHolder) holder).description.setText(propsLists.get(position).getDescribe());
            //((ItemViewHolder) holder).background_img.setImageResource(R.mipmap.ic_launcher);
            Glide.with(context)
                    .load(propsLists.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().into(((PropsAdapter.ItemViewHolder) holder).background_img);
            //((ItemViewHolder) holder).isLocked_img.setImageResource(R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((TextView) dialog.findViewById(R.id.content_title)).setText(propsLists.get(position).getName());
                    ((TextView) dialog.findViewById(R.id.content_tx)).setText(propsLists.get(position).getDescribe());
                    Glide.with(context)
                            .load(propsLists.get(position).getImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop().into((ImageView) dialog.findViewById(R.id.content_img));

                    is_use_button.setText("尚未解锁");
                    is_use_button.setBackgroundResource(R.drawable.corners_button_login_locked);

                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return propsLists.size();
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
