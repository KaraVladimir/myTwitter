package repository;


import domain.Tweet;

import java.util.List;

public interface TweetRepository {

    void save(Tweet tweet);

    List<Tweet> findAll();

}
