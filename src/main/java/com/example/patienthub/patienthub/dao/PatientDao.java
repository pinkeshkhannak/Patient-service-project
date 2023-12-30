package com.example.patienthub.patienthub.dao;

import com.example.patienthub.patienthub.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//Patient here is className which maps with the table
//Integer primary key
@Repository
public interface PatientDao extends JpaRepository<Patient, Integer> {
    List<Patient> findByGender(String catagory);
}
