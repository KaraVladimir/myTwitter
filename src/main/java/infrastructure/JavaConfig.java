package infrastructure;

import repository.TweetRepositoryImpl;
import service.TweetServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by main on 02.04.2017.
 */
public class JavaConfig implements Config {
    private final Map<String, Class> classMap = new HashMap<>();
    {
        classMap.put("tweetRepository", TweetRepositoryImpl.class);
        classMap.put("tweetService", TweetServiceImpl.class);
    }
    @Override
    public Class getImpl(String beanName) {
        return classMap.get(beanName);
    }
}
