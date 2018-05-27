package com.ninthsemester.ui.home.tasks;

import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.data.models.Task;
import com.example.ui.R;
import com.ninthsemester.ui.common.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ninthsemester.ui.common.Constants.DATE_DEADLINE_FORMAT;
import static com.ninthsemester.ui.home.tasks.ScreenType.ACTIVE;
import static com.ninthsemester.ui.home.tasks.ScreenType.COMPLETED;


/**
 * Created by sahil-mac on 15/05/18.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>{

    private static final String TAG = "TasksAdapter";

    private List<Task> mTasks = new ArrayList<>();
    private TaskSelectionCallback mCallback;
    private ScreenType mScreenType;

    public TasksAdapter(TaskSelectionCallback callback, ScreenType screenType) {
        mCallback = callback;
        mScreenType = screenType;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @LayoutRes int layoutId = 0;

        switch (mScreenType) {
            case ACTIVE:
                layoutId = R.layout.item_active_task;
                break;
            case COMPLETED:
                layoutId = R.layout.item_completed_task;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new TaskViewHolder(view);
    }

    public void setTasks(List<Task> tasks) {
        this.mTasks.clear();
        this.mTasks.addAll(tasks);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onBind(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDeadline;
        private ImageView imageView;

        private Task task;

        TaskViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_task_title);
            tvDescription = itemView.findViewById(R.id.tv_task_description);
            tvDeadline = itemView.findViewById(R.id.tv_deadline);
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.iv_indicator_active);


            if (mScreenType == COMPLETED) {
                tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvDescription.setPaintFlags(tvDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        @Override
        public void onClick(View v) {
            mCallback.onTaskSelected(task);
        }

        void onBind(Task task) {

            //  Setting the task. This will be used for sending click events.
            this.task = task;

            //  title can never be empty. Therefore we can directly set it.
            tvTitle.setText(task.getTaskTitle());

            //  If there is no description set, we will make it gone.
            //  This allows us to center the title in the screen.
            if (task.getTaskDescription() == null || task.getTaskDescription().isEmpty())  {
                tvDescription.setVisibility(View.GONE);
            } else {
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(task.getTaskDescription());
            }

            //  We have 4 different types of icons based on screen type
            //  and deadline.
            //  Screen COMPLETED -> Show done icon
            //  Screen ACTIVE, No Deadline -> Show grey default icon
            //  Screen ACTIVE, Deadline passed -> Show grey alarm icon
            //  Screen ACTIVE, Deadline due, Show green alarm icon
            Date deadline = task.getTaskDeadline();
            if (deadline != null) {

                tvDeadline.setText(Utils.getDateInFormat(deadline, DATE_DEADLINE_FORMAT));
                if (mScreenType == ACTIVE) {

                    //  Choosing between grey alarm and green alarm icon.
                    int drawable =
                            Utils.isDateFromFuture(deadline) ?
                                    R.drawable.ic_green_alarm_circle :
                                    R.drawable.ic_grey_alarm_circle;
                    imageView.setBackgroundResource(drawable);
                }
            } else {
                //  resetting the date set by previous item
                tvDeadline.setText("");
                if (mScreenType == ACTIVE) {
                    //  put grey default icon
                    imageView.setBackgroundResource(R.drawable.ic_grey_unchecked_circle);
                }
            }

            if (mScreenType == COMPLETED) {
                //  putting the completed icon
                imageView.setBackgroundResource(R.drawable.ic_green_checked_circle);
            }
        }
    }

    interface TaskSelectionCallback {
        void onTaskSelected(Task task);
    }
}
