package com.example.gymfitness.fragments.resources;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.help.HelpViewpagerAdapter;
import com.example.gymfitness.adapters.resources.ResourcesViewpagerAdapter;
import com.example.gymfitness.databinding.FragmentResourcesBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ResourcesFragment extends Fragment {
    FragmentResourcesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        binding = FragmentResourcesBinding.bind(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpTabs();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Resources");
    }

    private void setUpTabs() {
        binding.viewPager.setAdapter(new ResourcesViewpagerAdapter(getChildFragmentManager(), getLifecycle()));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Workout Videos");
                    break;
                case 1:
                    tab.setText("Articles & Tips");
                    break;
            }
        }).attach();

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            View tabView = ((ViewGroup) binding.tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
            layoutParams.setMargins(15, 0, 15, 0);  // Set left and right margins here
            tabView.setLayoutParams(layoutParams);
            tabView.requestLayout();
        }
    }
}