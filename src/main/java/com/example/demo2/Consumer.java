package com.example.demo2;

import com.example.demo2.model.Answer;
import com.example.demo2.model.Questions;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

public class Consumer {

    @Channel("questions")
    MutinyEmitter<Questions> questionEmitter;


    @Channel("answer")
    Multi<Answer> answer;


    //Emit messages
    public void send(Questions q) {
        Uni<Void> send = questionEmitter.send(q);
    }


    @Incoming("questions")
    @Outgoing("answer")
    public Answer getQuestions(Questions q){
        return answerQuestion(q);
    }

    private Answer answerQuestion(Questions q) {
        return new Answer(q.generateAnswer());
    }


    public Multi<Answer> getAnswer() {
        return answer;
    }
}
