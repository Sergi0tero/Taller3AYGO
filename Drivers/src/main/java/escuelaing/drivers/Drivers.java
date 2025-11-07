package escuelaing.drivers;

import java.util.ArrayList;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class Drivers implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    // Driver class representing a driver in the system
    private static class Driver{
        private String id;
        private String name;
        private boolean busy;

        public Driver(String name){
            this.id = java.util.UUID.randomUUID().toString();
            this.name = name;
            this.busy = false;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isBusy() {
            return busy;
        }
        public void setBusy(boolean busy) {
            this.busy = busy;
        }
    }

    ArrayList<Driver> drivers = new ArrayList<>();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input,
                                                      Context context) {

//        System.out.println("Received input: " + input.getBody());
//        System.out.println("Http method: " + input.getHttpMethod());
//        System.out.println("Context: " + context.toString());
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        if(input.getHttpMethod().equals("POST")){
            addDriver(input);
            responseEvent.setBody("Created driver");
            responseEvent.setStatusCode(201);
        } else if (input.getHttpMethod().equals("GET")) {
            String responseBody = getAllDrivers();
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

    private Gson gson = new Gson();

    // Method to add a driver
    public void addDriver(APIGatewayProxyRequestEvent input){
        Driver newDriver = gson.fromJson(input.getBody(), Driver.class);
        System.out.println("Adding driver: " + newDriver.getName());
        drivers.add(new Driver(newDriver.getName()));
        for (Driver d : drivers) {
            System.out.println("Driver in list: " + d.getName());
        }
    }

    // Method to get all drivers
    public String getAllDrivers(){
        return drivers.toString();
    }
}