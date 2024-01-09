package com.dk.apiversionone.topic;

import com.dk.apiversionone.topic.models.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic,String> {
}
