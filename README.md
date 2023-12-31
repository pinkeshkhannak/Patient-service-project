The Patient Service Project is a simple Spring Boot application that manages patient data, utilizes PostgreSQL as its primary database, incorporates Redis for caching, and includes automated CI/CD pipelines using Docker and hosted it on render.com This project offers basic CRUD (Create, Read, Update, Delete) functionality for patient information.

API Endpoints
Get All Patients
Method: GET
URL: https://patient-service-cmuz.onrender.com/patients/get
Description: Retrieves all patient records.
Response: JSON array containing patient information.

Create Patient 
Method: POST
URL: https://patient-service-cmuz.onrender.com/patients/add
Description: Creates a new patient record.
Request Body: JSON object with patient details (e.g., name, age, address).
Response: JSON response indicating success or failure.

Update Patient by ID
Method: POST
URL: https://patient-service-cmuz.onrender.com/patients/update/<id>
Description: Updates an existing patient record specified by the ID.
Request Body: JSON object with updated patient details.
Response: JSON response indicating success or failure.

Delete Patient
Method: POST
URL: https://patient-service-cmuz.onrender.com/patients/delete/<id>
Description: Deletes a patient record specified by the ID.
Response: JSON response indicating success or failure.
