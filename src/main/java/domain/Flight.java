package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Flight extends Entity<Long> {

    private String from;
    private String to;
    private LocalDate departureLong;
    private LocalDate landingTime;
    private int seats;
    private int seatsAvailable;

    public Flight(){}

    public Flight(String from, String to, LocalDate departureLong, LocalDate landingTime, int seats){
        this.from = from;
        this.to = to;
        this.departureLong = departureLong;
        this.landingTime = landingTime;
        this.seats = seats;
        this.seatsAvailable = seats;

    }

    public String getFrom(){ return from; }

    public String getTo(){ return to; }

    public LocalDate getDepartureLong(){ return departureLong; }

    public LocalDate getLandingTime(){ return landingTime; }

    public int getSeats (){ return seats; }

    public int getSeatsAvailable(){ return seatsAvailable; }

    public void setSeatsAvailable(int seats) {
        this.seatsAvailable = seats;
    }

    public String getIdString() { return super.getId().toString(); }

    public String getFromString() { return "" + from; }

    public String getToString() { return "" + to; }

    public String getDepartureLongString() { return "" + departureLong; }

    public String getLandingTimeString() { return "" + landingTime; }

    public String getSeatsString() { return "" + seats; }

    public String getSeatsAvailableString() { return "" + seatsAvailable; }

}
