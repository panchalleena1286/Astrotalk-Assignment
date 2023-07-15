# Astrotalk-Assignment

This project is developed using Java (11), Spring Boot (2.7.13), Spring Data JPA, H2-DB, Spring Security, JWT token for authentication and Lombok for simplified Java code generation. It provides an API for managing hospital staff, patients, expenses, and billing.

<h3>Getting Started</h3>

To get started with the project, follow the steps below:

1. <h4>Clone the repository:</h4>

      <code> git clone <repository_url> </code>

2. <strong>Import the project</strong> into your preferred IDE (e.g., IntelliJ).

3. <h4>Configure the project:</h4>
      Set the server port to 8090 in the application properties or configuration file.<br>
      Access the H2-DB console using the URL: 
      <a href="http://localhost:8090/h2-console/">h2-console</a>.

4. <h4>Install dependencies:</h4>
    The project uses Maven for dependency management. Run the following command to install the dependencies:

       mvn clean install 

5. <h4>Start the application:</h4>
    Run the main class <b>`HospitalStaffManagementApplication`</b> to start the Spring Boot application.

7. <h4>Import Postman collection:</h4>
    Import the provided file <b><i>HospitalManagement APIs.postman_collection.json</i></b> into Postman.
    The collection contains predefined requests for interacting with the API endpoints.


<h1>API Endpoints</h1>
The project exposes the following API endpoints:<br><br>

POST /api/auth/signup: Adds a new staff member to the system.<br><br>
POST /api/auth/login: Authenticates a staff member and returns a JWT token for subsequent requests.<br><br>
POST /api/hospital/admit: Adds a new patient to the hospital.<br><br>
GET /api/hospital/patients: Fetches all the patients in the hospital.<br><br>
GET /api/hospital/patients/{name}: Fetches a particular patient using their name.<br><br>
PUT /api/hospital/update/patient: Updates the details of a patient.<br><br>
PUT /api/hospital/discharge/patient/{name}: Updates the status of a patient as discharged, if all payments have been settled.<br><br>
GET /api/expense/getTotalAmountPending: Fetches the total amount that needs to be paid by a patient.<br><br>
GET /api/expense/getAllBill: Fetches all the bills of a patient, including the name of the item and its price.<br><br>
Please refer to the Postman collection for detailed information on the request payloads and responses.<br><br>

<h1>Contributing</h1>
  Contributions to the project are always welcome. If you have any suggestions, improvements, or issues to report, please create a new   
  issue or submit a pull request.

<h1>Contact</h1>
  For any further information or queries, please contact <strong>Leena Panchal</strong> at <a href="mailto:leenapanchal1286@gmail.com?">leenapanchal1286@gmail.com</a>
