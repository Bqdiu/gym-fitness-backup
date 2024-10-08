package com.example.gymfitness.fragments.resources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.adapters.resources.ArticleResourceAdapter;

import com.example.gymfitness.databinding.FragmentArticleResourceBinding;
import com.example.gymfitness.viewmodels.ArticleDetailResourceViewModel;
import com.example.gymfitness.viewmodels.HomeViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.ArrayList;

public class ArticleResourceFragment extends Fragment {
    private ArticleResourceAdapter articleResourceAdapter;
    private FragmentArticleResourceBinding binding;
    private HomeViewModel homeViewModel;
    private SharedViewModel sharedViewModel;
    private ArticleDetailResourceViewModel articleDetailResourceViewModel;
    public ArticleResourceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_resource, container, false);
        articleResourceAdapter = new ArticleResourceAdapter(new ArrayList<>());
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return binding.getRoot();
    }
    private void setupArticlesRecyclerView() {
        RecyclerView recyclerView = binding.rcVArticleResource;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(articleResourceAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupArticlesRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // homeViewModel.loadArticles();
        homeViewModel.getArticles().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    binding.progressBarArticle.setVisibility(View.VISIBLE);
                    break;
                case "Success":
                    binding.progressBarArticle.setVisibility(View.GONE);
                    articleResourceAdapter.setArticleList(resource.getData());
                    break;
                case "Error":
                    binding.progressBarArticle.setVisibility(View.GONE);
                    Log.d("hello", resource.getMessage());
                    break;
            }
        });

        articleResourceAdapter.setOnItemClickListener(article -> {
            Bundle bundle = new Bundle();
            bundle.putString("articleTitle", article.getArticle_title());
            sharedViewModel.setArticle(article);
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.articleResourceDetailFragment, bundle);
        });

    }

}