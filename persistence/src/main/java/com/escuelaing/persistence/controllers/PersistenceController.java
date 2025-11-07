package com.escuelaing.persistence.controllers;

import com.escuelaing.persistence.models.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class PersistenceController {

    // In-memory storage (usar ConcurrentHashMap para thread-safety)
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, Driver> drivers = new ConcurrentHashMap<>();
    private final Map<String, Ride> rides = new ConcurrentHashMap<>();

    // ==================== USERS ====================

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return users.get(id);
    }

    // ==================== DRIVERS ====================

    @PostMapping("/drivers")
    public Driver createDriver(@RequestBody Driver driver) {
        driver.setId(UUID.randomUUID().toString());
        driver.setBusy(false);
        drivers.put(driver.getId(), driver);
        return driver;
    }

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }

    @GetMapping("/drivers/{id}")
    public Driver getDriverById(@PathVariable String id) {
        return drivers.get(id);
    }

    @PutMapping("/drivers/{id}/busy")
    public Driver updateDriverBusyStatus(@PathVariable String id, @RequestBody Map<String, Boolean> body) {
        Driver driver = drivers.get(id);
        if (driver != null) {
            driver.setBusy(body.get("busy"));
        }
        return driver;
    }

    // ==================== RIDES ====================

    @PostMapping("/rides")
    public Ride createRide(@RequestBody Ride ride) {
        ride.setId(UUID.randomUUID().toString());
        ride.setStatus("pending");
        rides.put(ride.getId(), ride);
        return ride;
    }

    @GetMapping("/rides")
    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    @GetMapping("/rides/ids")
    public List<String> getAllRideIds() {
        return new ArrayList<>(rides.keySet());
    }

    @GetMapping("/rides/{id}")
    public Ride getRideById(@PathVariable String id) {
        return rides.get(id);
    }

    @PutMapping("/rides/{id}/status")
    public Map<String, String> updateRideStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        Ride ride = rides.get(id);
        Map<String, String> response = new HashMap<>();

        if (ride != null) {
            String newStatus = body.get("status");
            ride.setStatus(newStatus);
            response.put("message", "Ride " + id + " status updated to " + newStatus);
            response.put("rideId", id);
            response.put("status", newStatus);
        } else {
            response.put("message", "Ride not found");
        }

        return response;
    }

    // ==================== UTILITY ENDPOINTS ====================

    @DeleteMapping("/users/{id}")
    public Map<String, String> deleteUser(@PathVariable String id) {
        users.remove(id);
        return Map.of("message", "User deleted");
    }

    @DeleteMapping("/drivers/{id}")
    public Map<String, String> deleteDriver(@PathVariable String id) {
        drivers.remove(id);
        return Map.of("message", "Driver deleted");
    }

    @DeleteMapping("/rides/{id}")
    public Map<String, String> deleteRide(@PathVariable String id) {
        rides.remove(id);
        return Map.of("message", "Ride deleted");
    }
}