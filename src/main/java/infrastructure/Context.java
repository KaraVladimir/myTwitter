package infrastructure;

/**
 * Created by main on 02.04.2017.
 */
public interface Context {
    <T> T getBean(String beanName) throws IllegalAccessException, InstantiationException;
}
