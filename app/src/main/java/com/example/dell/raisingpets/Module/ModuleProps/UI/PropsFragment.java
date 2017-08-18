package com.example.dell.raisingpets.Module.ModuleProps.UI;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Module.ModuleProps.Job_and_Event.PropsEvent;
import com.example.dell.raisingpets.Module.ModuleProps.Job_and_Event.PropsJob;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/10/12.
 */

public class PropsFragment extends Fragment {
    private RecyclerView recyclerView;
    private PropsAdapter adapter;

    private JobManager jobManager;
    private Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.props_fragment,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        jobManager.addJobInBackground(new PropsJob());

        return view;
    }

    public void onEventMainThread(PropsEvent event) {
        adapter = new PropsAdapter(getActivity(), event.propsLists);

        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

