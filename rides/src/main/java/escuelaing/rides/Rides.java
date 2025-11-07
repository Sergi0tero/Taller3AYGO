package escuelaing.rides;

import java.util.ArrayList;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class Rides implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    // Ride class representing a ride in the system
    private class Ride{
        private String id;
        private String origin;
        private String end;
        private String driver;
        private String user;
        private String status;

        public Ride(String origin, String end, String driver, String user){
            this.id = java.util.UUID.randomUUID().toString();
            this.origin = origin;
            this.end = end;
            this.driver = driver;
            this.user = user;
            this.status = "pending";
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getOrigin() {
            return origin;
        }
        public void setOrigin(String origin) {
            this.origin = origin;
        }
        public String getEnd() {
            return end;
        }
        public void setEnd(String end) {
            this.end = end;
        }
        public String getDriver() {
            return driver;
        }
        public void setDriver(String driver) {
            this.driver = driver;
        }
        public String getUser() {
            return user;
        }
        public void setUser(String user) {
            this.user = user;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }

    ArrayList<Ride> rides = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input,
                                                      Context context) {

//        System.out.println("Received input: " + input.getBody());
//        System.out.println("Http method: " + input.getHttpMethod());
//        System.out.println("Context: " + context.toString());
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        switch (input.getHttpMethod()) {
            case "POST" -> {
                addRide(input);
                responseEvent.setBody("Created ride");
                responseEvent.setStatusCode(201);
            }
            case "PUT" -> {
                String responseBody = changeRideStatus(input);
                responseEvent.setBody(responseBody);
                responseEvent.setStatusCode(200);
                return responseEvent;
            }
            case "GET" -> {
                String responseBody = getAllRidesIds();
                responseEvent.setBody(responseBody);
                responseEvent.setStatusCode(200);
                return responseEvent;
            }
            default -> {
                responseEvent.setBody("Method not allowed");
                responseEvent.setStatusCode(405);
                return responseEvent;
            }
        }
        return responseEvent;
    }


    // Method to add a ride
    public void addRide(APIGatewayProxyRequestEvent input){
        Ride inputRide = gson.fromJson(input.getBody(), Ride.class);
        Ride newRide = new Ride(inputRide.getOrigin(), inputRide.getEnd(), inputRide.getDriver(), inputRide.getUser());
        System.out.println("Adding ride from " + newRide.getOrigin() + " to " + newRide.getEnd() + " for user " + newRide.getUser() + " with driver " + newRide.getDriver());
        rides.add(newRide);
        for (Ride ride : rides){
            System.out.println("Rides in list " + ride.getId());
        }
    }

    // Method to start a ride
    public String changeRideStatus(APIGatewayProxyRequestEvent input){
        Map<String, String> rideMap = gson.fromJson(input.getBody(), Map.class);
        Map<String, String> queryParams = input.getQueryStringParameters();
        String rideId = rideMap.get("id");
        for(Ride ride : rides){
            if(ride.getId().equals(rideId)){
                System.out.println("nuevo estado " + ride.getStatus());
                String newStatus = "in progress";
                String  message = "Ride " + rideId + " started";
                if (queryParams.get("status").equals("end")) {
                    newStatus = "completed";
                    message = "Ride " + rideId + " completed";
                }
                ride.setStatus(newStatus);
                System.out.println("nuevo estado " + ride.getStatus());
                return message;
            }
        }
        return "Ride not found";
    }

    // Method to get all rides
    public String getAllRidesIds(){
        ArrayList<String> ids = new ArrayList<>();
        for (Ride ride : rides){
            ids.add(ride.getId());
        }
        return ids.toString();
    }
}
