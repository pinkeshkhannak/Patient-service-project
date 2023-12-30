package com.example.patienthub.patienthub.controller;

import com.example.patienthub.patienthub.exception.ResponseWrapper;
import com.example.patienthub.patienthub.model.Patient;
import com.example.patienthub.patienthub.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/get")
    public ResponseEntity<ResponseWrapper<Object>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<ResponseWrapper<List<Patient>>> findByGender(@PathVariable String gender) {
        return patientService.findByGender(gender);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseWrapper<String>> createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper<Patient>> updateById(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.updateById(id, patient.toMap());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteById(@PathVariable Long id) {
        return patientService.deleteById(id);
    }
}
