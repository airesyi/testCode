package aop;

import java.lang.reflect.Proxy;

public class AnimalFactory {
    /***
     * 获取对象方法
     * @param obj
     * @return
     */
    private static Object getAnimalBase(Object obj) {
        //获取代理对象
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), new AOPHandle(obj));
    }

    private static Object getAnimalBase(Object obj, AOPMethod aopMethod) {
        //获取代理对象

        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), new AOPHandle(obj, aopMethod));
    }

    /***
     * 获取对象方法
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAnimal(Object obj) {
        return (T) getAnimalBase(obj);
    }

    /***
     * 获取对象方法
     * @param className
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAnimal(String className) {
        Object obj = null;
        try {
            obj = getAnimalBase(Class.forName(className).newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /***
     * 获取对象方法
     * @param clz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAnimal(Class clz) {
        Object obj = null;
        try {
            obj = getAnimalBase(clz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /***
     * 获取对象方法
     * @param clz
     * @return
     */
    public static <T> T getAnimal(Class clz, AOPMethod aopMethod) {
        Object obj = null;
        try {
            obj = getAnimalBase(clz.newInstance(), aopMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }
}
