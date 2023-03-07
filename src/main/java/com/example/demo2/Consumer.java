package com.example.demo2;

import com.example.demo2.model.Answer;
import com.example.demo2.model.Question;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

public class Consumer {

    @Channel("questions")
    MutinyEmitter<Question> questionEmitter;


    @Channel("answer")
    Multi<Answer> answer;


    //Emit messages
    public void send(Question q) {
        Uni<Void> send = questionEmitter.send(q);
    }


    @Incoming("questions")
    @Outgoing("answer")
    public Answer processQuestions(Question q) {
        return answerQuestion(q);
    }

    private Answer answerQuestion(Question q) {
        return new Answer(q.generateAnswer());
    }


    public Multi<Answer> getAnswer() {
        return answer;
    }


}
