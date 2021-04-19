package com.trilogyed.notequeueconsumer;

import com.trilogyed.notequeueconsumer.feign.NoteServiceClient;
import com.trilogyed.notequeueconsumer.models.Note;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MessageListener {

    NoteServiceClient noteServiceClient;
    @Autowired
    public MessageListener(NoteServiceClient noteServiceClient) {
        this.noteServiceClient = noteServiceClient;
    }

    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void createNote( Note note){
        System.out.println("got a note"+ note);
        if(note.getId()> 0){
            noteServiceClient.updateNote(note.getId(), note);
        }else{
             noteServiceClient.createNote(note);
        }

    }
//    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
//    public void updateNote(int id, Note note){
//        noteServiceClient.updateNote(id, note);
//    }

//    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
//    public void receiveMessage(NoteListEntry msg){
//        System.out.println(msg.toString());
//    }
}
