package com.example.dell.raisingpets.Module.ModuleCharacters.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Module.ModuleCharacters.Job_and_Event.CharacterEvent;
import com.example.dell.raisingpets.Module.ModuleCharacters.Job_and_Event.CharactersJob;
import com.example.dell.raisingpets.R;

import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/10/22.
 */

public class CharacterFragment extends Fragment {
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;

    private JobManager jobManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.characters_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        jobManager.addJobInBackground(new CharactersJob());

        return view;
    }

    public void onEventMainThread(CharacterEvent event) {
        adapter = new CharacterAdapter(getActivity(), event.characterLists);

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