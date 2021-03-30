package utils.events;

import domain.Flight;

public class FlightChangeEvent implements Event{
    private ChangeEventType type;
    private Flight data, oldData;

    public FlightChangeEvent(ChangeEventType type, Flight data) {
        this.type = type;
        this.data = data;
    }
    public FlightChangeEvent(ChangeEventType type, Flight data, Flight oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Flight getData() {
        return data;
    }

    public Flight getOldData() {
        return oldData;
    }
}
