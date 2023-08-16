package com.car.manager.util;

import static com.car.manager.Constants.FILE_NAME_COMPANIES;
import static com.car.manager.Constants.FILE_NAME_VEHICLES;

import android.content.Context;

import com.car.manager.models.Company;
import com.car.manager.models.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public final class JsonUtil {

    public static void saveVehicleToFile(Context context, Vehicle vehicle) {
        JSONObject vehicleJson = new JSONObject();
        try {
            vehicleJson.put("make", vehicle.getMake());
            vehicleJson.put("model", vehicle.getModel());
            vehicleJson.put("condition", vehicle.getCondition());
            vehicleJson.put("engineCylinders", vehicle.getEngineCylinders());
            vehicleJson.put("year", vehicle.getYear());
            vehicleJson.put("numberOfDoors", vehicle.getNumberOfDoors());
            vehicleJson.put("price", vehicle.getPrice());
            vehicleJson.put("color", vehicle.getColor());
            vehicleJson.put("thumbnailImage", vehicle.getThumbnailImage());
            vehicleJson.put("fullSizeImage", vehicle.getFullSizeImage());
            vehicleJson.put("dateSold", vehicle.getDateSold());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        saveDataToFile(context, FILE_NAME_VEHICLES, vehicleJson.toString());
    }

    public static void saveCompanyToFile(Context context, Company company) {
        JSONObject companyJson = new JSONObject();
        try {
            companyJson.put("name", company.getName());
            companyJson.put("logoImage", company.getLogoImage());
            companyJson.put("streetName", company.getStreetName());
            companyJson.put("city", company.getCity());
            companyJson.put("province", company.getProvince());
            companyJson.put("postalCode", company.getPostalCode());
            companyJson.put("carsSold", company.getCarsSold());
            companyJson.put("totalProfit", company.getTotalProfit());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        saveDataToFile(context, FILE_NAME_COMPANIES, companyJson.toString());
    }

    public static void saveDataToFile(Context context, String fileName, String data) {
        try {
            JSONArray jsonArray = readJsonArrayFromFile(context, fileName);

            if (jsonArray == null) {
                jsonArray = new JSONArray();
            }

            jsonArray.put(new JSONObject(data));

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(jsonArray.toString());
            osw.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJsonFromFile(Context context, String fileName) {
        JSONObject jsonObject = null;
        try {
            InputStream inputStream = context.openFileInput(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonText = new String(buffer, "UTF-8");
            jsonObject = new JSONObject(jsonText);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static JSONArray readJsonArrayFromFile(Context context, String fileName) {
        JSONArray jsonArray = null;
        try {
            InputStream inputStream = context.openFileInput(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonText = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(jsonText);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static List<Vehicle> readVehiclesFromFile(Context context) {
        List<Vehicle> vehicles = new ArrayList<>();
        JSONArray vehiclesJsonArray = readJsonArrayFromFile(context, FILE_NAME_VEHICLES);

        if (vehiclesJsonArray != null) {
            for (int i = 0; i < vehiclesJsonArray.length(); i++) {
                try {
                    JSONObject vehicleJson = vehiclesJsonArray.getJSONObject(i);
                    Vehicle vehicle = new Vehicle();
                    vehicle.setMake(vehicleJson.getString("make"));
                    vehicle.setModel(vehicleJson.getString("model"));
                    vehicle.setCondition(vehicleJson.getString("condition"));
                    vehicle.setEngineCylinders(vehicleJson.getString("engineCylinders"));
                    vehicle.setYear(vehicleJson.getInt("year"));
                    vehicle.setNumberOfDoors(vehicleJson.getInt("numberOfDoors"));
                    vehicle.setPrice(vehicleJson.getDouble("price"));
                    vehicle.setColor(vehicleJson.getString("color"));
                    vehicle.setThumbnailImage(vehicleJson.getString("thumbnailImage"));
                    vehicle.setFullSizeImage(vehicleJson.getString("fullSizeImage"));
                    vehicle.setDateSold(vehicleJson.optString("dateSold")); // Handle optional value

                    vehicles.add(vehicle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return vehicles;
    }


    public static Vehicle parseVehicleFromJson(JSONObject jsonObject) {
        Vehicle vehicle = new Vehicle();
        try {
            vehicle.setMake(jsonObject.getString("make"));
            vehicle.setModel(jsonObject.getString("model"));
            vehicle.setCondition(jsonObject.getString("condition"));
            vehicle.setEngineCylinders(jsonObject.getString("engineCylinders"));
            vehicle.setYear(jsonObject.getInt("year"));
            vehicle.setNumberOfDoors(jsonObject.getInt("numberOfDoors"));
            vehicle.setPrice(jsonObject.getDouble("price"));
            vehicle.setColor(jsonObject.getString("color"));
            vehicle.setThumbnailImage(jsonObject.getString("thumbnailImage"));
            vehicle.setFullSizeImage(jsonObject.getString("fullSizeImage"));
            vehicle.setDateSold(jsonObject.optString("dateSold"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vehicle;
    }


    public static List<Company> readCompaniesFromFile(Context context) {
        List<Company> companies = new ArrayList<>();
        JSONArray companiesJsonArray = readJsonArrayFromFile(context, FILE_NAME_COMPANIES);

        if (companiesJsonArray != null) {
            for (int i = 0; i < companiesJsonArray.length(); i++) {
                try {
                    JSONObject companyJson = companiesJsonArray.getJSONObject(i);
                    Company company = new Company();
                    company.setName(companyJson.getString("name"));
                    company.setLogoImage(companyJson.getString("logoImage"));
                    company.setStreetName(companyJson.getString("streetName"));
                    company.setCity(companyJson.getString("city"));
                    company.setProvince(companyJson.getString("province"));
                    company.setPostalCode(companyJson.getString("postalCode"));
                    company.setCarsSold(companyJson.getInt("carsSold"));
                    company.setTotalProfit(companyJson.getDouble("totalProfit"));

                    companies.add(company);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return companies;
    }

    public static Company parseCompanyFromJson(JSONObject jsonObject) {
        Company company = new Company();
        try {
            company.setName(jsonObject.getString("name"));
            company.setLogoImage(jsonObject.getString("logoImage"));
            company.setStreetName(jsonObject.getString("streetName"));
            company.setCity(jsonObject.getString("city"));
            company.setProvince(jsonObject.getString("province"));
            company.setPostalCode(jsonObject.getString("postalCode"));
            company.setCarsSold(jsonObject.getInt("carsSold"));
            company.setTotalProfit(jsonObject.getDouble("totalProfit"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return company;
    }

}
