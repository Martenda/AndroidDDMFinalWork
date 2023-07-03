package com.example.taskmasters.ui.main.tasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmasters.databinding.FragmentTaskBinding;
import com.example.taskmasters.model.user.UserType;
import com.example.taskmasters.ui.main.tasks.createService.CreateServiceActivity;
import com.example.taskmasters.ui.main.tasks.placeholder.TaskContent.TaskPlaceholder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<TaskPlaceholder> mValues;
    private final Context mContext;

    public TaskRecyclerViewAdapter(List<TaskPlaceholder> items, Context context) {
        mValues = items;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPrice.setText(mValues.get(position).price);
        holder.mTitle.setText(mValues.get(position).title);
        holder.mDescription.setText(mValues.get(position).description);
        holder.mCategory.setText(mValues.get(position).category.toString());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mPrice;
        public final TextView mTitle;
        public final TextView mDescription;
        public final TextView mCategory;
        public final TextView mPricePlaceholder;
        public TaskPlaceholder mItem;

        private final Context context;

        public ViewHolder(FragmentTaskBinding binding, Context context) {
            super(binding.getRoot());
            mPrice = binding.price;
            mTitle = binding.titleTask;
            mDescription = binding.descriptionTask;
            mCategory = binding.categoryTask;
            mPricePlaceholder = binding.dollarPlaceholder;
            this.context = context;

            SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("user_id", "none");
            int userType = sharedPreferences.getInt("user_type", 1);

            binding.taskContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userType == UserType.CONSUMER.type) {
                        Snackbar.make(v, "Contratar Serviço", 2000).show();
                    } else {
                        String taskId = mItem.id;
                        Intent intent = new Intent(context, CreateServiceActivity.class);
                        intent.putExtra("taskId", taskId);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @NonNull
        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mPrice=" + mPrice +
                    ", mTitle=" + mTitle +
                    ", mDescription=" + mDescription +
                    ", mCategory=" + mCategory +
                    ", mPricePlaceholder=" + mPricePlaceholder +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}