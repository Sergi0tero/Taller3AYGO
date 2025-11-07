package escuelaing.users;

import java.util.ArrayList;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class Users implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    // User class representing a user in the system
    private static class User{
        private String id;
        private String name;
        private String address;

        public User(String name){
            this.id = java.util.UUID.randomUUID().toString();
            this.name = name;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    private Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input,
                                                      Context context) {

//        System.out.println("Received input: " + input.getBody());
//        System.out.println("Http method: " + input.getHttpMethod());
//        System.out.println("Context: " + context.toString());
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        if(input.getHttpMethod().equals("POST")){
            addUser(input);
            responseEvent.setBody("Created user");
            responseEvent.setStatusCode(201);
        } else if (input.getHttpMethod().equals("GET")) {
            String responseBody = getAllUsers();
            responseEvent.setBody(responseBody);
            responseEvent.setStatusCode(200);
            return responseEvent;
        } else {
            responseEvent.setBody("Method not allowed");
            responseEvent.setStatusCode(405);
            return responseEvent;
        }
        return responseEvent;
    }

    ArrayList<User> users = new ArrayList<>();

    // Method to add a user
    public void addUser( APIGatewayProxyRequestEvent input){
        User inputUser = gson.fromJson(input.getBody(), User.class);
        User newUser = new User(inputUser.getName());
        newUser.setAddress(inputUser.getAddress());
        System.out.println("Adding user: " + newUser.getName() + " from " + newUser.getAddress());
        users.add(newUser);
        for (User d : users) {
            System.out.println("Users in list: " + d.getName());
        }
    }

    // Method to get all users
    public String getAllUsers(){
        return users.toString();
    }
}
