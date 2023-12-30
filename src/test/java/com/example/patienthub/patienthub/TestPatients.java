package com.example.patienthub.patienthub;

import com.example.patienthub.patienthub.model.Patient;

import java.util.HashMap;
import java.util.Map;

public class TestPatients {
    public static Patient createSamplePatient1() {
        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setAge("30");
        patient.setGender("Male");
        patient.setContactNumber(1234567890L);
        patient.setReasonForVisit("Checkup");
        return patient;
    }

    public static Map<String, String> generateUpdatedFields() {
        Map<String, String> updatedFields = new HashMap<>();
        updatedFields.put("name", "John Doe");
        updatedFields.put("age", "30");
        updatedFields.put("gender", "Male");
        updatedFields.put("contactNumber", "1234567890");
        updatedFields.put("reasonForVisit", "Checkup");
        return updatedFields;
    }
}
