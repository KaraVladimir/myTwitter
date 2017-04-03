package infrastructure;

import repository.TweetRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by main on 02.04.2017.
 */
public class JavaConfig implements Config {
    private final Map<String, Class> classMap = new HashMap<>();
    {
        classMap.put("repo", TweetRepositoryImpl.class);
    }
    @Override
    public Class getImpl(String beanName) {
        return classMap.get(beanName);
    }
}
