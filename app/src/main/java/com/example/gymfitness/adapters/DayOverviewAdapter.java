package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.data.DayOverview;
import com.example.gymfitness.databinding.ChartItemBinding;

import java.util.List;

public class DayOverviewAdapter extends RecyclerView.Adapter<DayOverviewAdapter.MyViewHolder> {
    private List<DayOverview> dayOverviews;

    public DayOverviewAdapter(List<DayOverview> dayOverviews) {
        this.dayOverviews = dayOverviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChartItemBinding chartItemBinding=ChartItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(chartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(dayOverviews.get(position));
    }

    @Override
    public int getItemCount() {
        return dayOverviews.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ChartItemBinding binding;

        public MyViewHolder(@NonNull ChartItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bindData(DayOverview dayOverview){
            String dayOfWeek=dayOverview.getDayOfWeek(dayOverview.getDate());
            int dayOfMonth=dayOverview.getDayOfMonth(dayOverview.getDate());

            binding.tvDayOfWeek.setText(dayOfWeek);
            binding.tvDayOfMonth.setText(String.valueOf(dayOfMonth));
            binding.tvSteps.setText(String.valueOf(dayOverview.getRep()));
            binding.tvDuration.setText(String.valueOf(dayOverview.getDuration()));
        }
    }
}
