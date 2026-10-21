
package com.parksmart.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    private String parkingId;
    private String parkingName;
    private String customerName;
    private String vehicleNumber;
    private String status = "CONFIRMED";

    public Reservation() {}

    public Reservation(String parkingId, String parkingName,
                       String customerName, String vehicleNumber) {
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.customerName = customerName;
        this.vehicleNumber = vehicleNumber;
    }

    public String getId() { return id; }
    public String getParkingId() { return parkingId; }
    public String getParkingName() { return parkingName; }
    public String getCustomerName() { return customerName; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getStatus() { return status; }
    public void setId(String id) { this.id = id; }
    public void setParkingId(String parkingId) { this.parkingId = parkingId; }
    public void setParkingName(String parkingName) { this.parkingName = parkingName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public void setStatus(String status) { this.status = status; }
}
