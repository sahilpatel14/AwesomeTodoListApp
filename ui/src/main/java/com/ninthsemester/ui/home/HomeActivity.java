package com.ninthsemester.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;


import com.example.ui.R;
import com.ninthsemester.ui.home.activeTasks.ActiveTasksFragment;
import com.ninthsemester.ui.home.completedTasks.CompletedTasksFragment;
import com.ninthsemester.ui.home.tasks.TasksFragment;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private ViewPager mTodoListViewPager;
    private List<TasksFragment> todoListFragments;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewPager();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener((it) -> notifyFragmentAboutFabClickEvent());
    }

    /**
     * Sends a call to Task Fragment whenever FAB is clicked.
     */
    private void notifyFragmentAboutFabClickEvent() {
        int currentPagePosition = mTodoListViewPager.getCurrentItem();
        TasksFragment currentFragment = todoListFragments.get(currentPagePosition);
        currentFragment.onFabClicked();
    }

    /**
     * Called by TaskFragment to show information Text on Snack bar.
     */
    public void showInformationText(String message) {
        Snackbar.make(fab, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * Setting up view pager.
     */
    private void setupViewPager() {

        mTodoListViewPager = findViewById(R.id.vp_todo_list);
        todoListFragments = Arrays.asList(new ActiveTasksFragment(), new CompletedTasksFragment());
        TodoListPagerAdapter mViewPagerAdapter = new TodoListPagerAdapter(getSupportFragmentManager());
        mTodoListViewPager.setAdapter(mViewPagerAdapter);
    }


    /**
     * Fragment adapter for our ViewPager.
     * Loads ActiveTaskFragment and CompletedTaskFragment
     */
    private class TodoListPagerAdapter extends FragmentStatePagerAdapter {

        TodoListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return todoListFragments.get(position);
        }

        @Override
        public int getCount() {
            return todoListFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 : return getString(R.string.active);
                case 1 : return getString(R.string.completed);
                default: throw new IllegalStateException("Invalid page position. We only have active and completed.");
            }
        }
    }

}
