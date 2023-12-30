package com.example.patienthub.patienthub;

import com.example.patienthub.patienthub.dao.PatientDao;
import com.example.patienthub.patienthub.exception.ResponseWrapper;
import com.example.patienthub.patienthub.model.Patient;
import com.example.patienthub.patienthub.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatienthubApplicationTests {
	@Mock
	private PatientDao patientDao; // Mocked dependency

	@InjectMocks
	private PatientService patientService; // The service to be tested

	@Test
	public void testGetAllPatientsSuccess() {
		Patient mockedPatient = TestPatients.createSamplePatient1();
		List<Patient> mockPatients = new ArrayList<>();
		mockPatients.add(mockedPatient);
		when(patientDao.findAll()).thenReturn(mockPatients);
		ResponseWrapper<Object> response = patientService.getAllPatients();
		assertEquals("Patients retrieved successfully", response.getMessage());
	}
	@Test
	public void testGetAllPatientsFailureNull() {
		when(patientDao.findAll()).thenReturn(Collections.emptyList());
		ResponseWrapper<Object> response = patientService.getAllPatients();
		assertEquals("No patients found", response.getMessage());
	}

	@Test
	public void testCreatePatientSuccess() {
		Patient patient = TestPatients.createSamplePatient1();
		when(patientDao.save(any(Patient.class))).thenReturn(patient);
		ResponseEntity<ResponseWrapper<String>> responseEntity = patientService.createPatient(patient);
		ResponseWrapper<String> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals("Patient added successfully", responseBody.getMessage());
	}

	@Test
	public void testCreatePatientMissingFields() {
		Patient patient = new Patient();
		ResponseEntity<ResponseWrapper<String>> responseEntity = patientService.createPatient(patient);
		assertEquals("All the fields are mandatory", responseEntity.getBody().getMessage());
		assertNull(responseEntity.getBody().getData());
	}
	@Test
	public void testUpdatePatientSuccess() {
		long patientId = 1L;
		Patient existingPatient = TestPatients.createSamplePatient1();
		existingPatient.setContactNumber(9876543210L); // Update this contact number to investigate
		when(patientDao.findById((int) patientId)).thenReturn(Optional.of(existingPatient));
		when(patientDao.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));
		ResponseEntity<ResponseWrapper<Patient>> responseEntity = patientService.updateById(patientId, TestPatients.generateUpdatedFields());
		ResponseWrapper<Patient> responseBody = responseEntity.getBody();
	}

	@Test
	public void testDeletePatientSuccess() {
		long patientId = 1L;
		Patient existingPatient = new Patient();
		existingPatient.setId(patientId);
		when(patientDao.findById(Math.toIntExact(patientId))).thenReturn(Optional.of(existingPatient));
		doNothing().when(patientDao).deleteById(Math.toIntExact(patientId));
		ResponseEntity<ResponseWrapper<String>> responseEntity = patientService.deleteById(patientId);
		assertEquals("Patients deleted successfully", responseEntity.getBody().getMessage());
	}

	@Test
	public void testDeletePatientNotFound() {
		long nonExistentPatientId = 2L;
		when(patientDao.findById(Math.toIntExact(nonExistentPatientId))).thenReturn(Optional.empty());
		ResponseEntity<ResponseWrapper<String>> responseEntity = patientService.deleteById(nonExistentPatientId);
		assertEquals("No patient found with the specified id", responseEntity.getBody().getMessage());
	}


}
