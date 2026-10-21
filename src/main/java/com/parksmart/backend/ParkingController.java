
package com.parksmart.backend;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:5174",
    "http://localhost:5175"
})
public class ParkingController {

    private final ParkingRepository parkingRepo;
    private final ReservationRepository reservationRepo;

    public ParkingController(ParkingRepository parkingRepo,
                             ReservationRepository reservationRepo) {
        this.parkingRepo = parkingRepo;
        this.reservationRepo = reservationRepo;
    }

    @GetMapping("/parking")
    public List<Parking> getParking() {
        if (parkingRepo.count() == 0) {
            parkingRepo.save(new Parking("City Center Parking", "Main Road", 12, 40, 30));
            parkingRepo.save(new Parking("Metro Station Parking", "Metro Station", 5, 25, 20));
            parkingRepo.save(new Parking("Shopping Mall Parking", "Central Mall", 0, 60, 40));
            parkingRepo.save(new Parking("Tech Park Parking", "IT Park", 18, 50, 25));
        }
        return parkingRepo.findAll();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Map<String, String> request) {

        String parkingId = request.get("parkingId");
        String customerName = request.get("customerName");
        String vehicleNumber = request.get("vehicleNumber");

        if (parkingId == null || customerName == null || customerName.isBlank()
                || vehicleNumber == null || vehicleNumber.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Parking, customer name and vehicle number are required");
        }

        Parking parking = parkingRepo.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Parking location not found"));

        if (parking.getAvailable() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "No parking spots available");
        }

        parking.setAvailable(parking.getAvailable() - 1);
        parkingRepo.save(parking);

        Reservation reservation = new Reservation(
            parkingId, parking.getName(), customerName.trim(),
            vehicleNumber.trim().toUpperCase());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reservationRepo.save(reservation));
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepo.findAll();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable String id) {
        Reservation reservation = reservationRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Reservation not found"));

        if ("CANCELLED".equals(reservation.getStatus())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Reservation already cancelled");
        }

        Parking parking = parkingRepo.findById(reservation.getParkingId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Parking location not found"));

        parking.setAvailable(Math.min(
            parking.getTotal(), parking.getAvailable() + 1));
        parkingRepo.save(parking);

        reservation.setStatus("CANCELLED");
        reservationRepo.save(reservation);

        return ResponseEntity.noContent().build();
    }
}
