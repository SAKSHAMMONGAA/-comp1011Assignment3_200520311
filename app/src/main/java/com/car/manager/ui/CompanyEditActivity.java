package com.car.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.car.manager.R;
import com.car.manager.databinding.ActivityCompanyEditBinding;
import com.car.manager.models.Company;
import com.car.manager.ui.viewmodels.AppViewModel;
import com.car.manager.util.JsonUtil;
import com.squareup.picasso.Picasso;

import java.io.File;


public class CompanyEditActivity extends AppCompatActivity {

    private ActivityCompanyEditBinding binding;

    private static final int REQUEST_CODE_SELECT_FULL_IMAGE = 1;
    private AppViewModel viewModel;
    private String fullImagePath = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCompanyEditBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        setContentView(binding.getRoot());

        binding.btnSelectFullImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_SELECT_FULL_IMAGE);
        });

        binding.buttonEdit.setOnClickListener(v -> {
            save(getApplicationContext());
        });

        viewModel.getCompanyMutableLiveData().observe(this, this::bindCompany);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_FULL_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            fullImagePath = getImagePathFromUri(selectedImageUri);
            Uri fromPath = Uri.fromFile(new File(fullImagePath));

            Picasso.get().load(fromPath)
                    .error(R.drawable.round_compost_24)
                    .into(binding.imageView);
        }
    }


    private void save(Context context) {
        if (!cleanData()) {
            return;
        }

        Company company = new Company();
        company.setName(binding.nameValue.getText().toString());
        company.setStreetName(binding.streetValue.getText().toString());
        company.setCity(binding.cityValue.getText().toString());
        company.setProvince(binding.provinceValue.getText().toString());
        company.setPostalCode(binding.postalCodeValue.getText().toString());
        company.setCarsSold(Integer.parseInt(binding.carsValue.getText().toString()));
        company.setTotalProfit(Double.parseDouble(binding.profitValue.getText().toString()));

        company.setLogoImage(fullImagePath);

        JsonUtil.saveCompanyToFile(context, company);
        Toast.makeText(context, "Updated " + company.getName() + " successfully", Toast.LENGTH_SHORT).show();


    }

    private boolean cleanData() {
        if (TextUtils.isEmpty(binding.nameValue.getText().toString())) {
            binding.nameValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.streetValue.getText().toString())) {
            binding.streetValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.cityValue.getText().toString())) {
            binding.cityValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.provinceValue.getText().toString())) {
            binding.provinceValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.postalCodeValue.getText().toString())) {
            binding.postalCodeValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.cityValue.getText().toString())) {
            binding.carsValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.profitValue.getText().toString())) {
            binding.profitValue.setError("Profit required!");
            return false;
        }

        return true;

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

    private String getImagePathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return uri.getPath();
    }
}