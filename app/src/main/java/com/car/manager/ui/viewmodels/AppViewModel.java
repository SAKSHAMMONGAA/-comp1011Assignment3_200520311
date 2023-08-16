package com.car.manager.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.car.manager.Constants;
import com.car.manager.models.Company;
import com.car.manager.models.Vehicle;
import com.car.manager.util.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppViewModel extends AndroidViewModel {

    private final Application application;

    private final MutableLiveData<ArrayList<Vehicle>> allVehicles = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Vehicle>> availableVehicles = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Vehicle>> soldVehicles = new MutableLiveData<>();

    private final MutableLiveData<Company> companyMutableLiveData = new MutableLiveData<>();


    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final MutableLiveData<String> mText = new MutableLiveData<>();


    public AppViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mText.setValue("Hello world from section: ");

        List<Company> companies =  JsonUtil.readCompaniesFromFile(application.getApplicationContext());
        if (companies.size() >0) {
            companyMutableLiveData.setValue(companies.get(companies.size()-1));
        }



        List<Vehicle> allVehiclesFilter = JsonUtil.readVehiclesFromFile(application.getApplicationContext());

        allVehicles.setValue((ArrayList<Vehicle>) allVehiclesFilter);

        List<Vehicle> availableVehiclesFilter = allVehiclesFilter.stream()
                .filter(v -> Objects.equals(v.getCondition(), "purchase")).collect(Collectors.toList());
        availableVehicles.setValue((ArrayList<Vehicle>) availableVehiclesFilter);

        List<Vehicle> soldVehiclesFilter = allVehiclesFilter.stream()
                .filter(v -> Objects.equals(v.getCondition(), "sold")).collect(Collectors.toList());
        soldVehicles.setValue((ArrayList<Vehicle>) soldVehiclesFilter);

    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public MutableLiveData<Company> getCompanyMutableLiveData() {
        return companyMutableLiveData;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<ArrayList<Vehicle>> getAllVehicles() {
        return allVehicles;
    }

    public MutableLiveData<ArrayList<Vehicle>> getAvailableVehicles() {
        return availableVehicles;
    }

    public MutableLiveData<ArrayList<Vehicle>> getSoldVehicles() {
        return soldVehicles;
    }

    public void refresh() {
        List<Vehicle> allVehiclesFilter = JsonUtil.readVehiclesFromFile(application.getApplicationContext());

        allVehicles.setValue((ArrayList<Vehicle>) allVehiclesFilter);

        List<Vehicle> availableVehiclesFilter = allVehiclesFilter.stream()
                .filter(v -> Objects.equals(v.getCondition(), "purchase")).collect(Collectors.toList());
        availableVehicles.setValue((ArrayList<Vehicle>) availableVehiclesFilter);

        List<Vehicle> soldVehiclesFilter = allVehiclesFilter.stream()
                .filter(v -> Objects.equals(v.getCondition(), "sold")).collect(Collectors.toList());
        soldVehicles.setValue((ArrayList<Vehicle>) soldVehiclesFilter);
    }
}