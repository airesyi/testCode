package aop;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class AOPTest {
    @Test
    public void Test1() {
        AnimalInterface dog = AnimalFactory.getAnimal(DogImp.class);
        dog.say();
        System.out.println("我的名字是" + dog.getName());
        dog.setName("二狗子");
        System.out.println("我的名字是" + dog.getName());
    }

    @Test
    public void Test2() {
        AnimalInterface dog = AnimalFactory.getAnimal(DogImp.class, new AOPMethod() {
            // 这里写方法执行前的AOP切入方法
            public void before(Object proxy, Method method, Object[] args) {
                System.out.println("proxy:" + proxy.getClass());
                System.err.println("我在" + method.getName() + "方法执行前执行");
            }

            // 这里系方法执行后的AOP切入方法
            public void after(Object proxy, Method method, Object[] args) {
                System.out.println("proxy:" + proxy.getClass());
                System.err.println("我在 " + method.getName() + "方法执行后执行");

            }
        });
        dog.say();
        String name1 = "我的名字是" + dog.getName();
        System.out.println(name1);
        dog.setName("二狗子");
        String name2 = "我的名字是" + dog.getName();
        System.out.println(name2);
    }
}
