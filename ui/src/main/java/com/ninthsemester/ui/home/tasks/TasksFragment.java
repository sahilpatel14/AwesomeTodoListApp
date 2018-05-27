package com.ninthsemester.ui.home.tasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.data.repositories.TaskRepository;
import com.example.ui.R;
import com.ninthsemester.ui.common.Injection;
import com.ninthsemester.ui.common.Utils;
import com.ninthsemester.ui.home.HomeActivity;

/**
 * Created by sahil-mac on 15/05/18.
 */

public abstract class TasksFragment extends Fragment implements TasksAdapter.TaskSelectionCallback {

    private static final String TAG = "TasksFragment";

    protected RecyclerView recyclerView;
    protected TasksAdapter adapter;
    protected ProgressBar progressBar;
    protected LinearLayout llListEmpty;
    private HomeActivity mAttachedActivity;

    protected TaskRepository taskRepository;
    protected ScreenType screenType;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            mAttachedActivity = (HomeActivity) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "Unable to cast activity as HomeActivity ", e);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        initViews(rootView);

        if (getContext() != null)
            taskRepository = Injection.getTaskRepository(getContext());


        return rootView;
    }

    private void initViews(View rootView) {
        progressBar = rootView.findViewById(R.id.list_progress_bar);
        recyclerView = rootView.findViewById(R.id.rv_task_list);
        llListEmpty = rootView.findViewById(R.id.rl_list_empty);


        if (getContext() != null)
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        adapter = new TasksAdapter(this, screenType);
        recyclerView.setAdapter(adapter);
    }

    abstract public void onFabClicked();

    protected void showInformationalText(String message) {
        mAttachedActivity.showInformationText(message);
    }

    protected void showErrorMessage(String message) {
        AlertDialog alertDialog = Utils.simpleAlertDialog(getContext(), message);
        alertDialog.show();
    }
}
