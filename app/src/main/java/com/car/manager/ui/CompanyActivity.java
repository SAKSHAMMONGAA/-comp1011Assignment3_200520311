package com.car.manager.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.car.manager.R;
import com.car.manager.databinding.ActivityCompanyBinding;
import com.car.manager.databinding.ActivityVehicleBinding;
import com.car.manager.models.Company;
import com.car.manager.models.Vehicle;
import com.car.manager.ui.viewmodels.AppViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;


public class CompanyActivity extends AppCompatActivity {

    private ActivityCompanyBinding binding;
    private AppViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCompanyBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        setContentView(binding.getRoot());


        binding.buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(CompanyActivity.this, CompanyEditActivity.class);
            startActivity(intent);
        });

        viewModel.getCompanyMutableLiveData().observe(this, this::bindCompany);


    }

    private void bindCompany(Company company) {

        Picasso.get().load(Uri.fromFile(new File(company.getLogoImage())))
                .error(R.drawable.round_compost_24)
                .into(binding.imageView);

        binding.nameValue.setText(company.getName());
        binding.streetValue.setText(company.getStreetName());
        binding.cityValue.setText(company.getCity());
        binding.provinceValue.setText(company.getProvince());
        binding.postalCodeValue.setText(company.getPostalCode());
        binding.carsValue.setText(String.valueOf(company.getCarsSold()));
        binding.profitValue.setText(String.valueOf(company.getTotalProfit()));

    }
}