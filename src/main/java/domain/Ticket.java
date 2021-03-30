package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ticket extends Entity<Long> {

    private String username;
    private Long  flightId;
    private LocalDateTime purchaseTime;

    public Ticket(String username, Long flightId, LocalDateTime purchaseTime){
        this.username = username;
        this.flightId = flightId;
        this.purchaseTime = purchaseTime;
    }

    public String getUsername(){return username;}

    public Long getFlightId(){return flightId;}

    public LocalDateTime getPurchaseTime(){return purchaseTime;}

    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "username='" + username + '\'' +
                ", flightId=" + flightId +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}
