package com.example.dell.raisingpets.Module.ModuleCharacters.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.NetWork.Result.CharactersResult;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.View.IphoneStyleDialog;

import java.util.List;

/**
 * Created by dell on 2016/10/22.
 */

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CharactersResult.CharacterList> characters;
    private Context context;
    private Dialog dialog;
    private boolean isUser = false;
    private Button is_use_button;

    public CharacterAdapter(Context context,List<CharactersResult.CharacterList> characters){
        this.context = context;
        this.characters = characters;
        dialog = IphoneStyleDialog.CharacterDialog(context);
        is_use_button = (Button)dialog.findViewById(R.id.is_use_button);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_characters,parent,false);
        Log.e("testCharacterAdapter","onCreateViewHolder"+System.currentTimeMillis());
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("testCharacterAdapter","onBindViewHolder"+System.currentTimeMillis());
        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).title_tx.setText(characters.get(position).getName());
            ((ItemViewHolder) holder).description.setText(characters.get(position).getDescribe());
            Glide.with(context)
                    .load(characters.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().into(((ItemViewHolder) holder).background_img);
            //((ItemViewHolder) holder).isLocked_img.setImageResource(R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String identifier = characters.get(position).getIdentifier();

                    ((TextView) dialog.findViewById(R.id.content_title)).setText(characters.get(position).getName());
                    ((TextView) dialog.findViewById(R.id.content_tx)).setText(characters.get(position).getDescribe());

                    Glide.with(context)
                            .load(characters.get(position).getImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop().into((ImageView) dialog.findViewById(R.id.content_img));

                    switch (identifier) {
                        case "wuming":
                            is_use_button.setText("尚未解锁");
                            is_use_button.setBackgroundResource(R.drawable.corners_button_login_locked);
                            break;
                        case "Alixiya":
                            is_use_button.setText("使用中");
                            is_use_button.setBackgroundResource(R.drawable.corners_button_login_locked);
                            break;
                    }

                    dialog.show();

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        Log.e("testCharacterAdapter","getItemCount"+System.currentTimeMillis());
        return characters.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

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

        }

    }


}
