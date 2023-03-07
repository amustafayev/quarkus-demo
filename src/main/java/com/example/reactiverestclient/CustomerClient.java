package com.example.reactiverestclient;

import com.example.reactiverestclient.models.Customer;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "client.customer")
public interface CustomerClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Customer> getCustomerInfo(@PathParam("id") Long id);

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    Uni<List<Customer>> helloUni(); // By Using Uni, we use client in reactive manner => nonblocking



    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Customer getCustomerInfoBlocking(@PathParam("id") Long id); // returning result directly block the thread

}
