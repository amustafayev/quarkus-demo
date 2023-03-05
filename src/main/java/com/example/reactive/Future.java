package com.example.reactive;

import io.smallrye.mutiny.tuples.Tuple2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class Future {

    public static void main(String[] args) {

        CustomerService service = new CustomerService();


        //Blocking IO
        String customerInfoBlockingResp = service.getCustomerInfoBlocking(1L);

        // Non-Blocking IO
        CompletableFuture<String> customerInfo = service.getCustomerInfo(1L);
        customerInfo.thenAccept(System.out::println);

        //Error handling

        //Not working
        try {
            service.getCustomerInfo(5L).get();
        } catch (InterruptedException | ExecutionException e) {
            // Try catch useless if service produce failure asynchronously
        }

        //Callbacks
        Consumer<String> onSuccess = s -> System.out.println("Success " + s);
        Consumer<String> onFailure = s -> System.out.println("Failure: " + s);

        //Async call, complete in future
        service.getCustomerInfo(4L, onSuccess, onFailure);

        // Sequential composition
        service.getCustomerInfo(5L)
                .thenCompose(customerInfo5 ->
                        service.getCustomerInfo(6L)
                                .thenApply(customerInfo6 ->
                                        Tuple2.of(customerInfo5,customerInfo6)));


    }

}

class CustomerService {

    public CompletableFuture<String> getCustomerInfo(Long id) {
        return CompletableFuture.completedFuture("Customer info by id: " + id); // add to event loop
    }

    public String getCustomerInfoBlocking(Long id) {
        return "Customer info by id: " + id;
    }


    public CompletableFuture<String> getCustomerInfo(long id, Consumer<String> onSuccess,
                                                     Consumer<String> onFailure) {
        // Implement the logic
        throw new RuntimeException("Not impl yet");
    }
}
