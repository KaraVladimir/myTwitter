package service;

import domain.Tweet;

import java.util.List;

/**
 * Created by Volodymyr_Kara on 4/3/2017.
 */
public interface TweetService {
    List<Tweet> findAll();
}
