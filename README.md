# Authentication-Module
 
When loading up the front end, you might need to change the allowedOrigins URL in src/main/java/AuthenticationModule/Authentication/config/WebConfig.java to the hosted url of the frontend application.

Upon loading a default Admin user will be created:

Username: admin@admin.com
Password: admin123

No additional admin users can be created.

/api/v1/auth/create

Note: only a user with admin role can access this endpoint.

When logging in with an Admin user, a token and a redirect url will be sent. The frontend will automatically be redirected to the user creation page. The admin can create a new user with a valid email address and a minimum password length of 8 characters. All information is validated from the backend. 

This requires a body of 4 fields as seen below. 

{
    "firstname":"Chris",
    "lastname":"Martin",
    "email":"chris@martin.com",
    "password":"password123"
}


/api/v1/auth/authenticate

This endpoint can be accessed by anyone. If an admin logs in, they will be redirected to the register.html page via a response sent by the server. If a user logs in they will be redirected to user.html page (this has no functionality).

Java JDK 17 - Spring Boot 3.15.0 - H2 database
