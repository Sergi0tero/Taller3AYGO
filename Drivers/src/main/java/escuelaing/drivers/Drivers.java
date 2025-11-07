package escuelaing.drivers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class Drivers implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String PERSISTENCE_API_URL = "http://54.196.226.4:8080/api";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        try {
            if (input.getHttpMethod().equals("POST")) {
                String response = addDriver(input);
                responseEvent.setBody(response);
                responseEvent.setStatusCode(201);
            } else if (input.getHttpMethod().equals("GET")) {
                String response = getAllDrivers();
                responseEvent.setBody(response);
                responseEvent.setStatusCode(200);
            } else {
                responseEvent.setBody("Method not allowed");
                responseEvent.setStatusCode(405);
            }
        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            responseEvent.setBody("{\"error\": \"" + e.getMessage() + "\"}");
            responseEvent.setStatusCode(500);
        }

        return responseEvent;
    }

    // Method to add a driver - now calls persistence API
    public String addDriver(APIGatewayProxyRequestEvent input) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PERSISTENCE_API_URL + "/drivers"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(input.getBody()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to create driver: " + response.statusCode());
        }
    }

    // Method to get all drivers - now calls persistence API
    public String getAllDrivers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PERSISTENCE_API_URL + "/drivers"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to get drivers: " + response.statusCode());
        }
    }
}
