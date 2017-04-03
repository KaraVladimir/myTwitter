import infrastructure.ApplicationContext;
import infrastructure.Config;
import infrastructure.Context;
import infrastructure.JavaConfig;
import repository.TweetRepository;

/**
 * Created by main on 02.04.2017.
 */
public class Runner {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Context context = new ApplicationContext(new JavaConfig());

        TweetRepository repo = context.getBean("repo");
        System.out.println(repo);
    }
}
