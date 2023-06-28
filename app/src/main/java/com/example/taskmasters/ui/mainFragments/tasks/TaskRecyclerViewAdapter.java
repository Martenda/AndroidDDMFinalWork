package com.example.taskmasters.ui.mainFragments.tasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmasters.R;
import com.example.taskmasters.databinding.FragmentTaskBinding;
import com.example.taskmasters.ui.mainFragments.tasks.placeholder.PlaceholderContent.TaskPlaceholder;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<TaskPlaceholder> mValues;

    public TaskRecyclerViewAdapter(List<TaskPlaceholder> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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

        public ViewHolder(FragmentTaskBinding binding) {
            super(binding.getRoot());
            mPrice = binding.price;
            mTitle = binding.titleTask;
            mDescription = binding.descriptionTask;
            mCategory = binding.categoryTask;
            mPricePlaceholder = binding.dollarPlaceholder;

            binding.taskContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("hi");
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