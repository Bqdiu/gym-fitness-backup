package com.example.gymfitness.adapters.home;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.ExerciseItemBinding;
import com.example.gymfitness.R;
import com.example.gymfitness.utils.Converters;

import java.util.ArrayList;

public class ExerciseRCVAdapter extends RecyclerView.Adapter<ExerciseRCVAdapter.ExerciseHolder> {

    private ArrayList<Exercise> exercisesList;

    public ExerciseRCVAdapter(ArrayList<Exercise> list)
    {
        this.exercisesList = list;
    }
    @NonNull
    @Override
    public ExerciseRCVAdapter.ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ExerciseItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.exercise_item, parent, false);
        return new ExerciseRCVAdapter.ExerciseHolder(binding);
    }
    static class ExerciseHolder extends RecyclerView.ViewHolder {
        private ExerciseItemBinding binding;
        public ExerciseHolder(@NonNull ExerciseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Exercise exercise) {
            binding.setExercise(exercise);
            binding.executePendingBindings();
            binding.tvTime.setText(Converters.convertSecondsToTimeFormat(exercise.getDuration()));
        }
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ExerciseRCVAdapter.ExerciseHolder holder, int position) {
        Exercise exercise = exercisesList.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercisesList == null ? 0 : exercisesList.size();
    }

    public void setWorkoutList(ArrayList<Exercise> exercises) {
        this.exercisesList.clear();
        this.exercisesList.addAll(exercises);
        notifyDataSetChanged();
    }

}
