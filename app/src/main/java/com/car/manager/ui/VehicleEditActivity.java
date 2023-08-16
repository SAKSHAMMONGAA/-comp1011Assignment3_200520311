package com.car.manager.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.car.manager.R;
import com.car.manager.databinding.ActivityVehicleEditBinding;
import com.car.manager.models.Vehicle;
import com.car.manager.util.JsonUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class VehicleEditActivity extends AppCompatActivity {

    private final String TAG = VehicleEditActivity.class.getCanonicalName();
    private ActivityVehicleEditBinding binding;
    private static final int REQUEST_CODE_SELECT_FULL_IMAGE = 1;
    private static final int REQUEST_CODE_SELECT_THUMBNAIL = 2;

    private String fullImagePath = " ";
    private String thumbnailImagePath = " ";
    boolean editing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVehicleEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermissions();

        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().setLoggingEnabled(true);


        Vehicle vehicle = getIntent().getParcelableExtra("vehicle");

        if (vehicle != null) {
            bindVehicle(vehicle);
            editing = true;
        }

        binding.buttonSave.setOnClickListener(v -> save(getApplicationContext()));

        binding.btnSelectFullImage.setOnClickListener(v -> {
            if (checkPermissions()) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_FULL_IMAGE);
            }

        });
        binding.btnSelectThumbnail.setOnClickListener(v -> {
            if (checkPermissions()) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_THUMBNAIL);
            }
        });

    }

    private boolean checkPermissions() {
        if (!permissionGranted()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        return permissionGranted();
    }

    private boolean permissionGranted() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            return Environment.isExternalStorageManager();
        }else {
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_FULL_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            fullImagePath = getImagePathFromUri(selectedImageUri);
            Uri fromPath = Uri.fromFile(new File(fullImagePath));

            Picasso.get().load(fromPath)
                    .error(R.drawable.baseline_car_crash_24)
                    .into(binding.imageView);

        }
        if (requestCode == REQUEST_CODE_SELECT_THUMBNAIL && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            thumbnailImagePath = getImagePathFromUri(selectedImageUri);
        }
    }

    private void save(Context context) {
        if (!cleanData()) {
            return;
        }

        Vehicle v = new Vehicle();
        v.setMake(binding.makeValue.getText().toString());
        v.setModel(binding.modelValue.getText().toString());
        v.setCondition(binding.conditionValue.getText().toString());
        v.setEngineCylinders(binding.engineValue.getText().toString());
        v.setYear(Integer.parseInt(binding.yearValue.getText().toString()));
        v.setNumberOfDoors(Integer.parseInt(binding.doorsValue.getText().toString()));
        v.setPrice(Double.parseDouble(binding.priceValue.getText().toString()));
        v.setColor(binding.colorValue.getText().toString());
        v.setThumbnailImage(thumbnailImagePath);
        v.setFullSizeImage(fullImagePath);
        if (TextUtils.isEmpty(binding.dateSoldValue.getText().toString())) {
            v.setDateSold(" ");
        } else v.setDateSold(binding.dateSoldValue.getText().toString());

        if (!editing) {
            JsonUtil.saveVehicleToFile(context, v);
            Toast.makeText(context, "Saved " + v.getMake() + " successfully", Toast.LENGTH_SHORT).show();
        } else {
///
        }

    }

    private boolean cleanData() {
        if (TextUtils.isEmpty(binding.modelValue.getText().toString())) {
            binding.modelValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.makeValue.getText().toString())) {
            binding.makeValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.conditionValue.getText().toString())) {
            binding.conditionValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.engineValue.getText().toString())) {
            binding.engineValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.yearValue.getText().toString())) {
            binding.yearValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.doorsValue.getText().toString())) {
            binding.doorsValue.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(binding.priceValue.getText().toString())) {
            binding.priceValue.setError("Price required!");
            return false;
        }
        if (TextUtils.isEmpty(binding.colorValue.getText().toString())) {
            binding.colorValue.setError("Required");
            return false;
        }

        return true;

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