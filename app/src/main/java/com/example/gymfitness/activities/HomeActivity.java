package com.example.gymfitness.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.ActivityHomeBinding;
import com.example.gymfitness.utils.UserData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private BottomNavigationView bottomNavigation;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar, (v, insets) -> {
            v.setPadding(insets.getSystemWindowInsetLeft(),
                    insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(),
                    0);
            return insets;
        });
        setSupportActionBar(binding.toolbar);


        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMainContainerView);
        navController = navHostFragment.getNavController();

        bottomNavigation = binding.navigationBottom;
        // update checked bottom navigation
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            int id2 = R.id.homeFragment;
            Log.d("checkid", String.valueOf(id));
            if(id == R.id.homeFragment)
            {
                MenuItem menuItem = bottomNavigation.getMenu().findItem(R.id.ic_homeFragment);
                if (menuItem != null) {
                    menuItem.setChecked(true);
                }
            }else if(id == R.id.resourcesFragment2)
            {
                MenuItem menuItem = bottomNavigation.getMenu().findItem(R.id.ic_resourcesFragment);
                if (menuItem != null) {
                    menuItem.setChecked(true);
                }
            }else if(id == R.id.favoritesFragment)
            {
                MenuItem menuItem = bottomNavigation.getMenu().findItem(R.id.ic_startsFragment);
                if (menuItem != null) {
                    menuItem.setChecked(true);
                }
            }
        });

        username = UserData.getUsername(this);

        // setting toolbar
        navController.addOnDestinationChangedListener(((navController, navDestination, bundle) -> {
            if(navDestination.getId() == R.id.homeFragment) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                binding.toolbar.setTitle("Hi, " + username);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                binding.toolbar.setNavigationIcon(R.drawable.arrow);
            }
        }));
        // bottom navigation
        NavigationUI.setupWithNavController(bottomNavigation,navController);
        // event
        addEvents();

    }



    private void addEvents()
    {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.navigationBottom.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.ic_homeFragment){
                navController.navigate(R.id.homeFragment);
                return true;
            } else if(id == R.id.ic_resourcesFragment){
                navController.navigate(R.id.resourcesFragment2);
                return true;
            }
            else if (id == R.id.ic_startsFragment){
                navController.navigate(R.id.favoritesFragment);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ic_profile){
            navController.navigate(R.id.profileFragment);
            return true;
        } else if(id == R.id.ic_search){
            navController.navigate(R.id.allSearchFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public BottomNavigationView getBottomNavigationView() {
        return binding.navigationBottom;
    }
}
