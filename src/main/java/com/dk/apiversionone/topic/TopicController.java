package com.dk.apiversionone.topic;


import com.dk.apiversionone.topic.models.TopicDummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    TopicDummyService topicDummyService;

    @RequestMapping("/topics")
    public List<TopicDummy> getAllTopics(){
        return  topicDummyService.getAllTopics();
    }

    @RequestMapping("/topics/{id}")
    public TopicDummy getById(@PathVariable String id){
        return  topicDummyService.getTopic(id);
    }

    @RequestMapping(method = RequestMethod.POST ,path = "/topics/add")
    public void addTopic(@RequestBody TopicDummy topic){
        topicDummyService.addTopic(topic);
    }

    @RequestMapping(method = RequestMethod.PUT ,path = "/topics/{id}")
    public void updateTopic(@RequestBody TopicDummy topic,@PathVariable String id){
        topicDummyService.updateTopic(id,topic);
    }

    @RequestMapping(method = RequestMethod.DELETE ,path = "/topics/{id}")
    public void deleteTopic(@PathVariable String id){
        topicDummyService.deleteTopic(id);
    }

}
