package infrastructure;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by main on 02.04.2017.
 */
public class ApplicationContext implements Context {
    Config config;
    Map<String, Object> beanMap = new HashMap();

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @Override
    public <T> T getBean(String beanName) throws IllegalAccessException, InstantiationException {
        T bean = (T) beanMap.get(beanName);
        if (bean != null) {
            return bean;
        }

        Class beanClass = config.getImpl(beanName);
        if (beanClass == null) {
            throw new RuntimeException("Bean def not found");
        }

        bean = beanBuilder(beanClass);
        return bean;
    }

    private <T> T beanBuilder(Class beanClass) throws InstantiationException, IllegalAccessException {
        T bean = (T) beanClass.newInstance();
        annotateMethodPostConstruct(beanClass,bean);
        bean = annotateMethodBenchmark(beanClass, bean);
        return bean;
    }

    private <T> T annotateMethodBenchmark(Class aClass, T bean) {
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
            return createProxy(bean);
        }
        return bean;
    }

    private <T> T createProxy(T bean) {
        T proxy = (T) Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy1, method, args) -> {
                    Object o;
                    if (bean.getClass().getMethod(method.getName()).isAnnotationPresent(Benchmark.class)) {
                        System.out.println("Benchmarked");
                        long startTime = System.nanoTime();
                        o = method.invoke(bean, method.getParameters());
                        System.out.println(System.nanoTime()-startTime);
                    }else {
                        o = method.invoke(bean, method.getParameters());
                    }
                    return o;
                });
        return proxy;
    }


    private <T> void annotateMethodPostConstruct(Class aClass, T bean) {
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
