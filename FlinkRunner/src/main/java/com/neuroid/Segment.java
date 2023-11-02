package com.neuroid;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Data;

@Data
public class Segment {

    private String target;
    private List<Event> events;
}
