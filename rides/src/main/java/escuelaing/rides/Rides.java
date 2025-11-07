// ==================== RIDES.JAVA ====================
package escuelaing.rides;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class Rides implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String PERSISTENCE_API_URL = "http://54.196.226.4:8080/api";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        try {
            switch (input.getHttpMethod()) {
                case "POST" -> {
                    String response = addRide(input);
                    responseEvent.setBody(response);
                    responseEvent.setStatusCode(201);
                }
                case "PUT" -> {
                    String response = changeRideStatus(input);
                    responseEvent.setBody(response);
                    responseEvent.setStatusCode(200);
                }
                case "GET" -> {
                    String response = getAllRidesIds();
                    responseEvent.setBody(response);
                    responseEvent.setStatusCode(200);
                }
                default -> {
                    responseEvent.setBody("Method not allowed");
                    responseEvent.setStatusCode(405);
                }
            }
        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            responseEvent.setBody("{\"error\": \"" + e.getMessage() + "\"}");
            responseEvent.setStatusCode(500);
        }

        return responseEvent;
    }

    // Method to add a ride - now calls persistence API
    public String addRide(APIGatewayProxyRequestEvent input) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PERSISTENCE_API_URL + "/rides"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(input.getBody()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to create ride: " + response.statusCode());
        }
    }

    // Method to change ride status - now calls persistence API
    public String changeRideStatus(APIGatewayProxyRequestEvent input) throws Exception {
        Map<String, String> rideMap = gson.fromJson(input.getBody(), Map.class);
        Map<String, String> queryParams = input.getQueryStringParameters();
        String rideId = rideMap.get("id");

        // Determinar el nuevo status
        String newStatus = "in progress";
        if (queryParams != null && queryParams.get("status") != null && queryParams.get("status").equals("end")) {
            newStatus = "completed";
        }

        // Crear el body para la petición
        String requestBody = gson.toJson(Map.of("status", newStatus));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PERSISTENCE_API_URL + "/rides/" + rideId + "/status"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to update ride status: " + response.statusCode());
        }
    }

    // Method to get all ride IDs - now calls persistence API
    public String getAllRidesIds() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PERSISTENCE_API_URL + "/rides/ids"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to get rides: " + response.statusCode());
        }
    }
}