package service;

import domain.Tweet;
import repository.TweetRepository;

import java.util.List;

/**
 * Created by Volodymyr_Kara on 4/3/2017.
 */
public class TweetServiceImpl implements TweetService {

    TweetRepository tweetRepository;

    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }
}
