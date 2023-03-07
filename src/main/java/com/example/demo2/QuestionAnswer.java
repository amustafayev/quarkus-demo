package com.example.demo2;


import com.example.demo2.model.Answer;
import com.example.demo2.model.Questions;
import io.smallrye.mutiny.Multi;
import org.jboss.logging.annotations.Pos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/questions")
public class QuestionAnswer {

    @Inject
    Consumer consumer;


    @POST
    @Path("/ask")
    public void sendQuestions(Questions questions) {
        consumer.send(questions);
    }

    @GET
    @Path("/answer")
    public Multi<Answer> getAnswer() {
        return consumer.getAnswer();
    }


}