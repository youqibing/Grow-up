package com.example.dell.raisingpets.Module.ModuleLogin.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Util.AsyncTaskLoader;
import com.example.dell.raisingpets.Util.IAsyncCallback;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.FinishInformationEvent;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.HeaderPhotoEvent;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.HeaderPhotoJob;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;

import de.greenrobot.event.EventBus;


/**
 * Created by dell on 2016/8/14.
 */

public class FinishInformationActivity extends BaseActivity implements View.OnClickListener{
    private Button back_btn;

    private EditText nickName_edt;
    private Button complete_btn;
    private ImageView avatarImage;
    private Switch sexSwitch;

    private RegisterActivity activity;
    private RelativeLayout change_relative;

    private Bitmap uploadBitmap;
    private JobManager jobManager;

    private int gender = 0;
    private boolean isPhotoChange;

    public static final int FROM_LOCAL = 130;
    public static final int FROM_CAMERA = 131;
    public static final int FROM_CUT = 132;

    public Uri uriPath;

    public static final String RAISING_PETS_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"RaisingPets" + "/" ;
    public static final String RAISING_PETS_UPLOAD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"RaisingPets"+"/" + "headPhoto.png";
    public static final String RAISING_PETS_PATH = "file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"RaisingPets"+"/" + "headPhoto.png";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishinformation_fragment);

        back_btn = (Button)findViewById(R.id.back);
        change_relative = (RelativeLayout)findViewById(R.id.change_photo);
        nickName_edt = (EditText)findViewById(R.id.nickname);
        avatarImage = (ImageView)findViewById(R.id.avatar);
        sexSwitch = (Switch)findViewById(R.id.sex);
        complete_btn = (Button)findViewById(R.id.complete);

        back_btn.setOnClickListener(this);
        change_relative.setOnClickListener(this);
        complete_btn.setOnClickListener(this);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    sexSwitch.setText("男");
                    gender = 1;
                }else {
                    sexSwitch.setText("女");
                    gender = 2;
                }
            }
        };
        sexSwitch.setOnCheckedChangeListener(listener);

        EventBus.getDefault().register(this);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();

        ExternalStorageState();//为我们储存用户头像的路径创造一个文件夹
    }

    private void ExternalStorageState(){

        File file = new File(RAISING_PETS_FILE);
        Log.e("testFilePath",RAISING_PETS_FILE);
        Log.e("testFile",file.exists()+"");

        if (!file.exists()) {
            file.mkdirs();
            Log.e("testFile",file.exists()+"");
        }

    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.change_photo:

                showSelectDiaglog();
                break;
            case R.id.complete:
                String nickName= nickName_edt.getText().toString();
                int sex = gender;

                if(isPhotoChange){

                }
                else {
                    jobManager.addJobInBackground(new HeaderPhotoJob(Uri.parse(RAISING_PETS_PATH).toString()));//再上传一遍

                }

        }
    }

    public void showSelectDiaglog(){
        final int FROM_PHOTO_GALLERY = 0;

        CharSequence[] items = {"相册","相机"};
        new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items,new DatePickerDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == FROM_PHOTO_GALLERY){

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                            galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                            startActivityForResult(galleryIntent,FROM_LOCAL);
                        }else {

                            Intent crameIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            crameIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.parse(RAISING_PETS_PATH));
                            startActivityForResult(crameIntent,FROM_CAMERA);
                        }
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case FROM_CAMERA:
                    cutImage(Uri.parse(RAISING_PETS_PATH));
                    break;

                case FROM_LOCAL:
                    if (null != data) {
                        Uri uri = data.getData();
                        cutImage(uri);

                        //本来是要做压缩处理,后来感觉还是都做剪切比较好。
                        /*
                        ContentResolver resolver = getContentResolver();
                        if (null != getUploadBitmap()) {
                            getUploadBitmap().recycle();
                        }
                        Bitmap bitmap = ImageUtils.decodeSampledBitmapFromUri(resolver, uri, 500, 500);
                        setUploadBitmap(bitmap);

                        avatarImage.setImageBitmap(bitmap);
                        */

                    }
                    break;

                case FROM_CUT:

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final IAsyncCallback callback = new IAsyncCallback() {
                                Bitmap bitmap = null;

                                @Override
                                public void workToDo() {
                                    if (null != data) {
                                        try {
                                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriPath));
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

                default:
                    break;
            }
        }
    }

    public Bitmap getUploadBitmap() {
        return uploadBitmap;
    }

    public void setUploadBitmap(Bitmap uploadBitmap) {
        this.uploadBitmap = uploadBitmap;
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

        uriPath = Uri.parse(RAISING_PETS_PATH);
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, FROM_CUT);
    }


    public void onEvent(HeaderPhotoEvent event){

        if(event.isSucess()){
            ToastUtil.showShortToast("头像上传成功!");
            isPhotoChange = true;
        }else {
            ToastUtil.showShortToast("头像上传失败,请检查网络连接");
            isPhotoChange = false;
        }
    }

    public void onEvent(FinishInformationEvent event){
        if(event.isSucess()){
            if(isPhotoChange){
                ToastUtil.showShortToast("信息修改成功");
                activity.finish();
            }else {
                ToastUtil.showShortToast("头像上传失败，换张头像试试？");
            }
        }else {
            ToastUtil.showShortToast("修改信息失败");
        }
    }

}
