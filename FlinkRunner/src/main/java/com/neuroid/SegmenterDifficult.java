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
public class SegmenterDifficult extends KeyedProcessFunction<String, Event, Segment> {

    @Override
    public void open(Configuration parameters) {

    }

    @Override
    public void processElement(Event value, Context ctx, Collector<Segment> out) throws Exception {

        log.info("Processing key '{}': {}", ctx.getCurrentKey(), value);


    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Segment> out) throws Exception {
        log.info("Timer fired for session '{}'", ctx.getCurrentKey());


    }

}
