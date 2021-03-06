package cn.ucaner.spring.tiny.anntotion.handle;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ucaner.spring.tiny.beans.config.BeanDefinition;
import cn.ucaner.spring.tiny.beans.config.DefaultBeanDefinition;
import cn.ucaner.spring.tiny.context.AbstractApplicationContext;
import cn.ucaner.spring.tiny.exception.AnnotationBenaConfigurationErrorException;
import cn.ucaner.spring.tiny.ioc.annotation.Autowired;
import cn.ucaner.spring.tiny.ioc.annotation.Component;

/**
* @Package：cn.ucaner.spring.tiny.anntotion.handle   
* @ClassName：ComponentHandle   
* @Description：   <p> ComponentHandle </p>
* @Author： - chenwentao   
* @Modify By：   
* @ModifyTime：  2018年4月27日
* @Modify marker：   
* @version    V1.0
 */
public class ComponentHandle {

	
	/**
	 * 获取日志记录器
	 */
    private static Logger logger = LoggerFactory.getLogger(ComponentHandle.class);

    /**
     * BeanDefinition Resource定位、载入和注册
     */
    protected static Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    
    /**
     * @Description: @Componnet注解的类注入到IOC容器 返回beanDefinitions 而不是bean
     * @param ComponentClass 被@Componnet注解了的类
     * @return Map<String, BeanDefinition>
     * @throws Exception Map<String,BeanDefinition>
     * @Autor: wubin - binwu@pcitech.com
     */
    public static Map<String, BeanDefinition> getBeanDefinitionMap(List<Class<?>> ComponentClass) throws Exception {

        ComponentClass.forEach((beanClass) -> {
            // 处理class对象为空的情况
            if (beanClass == null) {
                try {
                    throw new AnnotationBenaConfigurationErrorException("在解析component注解的过程中,传入了空的Class对象!");
                } catch (AnnotationBenaConfigurationErrorException e) {
                    logger.error("在解析component注解的过程中,传入了空的Class对象!");
                }
            }
            Component component = beanClass.getAnnotation(Component.class);
            if (component != null) {
                BeanDefinition beanDefinition = new DefaultBeanDefinition();
                String beanName = null;
                try {
                	//获取beanName     类全名 eg:cn.ucaner.spring.tiny.anntotion.handle.ComponentHandle   -> ComponentHandle
                    beanName = beanClass.getName().split("\\.")[beanClass.getName().split("\\.").length - 1];
                    if (beanClass != null && beanName != null) {
                    	
                    	//
                        beanDefinition.setBeanClass(beanClass);
                        // 在获取了bean的名字和初始实例之后，我还需要获得bean的依赖
                        Method[] methods = beanClass.getDeclaredMethods();
                        // 如果没有
                        for (Method method : methods) {
                            Autowired autowured = method.getAnnotation(Autowired.class);
                            if (autowured != null) {
                                // 添加依赖的bean的名字
                                beanDefinition.addDepend(autowured.value());
                            }
                        }
                    } else {
                        throw new InstantiationException("注解解析异常");
                    }
                } catch (InstantiationException e) {
                    logger.error("无法通过空的构造方法获取bean实例！");
                }
                beanDefinitions.put(beanName, beanDefinition);
            }
        });
        return beanDefinitions;
    }
    
    public static void main(String[] args) {
    	String name = AbstractApplicationContext.class.getName();
    	System.out.println(name);//cn.ucaner.spring.tiny.context.AbstractApplicationContext
	}

}
