package com.example.patienthub.patienthub.service;

import com.example.patienthub.patienthub.dao.PatientDao;
import com.example.patienthub.patienthub.exception.ResponseWrapper;
import com.example.patienthub.patienthub.model.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@EnableCaching
public class PatientService {

    @Autowired
    private PatientDao patientDao;
    @Cacheable(value = "patients")
    public ResponseWrapper<Object> getAllPatients() {
        try {
            List<Patient> patients = patientDao.findAll();
            if (!patients.isEmpty()) {
                return new ResponseWrapper<>(HttpStatus.OK, "Patients retrieved successfully", patients);
            } else {
                return new ResponseWrapper<>(HttpStatus.NOT_FOUND, "No patients found", null);
            }
        } catch (Exception e) {
            return new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve patients: " + e.getMessage(), null);
        }
    }

    @Cacheable(value = "patientsByGender", key = "#gender")
    public ResponseEntity<ResponseWrapper<List<Patient>>> findByGender(String gender) {
        try {
            List<Patient> patients = patientDao.findByGender(gender);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK, "Patients found with gender: " + gender, patients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST, "Error fetching patients by gender: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<ResponseWrapper<String>> createPatient(@RequestBody Patient patient) {
        try {
            if (patient.getName() == null || patient.getAge() == null || patient.getGender() == null || patient.getContactNumber() == null ||patient.getReasonForVisit() == null) {
                return ResponseEntity.badRequest()
                        .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST, "All the fields are mandatory", null));
            }

            patientDao.save(patient);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK, "Patient added successfully",""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST, "Patient not created due to this error: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<ResponseWrapper<Patient>> updateById(Long id, @Valid @RequestBody Map<String, String> updatedFields) {
        try {
            Optional<Patient> optionalPatient = patientDao.findById(Math.toIntExact(id));
            if (optionalPatient.isPresent()) {
                Patient patient = optionalPatient.get();
                updateFields(patient, updatedFields);
                if (patient.getName() == null || patient.getAge() == null || patient.getGender() == null || patient.getContactNumber() == null ||patient.getReasonForVisit() == null) {
                    return ResponseEntity.badRequest()
                            .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST, "All the fields are mandatory", null));
                }
                patientDao.save(patient);
                return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK, "Patients updated successfully", patient));
            } else {
                return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.NOT_FOUND, "No patient found with the specified id", null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR, "Patient not updated due to this error: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<ResponseWrapper<String>> deleteById(Long id) {
        try {
            Optional<Patient> optionalPatient = patientDao.findById(Math.toIntExact(id));
            if (optionalPatient.isPresent()) {
                patientDao.deleteById(Math.toIntExact(id));
                return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK, "Patients deleted successfully", ""));
            } else {
                return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.NOT_FOUND, "No patient found with the specified id", null));
            }
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND, "No patient found with the specified id", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR, "Patient not deleted due to this error: " + e.getMessage(), null));
        }
    }


    private void updateFields(Patient patient, Map<String, String> updatedFields) {
        for (Map.Entry<String, String> entry : updatedFields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            switch (fieldName) {
                case "name":
                    patient.setName(fieldValue);
                    break;
                case "age":
                    patient.setAge(fieldValue);
                    break;
                case "gender":
                    patient.setGender(fieldValue);
                    break;
                case "reasonForVisit":
                    patient.setReasonForVisit(fieldValue);
                    break;
                default:
                    break;
            }
        }
    }

}
