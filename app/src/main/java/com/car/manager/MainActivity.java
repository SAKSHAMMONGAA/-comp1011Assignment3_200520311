package com.car.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.viewpager.widget.ViewPager;

import com.car.manager.databinding.ActivityMainBinding;
import com.car.manager.models.Company;
import com.car.manager.models.Vehicle;
import com.car.manager.ui.CompanyActivity;
import com.car.manager.ui.ListFragment;
import com.car.manager.ui.VehicleEditActivity;
import com.car.manager.ui.main.SectionsPagerAdapter;
import com.car.manager.util.JsonUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ensureFilesExist(getApplicationContext());

        Toolbar toolbar = (Toolbar) binding.toolbar;
        setSupportActionBar(toolbar);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};

        sectionsPagerAdapter.addFragment(
                TAB_TITLES,
                ListFragment.newInstance("all"),
                ListFragment.newInstance("purchase"),
                ListFragment.newInstance("sold")
        );

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        setupMenu();

        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, VehicleEditActivity.class));
        });

       }

    private void setupMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.item_company) {
                    startActivity(new Intent(MainActivity.this, CompanyActivity.class));
                    return true;
                }
                return false;

            }
        };

        addMenuProvider(menuProvider);
    }

    public static void ensureFilesExist(Context context) {
        File vehiclesFile = new File(context.getFilesDir(), Constants.FILE_NAME_VEHICLES);
        File companiesFile = new File(context.getFilesDir(), Constants.FILE_NAME_COMPANIES);

        if (!vehiclesFile.exists()) {
            JsonUtil.saveDataToFile(context, Constants.FILE_NAME_VEHICLES, "[]");
        }

        if (!companiesFile.exists()) {
            Company company = new Company("XYZ holdings", " ", "NY 09", "Tokyo", "New Tokyo 434", "NY-0-908", 23, 45637.03);
            JsonUtil.saveCompanyToFile(context, company);
        }
    }

}