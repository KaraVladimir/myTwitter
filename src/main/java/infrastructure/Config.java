package infrastructure;

/**
 * Created by main on 02.04.2017.
 */
public interface Config {
    Class getImpl(String beanName);
}
