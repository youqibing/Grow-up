package com.example.dell.raisingpets.Module.ModuleMine.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.HeaderPhotoJob;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.NickNameJob;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.AsyncTaskLoader;
import com.example.dell.raisingpets.Util.IAsyncCallback;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import java.io.File;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;
import static com.example.dell.raisingpets.Module.ModuleLogin.UI.FinishInformationActivity.FROM_CUT;

/**
 * Created by dell on 2016/10/12.
 */

public class MineFragment extends Fragment implements View.OnClickListener{

    private View view;
    public Uri uriPath;
    private RelativeLayout change_relative;
    private TextView userNickName;
    private ImageView avatarImage;
    private ImageView edit_img;
    private ImageView confirm_img;
    private EditText nickname_edt;

    private JobManager jobManager;

    public static final int FROM_LOCAL = 1;
    //public static final int NICKNAME = 2;

    public static final String RAISING_PETS_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"RaisingPets" + "/" ;
    public static final String RAISING_PETS_UPLOAD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"RaisingPets"+"/" + "headPhoto.png";
    public static final String RAISING_PETS_URL_PATH = "file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"RaisingPets"+"/" + "headPhoto.png";

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        ExternalStorageState();//为我们储存用户头像的路径创造一个文件夹
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment,container,false);

        ((TextView)view.findViewById(R.id.all_steps)).setText("当前累计步数"+" : "+UserPreference.getAllPace()+"步");
        ((TextView)view.findViewById(R.id.money_tx)).setText("当前累计金币:"+" "+UserPreference.getMoney());
        avatarImage = (ImageView)view.findViewById(R.id.avatar);
        nickname_edt = (EditText)view.findViewById(R.id.nickname_edt);
        change_relative = (RelativeLayout)view.findViewById(R.id.change_photo);
        edit_img = (ImageView)view.findViewById(R.id.edit);
        confirm_img = (ImageView)view.findViewById(R.id.confirm_img);
        userNickName = (TextView)view.findViewById(R.id.userNickName);
        userNickName.setText(UserPreference.getNickName());

        nickname_edt.setVisibility(View.GONE);
        confirm_img.setVisibility(View.GONE);

        change_relative.setOnClickListener(this);
        edit_img.setOnClickListener(this);
        confirm_img.setOnClickListener(this);
        Glide.with(getActivity())
                .load(UserPreference.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into((ImageView)view.findViewById(R.id.avatar));

        /*
        ((ImageView)view.findViewById(R.id.edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), EditUserPopupActivity.class),NICKNAME);
            }
        });
        */

        return view;
    }

    private void ExternalStorageState(){
        File file = new File(RAISING_PETS_FILE);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_photo:

                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(galleryIntent,FROM_LOCAL);

                break;

            case R.id.edit:

                nickname_edt.setVisibility(View.VISIBLE);
                userNickName.setVisibility(View.GONE);
                edit_img.setVisibility(View.GONE);
                confirm_img.setVisibility(View.VISIBLE);

                break;

            case R.id.confirm_img:

                String nickName = nickname_edt.getText().toString();
                if((nickName.length() > 5)){
                    ToastUtil.showLongToast("昵称字数超出限制");
                }else {
                    jobManager.addJobInBackground(new NickNameJob(nickName,userNickName));

                    nickname_edt.setVisibility(View.GONE);
                    userNickName.setVisibility(View.VISIBLE);
                    edit_img.setVisibility(View.VISIBLE);
                    confirm_img.setVisibility(View.GONE);
                }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){

                case FROM_LOCAL:
                    if (null != data) {
                        Uri uri = data.getData();
                        cutImage(uri);
                    }
                    break;

                case FROM_CUT:
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final IAsyncCallback callback = new IAsyncCallback() {
                                Bitmap bitmap = null;

                                @Override
                                public void workToDo() {
                                    if (null != data) {
                                        try {
                                            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uriPath));
                                            //由于这个读入图片的过程极其容易引起OOM(MI 5上面),因此这里用一个AsyncTask将这个过程放到后台,这个东西真的很好用,别说小米了,大米现在也没有问题.
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onComplete() {
                                    avatarImage.setImageBitmap(bitmap);
                                    jobManager.addJobInBackground(new HeaderPhotoJob(Uri.parse(RAISING_PETS_UPLOAD).toString()));
                                }
                            };
                            new AsyncTaskLoader().execute(callback);
                        }
                    });

                    break;

                /*
                case NICKNAME:

                    String nickName = data.getExtras().getString("nickName");
                    ((TextView)view.findViewById(R.id.userNickName)).setText(nickName);
                    */

                default:
                    break;
            }
        }
    }


    public void cutImage(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。
        //设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);

        uriPath = Uri.parse(RAISING_PETS_URL_PATH);
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, FROM_CUT);
    }


}
