
package com.parksmart.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parking")
public class Parking {
    @Id
    private String id;
    private String name;
    private String location;
    private int available;
    private int total;
    private double price;

    public Parking() {}

    public Parking(String name, String location, int available,
                   int total, double price) {
        this.name = name;
        this.location = location;
        this.available = available;
        this.total = total;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getAvailable() { return available; }
    public void setAvailable(int available) { this.available = available; }
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
