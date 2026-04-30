package com.example.a0644_a1;

public class Booking {
    private String bookingId;
    private String movieName;
    private String moviePoster;
    private int seats;
    private double totalPrice;
    private String dateTime;
    private long timestamp;

    public Booking() {}

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public String getMovieName() { return movieName; }
    public String getMoviePoster() { return moviePoster; }
    public int getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }
    public String getDateTime() { return dateTime; }
    public long getTimestamp() { return timestamp; }
}