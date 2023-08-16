package com.car.manager.util;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

import com.car.manager.models.Company;
import com.car.manager.models.Vehicle;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class JsonUtilTest{

    @Mock
    JSONObject mockJsonObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveVehicleToFile() {
    }

    @Test
    public void testSaveCompanyToFile() {
    }

    @Test
    public void testReadJsonFromFile() {
    }

    @Test
    public void testParseVehicleFromJson() {

        try {
            when(mockJsonObject.getString("make")).thenReturn("Toyota");
            when(mockJsonObject.getString("model")).thenReturn("Camry");
            when(mockJsonObject.getString("condition")).thenReturn("Used");

            Vehicle vehicle = JsonUtil.parseVehicleFromJson(mockJsonObject);

            assertNotNull(vehicle);
            assertEquals("Toyota", vehicle.getMake());
            assertEquals("Camry", vehicle.getModel());
            assertEquals("Used", vehicle.getCondition());
        } catch (JSONException e) {
            fail("JSONException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testParseCompanyFromJson() {
        try {
            when(mockJsonObject.getString("name")).thenReturn("XYZ Motors");
            when(mockJsonObject.getString("logoImage")).thenReturn("logo.jpg");
            Company company = JsonUtil.parseCompanyFromJson(mockJsonObject);

            assertNotNull(company);
            assertEquals("XYZ Motors", company.getName());
            assertEquals("logo.jpg", company.getLogoImage());
            System.out.println(company);
        } catch (JSONException e) {
            fail("JSONException occurred: " + e.getMessage());
        }
    }

}