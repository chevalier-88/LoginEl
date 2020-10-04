package com.gmail.vladimir.chevalier.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.vladimir.chevalier.web.dto.EventType;
import com.gmail.vladimir.chevalier.web.dto.ObjectType;
import com.gmail.vladimir.chevalier.web.entities.Message;
import com.gmail.vladimir.chevalier.web.entities.View;
import com.gmail.vladimir.chevalier.web.repo.MessageRepo;
import com.gmail.vladimir.chevalier.web.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> wsSender;


    @Autowired
    public MessageController(MessageRepo messageRepo, WsSender wsSender) {
        this.messageRepo = messageRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.Advanced.class);
    }

    @GetMapping()
    public List<Message> getMessagesList() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(View.Basic.class)
    public Message getMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping()
    public Message createMessage(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        Message updatedMessage = messageRepo.save(message);
        wsSender.accept(EventType.CREATE, updatedMessage);
        return updatedMessage;
    }

    @PutMapping("{id}")
    public Message updateMessage(@PathVariable("id") Message messageFromDB, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDB, "id");
        Message updatedMessage = messageRepo.save(messageFromDB);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void removeMessage(@PathVariable("id") Message message) {
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

}
