package com.neuroid


import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.util.ProcessFunctionTestHarnesses
import spock.lang.Specification

class SegmenterSpec extends Specification implements Serializable {

    def "Can create a segment"() {
        given:
        def segmenter = new SegmenterSolution()
        def harness = ProcessFunctionTestHarnesses.forKeyedProcessFunction(segmenter, e -> e.sessionId, TypeInformation.of(Event))

        when: 'a session is created'
        process(harness, 0L, 'CREATE_SESSION', '1')
        process(harness, 0L, 'MOUSE_MOVE', '1')

        and: 'a target is focused with key events'
        process(harness, 1L, 'FOCUS', '1', 'first-name')
        process(harness, 2L, 'KEY_DOWN', '1', 'first-name')
        process(harness, 3L, 'KEY_DOWN', '1', 'first-name')
        process(harness, 4L, 'KEY_DOWN', '1', 'first-name')

        and: 'a new target is focused'
        process(harness, 5L, 'FOCUS', '1', 'last-name')

        then: 'two segments are emitted'
        harness.extractOutputValues().size() == 2
        harness.extractOutputValues()[0].events.size() == 2
        harness.extractOutputValues()[1].events.size() == 4
        harness.extractOutputValues()[1].events.stream().allMatch(e -> 'first-name' == e.target)
    }

    def "Can close a segment after a timeout"() {
        given:
        def segmenter = new SegmenterSolution()
        def harness = ProcessFunctionTestHarnesses.forKeyedProcessFunction(segmenter, e -> e.sessionId, TypeInformation.of(Event))

        when: 'a session is created'
        process(harness, 0L, 'CREATE_SESSION', '1')
        process(harness, 0L, 'MOUSE_MOVE', '1')

        and: 'a target is focused with key events'
        process(harness, 1L, 'FOCUS', '1', 'first-name')
        process(harness, 2L, 'KEY_DOWN', '1', 'first-name')
        process(harness, 3L, 'KEY_DOWN', '1', 'first-name')
        process(harness, 4L, 'KEY_DOWN', '1', 'first-name')

        and: 'we fast forward time by 35 seconds'
        harness.setProcessingTime(35_000L)

        then: 'two a segments are emitted'
        harness.extractOutputValues().size() == 2
        harness.extractOutputValues()[0].events.size() == 2
        harness.extractOutputValues()[1].events.size() == 4
        harness.extractOutputValues()[1].events.stream().allMatch(e -> 'first-name' == e.target)
    }

    def process(harness, timestamp, eventType, sessionId, target = null) {
        def event = new Event(eventType: eventType, sessionId: sessionId, target: target, timestamp: timestamp)
        harness.processElement(event, timestamp)
    }
}