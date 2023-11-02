package com.neuroid;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

@Slf4j
public class SegmenterSimple extends KeyedProcessFunction<String, Event, Segment> {

    /**
     * The number of milliseconds to close the session after if no additional events are received.
     */
    private static final long SESSION_CLOSE_MS = 30_000;

    private transient ListState<Event> eventListState;
    private transient ValueState<Event> lastEvent;

    private transient ValueState<Long> timerState;

    @Override
    public void open(Configuration parameters) {
        var simpleSegmentEventsListDescriptor =
            new ListStateDescriptor<>("segment-events", TypeInformation.of(Event.class));
        eventListState = getRuntimeContext().getListState(simpleSegmentEventsListDescriptor);

        var lastEventDescriptor =
            new ValueStateDescriptor<>("last-event", TypeInformation.of(Event.class));
        lastEvent = getRuntimeContext().getState(lastEventDescriptor);

        var timerDescriptor = new ValueStateDescriptor<>("timer", Long.class);
        timerState = getRuntimeContext().getState(timerDescriptor);
    }

    @Override
    public void processElement(Event value, Context ctx, Collector<Segment> out) throws Exception {

        log.info("Processing key '{}': {}", ctx.getCurrentKey(), value);

        // Reset the timer to close this session
        updateTimer(ctx);

        var lastEventValue = lastEvent.value();
        if (lastEventValue == null) {
            
        }

        if ("FOCUS".equals(value.getEventType()) && !value.getTarget().equals(lastEventValue.getTarget())) {


        } else {

        }


    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Segment> out) throws Exception {

    }

    private void updateTimer(Context ctx) throws IOException {

    }
}
