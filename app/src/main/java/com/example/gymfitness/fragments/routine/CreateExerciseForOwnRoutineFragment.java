package com.example.gymfitness.fragments.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ExerciseForOwnRoutineAdapter;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.FragmentCreateExerciseForOwnRoutineBinding;
import com.example.gymfitness.viewmodels.OwnRoutineViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CreateExerciseForOwnRoutineFragment extends Fragment {
    private ExerciseForOwnRoutineAdapter adapter;
    private int roundId = 0;

    public CreateExerciseForOwnRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCreateExerciseForOwnRoutineBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_create_exercise_for_own_routine, container, false);
        OwnRoutineViewModel ownRoutineViewModel = new ViewModelProvider(this).get(OwnRoutineViewModel.class);

        binding.setOwnRoutineViewModel(ownRoutineViewModel);
        binding.setLifecycleOwner(this);

        // Nhận RoundId từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            roundId = bundle.getInt("roundId", 0);
        }

        // Kiểm tra roundId hợp lệ
        if (roundId <= 0) {
            return binding.getRoot();
        }

        // Lấy Exercises từ Firebase
        ownRoutineViewModel.getExercisesFromFirebase();

        // Khởi tạo adapter với danh sách rỗng
        adapter = new ExerciseForOwnRoutineAdapter(new ArrayList<>(), inflater, new ExerciseForOwnRoutineAdapter.ExerciseAddListener() {
            @Override
            public void onExerciseAdded(Exercise exercise) {
                ownRoutineViewModel.addExerciseToRound(exercise, roundId);
            }
        }, roundId);

        binding.gridViewExercise.setAdapter(adapter);

        // danh sách tất cả các bài tập từ Room
        ownRoutineViewModel.getExercises().observe(getViewLifecycleOwner(), exercises -> {
            if (exercises != null) {
                adapter.setAllExercises(exercises);
            }
        });

        // danh sách các bài tập đã thêm vào Round
        LiveData<List<Exercise>> addedExercisesLiveData = ownRoutineViewModel.getExercisesForRound(roundId);

        addedExercisesLiveData.observe(getViewLifecycleOwner(), addedExercises -> {
            Set<Integer> addedExerciseIds = new HashSet<>();
            if (addedExercises != null) {
                for (Exercise e : addedExercises) {
                    addedExerciseIds.add(e.getExercise_id());
                }
            }
            adapter.setAddedExerciseIds(addedExerciseIds); // set cac bai tap duoc them vao round vao danh sach duoc them
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Create Your Routine");
    }
}
