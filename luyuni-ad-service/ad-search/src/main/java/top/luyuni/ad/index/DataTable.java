package top.luyuni.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 索引服务类的缓存
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {
    /**
     * 应用程序上下文
     */
    private static ApplicationContext applicationContext;
    /**
     * 存储数据表
     */
    public static final Map<Class, Object> dataTableMap =
            new ConcurrentHashMap<>();

    /**
     * 可以获得应用程序上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    /**
     * 设置初始化优先级
     * @return
     */
    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    /**
     * 获取所有服务类
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T of(Class<T> clazz) {

        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {
            return instance;
        }
        dataTableMap.put(clazz, bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    /**
     * 通过名字获取容器中对应的bean
     * @param beanName
     * @param <T>
     * @return
     */
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 通过类型获取容器中对应的bean
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }
}
