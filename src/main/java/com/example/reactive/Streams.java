package com.example.reactive;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.time.Duration;

public class Streams {

    public static void main(String[] args) {

        Multi<String> stream = Multi.createFrom().items("a", "b", "c", "d");
        Multi<String> stream1 = Multi.createFrom().items("a", "b", "c");
        Multi<String> stream2 = Multi.createFrom().items("D", "E", "F");

        //Subscribe and operate
        stream
                .subscribe().with(
                        item -> System.out.println("Received an item: " + item),
                        failure -> System.out.println("Oh no! Received a failure: " + failure.getMessage()),
                        () -> System.out.println("Received the completion signal")
                );

        // Merge
        Multi.createBy().merging().streams(stream1, stream2)
                .subscribe().with(
                        item -> System.out.println("Received a square or circle: " + item),
                        failure -> System.out.println("Oh no! Received a failure: " + failure.getMessage()),
                        () -> System.out.println("Received the completion signal")
                );

        //Buffering
        Multi.createFrom().ticks().every(Duration.ofMillis(10))
//                .onOverflow().buffer(250)
                .onOverflow().drop()
                .emitOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(x -> canOnlyConsumeOneItemPerSecond(x))
                .subscribe().with(
                        item -> System.out.println("Got item: " + item),
                        failure -> System.out.println("Got failure: " + failure)
                );

        //Backpressure
        Multi.createFrom().ticks().every(Duration.ofMillis(10))
                .emitOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(x -> canOnlyConsumeOneItemPerSecond(x))
                .subscribe().with(
                        item -> System.out.println("Got item: " + item),
                        failure -> System.out.println("Got failure: " + failure)
                );


    }


    private static long canOnlyConsumeOneItemPerSecond(long x) {
        try {
            Thread.sleep(1000);
            return x;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return x;
        }
    }
}
