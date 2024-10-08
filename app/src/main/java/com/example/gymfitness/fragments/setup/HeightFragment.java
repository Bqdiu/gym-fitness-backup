package com.example.gymfitness.fragments.setup;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentHeightBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;
import com.shawnlin.numberpicker.NumberPicker;

public class HeightFragment extends Fragment {
    FragmentHeightBinding binding;
    SetUpViewModel setUpViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_height, container, false);
        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);
        binding.numberPicker.setSelectedTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);
        binding.numberPicker.setTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);
        setUpViewModel.setHeight(165);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.txtHeight.setText(String.valueOf(newVal));
                setUpViewModel.setHeight(newVal);
            }
        });
    }
}