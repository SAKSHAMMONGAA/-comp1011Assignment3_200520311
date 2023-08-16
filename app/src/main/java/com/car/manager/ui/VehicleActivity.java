package com.car.manager.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.car.manager.R;
import com.car.manager.databinding.ActivityVehicleBinding;
import com.car.manager.models.Vehicle;
import com.squareup.picasso.Picasso;

import java.io.File;


public class VehicleActivity extends AppCompatActivity {

    private ActivityVehicleBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Vehicle vehicle = getIntent().getParcelableExtra("vehicle");

        if (vehicle != null) {
            bindVehicle(vehicle);

            binding.buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(VehicleActivity.this, VehicleEditActivity.class);
                intent.putExtra("vehicle", vehicle);
                startActivity(intent);
            });
        }

    }

    private void bindVehicle(Vehicle v) {

        Picasso.get().load(Uri.fromFile(new File(v.getFullSizeImage())))
                .error(R.drawable.baseline_car_crash_24)
                .into(binding.imageView);

        binding.modelValue.setText(v.getModel());
        binding.makeValue.setText(v.getMake());
        binding.conditionValue.setText(v.getCondition());
        binding.engineValue.setText(v.getEngineCylinders());
        binding.yearValue.setText(String.valueOf(v.getYear()));
        binding.doorsValue.setText(String.valueOf(v.getNumberOfDoors()));
        binding.priceValue.setText(String.valueOf(v.getPrice()));
        binding.colorValue.setText(v.getColor());
        binding.dateSoldValue.setText(v.getDateSold());
    }
}