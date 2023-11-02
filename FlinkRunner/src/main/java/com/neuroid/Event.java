package com.neuroid;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.java.tuple.Tuple2;

@Data
@NoArgsConstructor
public class Event implements Comparable<Event>, Serializable {

    private static final long serialVersionUID = 1L;

    private String eventType;
    private String sessionId;
    private String target;
    private Long timestamp;

    // Mouse/Click specific attributes
    private Tuple2<Double, Double> coordinates;
    private boolean invalid;

    public Event(String eventType, String sessionId, String target) {
        this.eventType = eventType;
        this.sessionId = sessionId;
        this.target = target;
    }

    @Override
    public int compareTo(Event event) {
        return Long.compare(this.timestamp, event.timestamp);
    }
}
