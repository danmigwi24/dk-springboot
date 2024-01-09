package com.dk.apiversionone.topic;

import com.dk.apiversionone.topic.models.TopicDummy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicDummyService {

    private final List<TopicDummy> topics = new ArrayList<>(Arrays.asList(
            new TopicDummy("spring","Spring Framework", "Spring Framework")
    ));

    public  List<TopicDummy> getAllTopics(){
        return  topics;
    }

    public TopicDummy getTopic(String id){
        return  topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public void addTopic(TopicDummy topic){
        topics.add(topic);
    }

    public  void  updateTopic(String id,TopicDummy topic){
        for (int i=0; i<topics.size();i++){
            TopicDummy t =topics.get(i);
            if (t.getId().equals(id)){
                topics.set(i,topic);
                return;
            }
        }
    }

    public void  deleteTopic(String id){
        topics.removeIf(t-> t.getId().equals(id));
    }
}
