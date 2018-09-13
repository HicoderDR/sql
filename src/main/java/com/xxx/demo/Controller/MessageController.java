package com.xxx.demo.Controller;

import com.xxx.demo.Common.Response;
import com.xxx.demo.Entity.Message;
import com.xxx.demo.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.xxx.demo.Common.ResultGenerator.genFailResult;
import static com.xxx.demo.Common.ResultGenerator.genSuccessResult;
import com.xxx.demo.Controller.GetAnswers;

@RestController
@CrossOrigin
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping("/getMessageRecord")
    public Response getMessageRecord(@RequestParam int user_id,@RequestParam int bot_id)
    {
        List<Message> messageList = messageService.getMessageRecord(user_id,bot_id);
        if (messageList == null){
            return genFailResult("无记录或查询失败");
        }
        else {
            return genSuccessResult(messageList);
        }
    }

    @GetMapping("/getResponse")
    public Response getResponse(@RequestParam String kb,@RequestParam int user_id ,@RequestParam String question){
        try {
            String response = GetAnswers.GetAnswers (kb,question);
            messageService.addMessage(new Message(user_id,kb,question,new Date(),0));
            messageService.addMessage(new Message(user_id,kb,response,new Date(),1));
            return genSuccessResult(response);
        }
        catch (Exception e) {
            return  genFailResult(e.getCause().getMessage());
        }
    }
}