package infrastructure;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by main on 02.04.2017.
 */
public class ApplicationContext implements Context {
    Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @Override
    public <T> T getBean(String beanName) throws IllegalAccessException, InstantiationException {
        Class beanClass = config.getImpl(beanName);
        T bean = (T) beanClass.newInstance();
        annotateMethodPostConstruct(beanName,bean);
        bean = annotateMethodBenchmark(beanName, bean);
        return bean;
    }

    private <T> T annotateMethodBenchmark(String beanName, T bean) {
        Class aClass = config.getImpl(beanName);
        Method[] methods = aClass.getMethods();
        boolean isNeedProxy = false;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Benchmark.class)) {
                if (method.getAnnotation(Benchmark.class).value()) {
                    isNeedProxy = true;
                    break;
                }
            }
        }
        if (isNeedProxy) {
            bean =  Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return null;
                        }
                    });
        }
        return bean;
    }


    private <T> void annotateMethodPostConstruct(String beanName, T bean) {
        Class aClass = config.getImpl(beanName);
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(PostConstructAnnotation.class)) {
                try {
                    method.invoke(bean, method.getParameters());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
