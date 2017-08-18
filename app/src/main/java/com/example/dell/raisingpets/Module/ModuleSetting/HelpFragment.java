package com.example.dell.raisingpets.Module.ModuleSetting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event.SendWordEvent;
import com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event.SendWordsJob;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-22.
 */

public class HelpFragment extends Fragment {
    private EditText contact_content_edt;
    private EditText contact_edt;
    private TextView wraning_tx;
    private JobManager jobManager;

    private Dialog lodingDialog;

    private RelativeLayout about_us_relative;
    private RelativeLayout contact_us_relative;
    private RelativeLayout help_mian_relative;

    private final int MAX_COUNT = 300;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        lodingDialog = IphoneStyleDialog.loadingDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.help_fragment,container,false);

        about_us_relative = (RelativeLayout)view.findViewById(R.id.about_us_relative);
        contact_us_relative = (RelativeLayout)view.findViewById(R.id.contact_us_relative);
        help_mian_relative = (RelativeLayout)view.findViewById(R.id.help_mian_relative);

        contact_content_edt = (EditText)view.findViewById(R.id.contact_content);
        contact_content_edt.addTextChangedListener(mTextWatcher);
        contact_edt = (EditText)view.findViewById(R.id.contact_edt);
        wraning_tx = (TextView)view.findViewById(R.id.wraning_tx);
        setLeftCount();

        about_us_relative.setVisibility(View.GONE);
        contact_us_relative.setVisibility(View.GONE);

        ((Button)view.findViewById(R.id.about_us)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_mian_relative.setVisibility(View.GONE);
                about_us_relative.setVisibility(View.VISIBLE);
            }
        });

        ((Button)view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about_us_relative.setVisibility(View.GONE);
                help_mian_relative.setVisibility(View.VISIBLE);
            }
        });

        ((Button)view.findViewById(R.id.contanct_us)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_mian_relative.setVisibility(View.GONE);
                contact_us_relative.setVisibility(View.VISIBLE);
            }
        });

        ((Button)view.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact_us_relative.setVisibility(View.GONE);
                help_mian_relative.setVisibility(View.VISIBLE);
            }
        });

        ((Button)view.findViewById(R.id.send_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contact_edt.getText().toString().equals("")){
                    ToastUtil.showShortToast("联系方式不能为空!");
                }else if(contact_content_edt.getText().toString().equals("")){
                    ToastUtil.showShortToast("文本内容不能为空!");
                }else if(getInputCount()>MAX_COUNT){
                    ToastUtil.showShortToast("文本字数超出限制,请将字数限制在300字以内!");
                }else {
                    jobManager.addJobInBackground(new SendWordsJob(contact_edt.getText().toString(),contact_content_edt.getText().toString()));
                }
            }
        });

        return view;
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;

        public void afterTextChanged(Editable s) {
            editStart = contact_content_edt.getSelectionStart();
            editEnd = contact_content_edt.getSelectionEnd();

            // 先去掉监听器，否则会出现栈溢出
            contact_content_edt.removeTextChangedListener(mTextWatcher);

            // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
            // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
            while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            // mEditText.setText(s);将这行代码注释掉就不会出现后面所说的输入法在数字界面自动跳转回主界面的问题了，多谢@ainiyidiandian的提醒
            contact_content_edt.setSelection(editStart);

            // 恢复监听器
            contact_content_edt.addTextChangedListener(mTextWatcher);

            setLeftCount();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    };

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
     */
    private void setLeftCount() {
        wraning_tx.setText(String.valueOf((MAX_COUNT - getInputCount())));
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(contact_content_edt.getText().toString());
    }

    public void onEventMainThread(ProgressEvent event){
        lodingDialog.show();
    }

    public void onEventMainThread(SendWordEvent event){
        boolean isSuccess = event.isSuccess();
        if(isSuccess) {
            lodingDialog.dismiss();
            contact_edt.getText().clear();
            contact_content_edt.getText().clear();
            about_us_relative.setVisibility(View.GONE);
            contact_us_relative.setVisibility(View.GONE);
            help_mian_relative.setVisibility(View.VISIBLE);
            ToastUtil.showLongToast("反馈成功,谢谢您的建议,我们会持续改进");
        }else {
            ToastUtil.showLongToast(event.getMsg()+"");
        }
    }

}
