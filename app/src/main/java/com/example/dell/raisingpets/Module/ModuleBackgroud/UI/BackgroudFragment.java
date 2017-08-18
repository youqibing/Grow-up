package com.example.dell.raisingpets.Module.ModuleBackgroud.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event.BackgroudEvent;
import com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event.BackgroudJob;
import com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event.ChangeBackgroudEvent;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/10/12.
 */

public class BackgroudFragment extends Fragment {
    private RecyclerView recyclerView;
    private BackgroudAdapter adapter;

    private JobManager jobManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.backgroud_fragment,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        jobManager.addJobInBackground(new BackgroudJob());

        return view;
    }

    public void onEventMainThread(BackgroudEvent event) {
        adapter = new BackgroudAdapter(getActivity(), event.backgroudLists);

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
