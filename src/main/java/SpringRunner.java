import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import repository.TweetRepository;
import service.TweetService;

/**
 * Created by Volodymyr_Kara on 4/3/2017.
 */
public class SpringRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        TweetRepository repository = context.getBean("repo", TweetRepository.class);
        ConfigurableApplicationContext serviceContext = new ClassPathXmlApplicationContext("service.xml", "spring-context.xml");
        TweetService tweetService = serviceContext.getBean("service",TweetService.class);
//        repo.findAll();
    }
}
