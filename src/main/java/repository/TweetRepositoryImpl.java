package repository;


import domain.Tweet;
import infrastructure.Benchmark;
import infrastructure.PostConstructAnnotation;

import java.util.ArrayList;
import java.util.List;

public class TweetRepositoryImpl implements TweetRepository {

    private final List<Tweet> tweetRepo = new ArrayList<>();

    @Override
    public void save(Tweet tweet) {
        tweetRepo.add(tweet);
    }

    @Benchmark(value = true)
    @Override
    public List<Tweet> findAll() {
        System.out.println("findAll");
        return new ArrayList<>(tweetRepo);
    }

    @PostConstructAnnotation
    public void init(){
        System.out.println("postConstruct");
    }

}
