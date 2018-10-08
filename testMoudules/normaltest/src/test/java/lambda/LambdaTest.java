package lambda;

import bean.Student;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * auth: shi yi
 * create date: 2018/8/7
 */
public class LambdaTest {
    @Test
    public void test1() {
        new Thread(() -> System.out.println("hello lambda")).start();
    }

    @Test
    public void test2() {
        Factory<Animal> dogFactory = Dog::new;
        Animal dog = dogFactory.create("alias", 4);
        dog.behavior();


        OtherInterface otherInterface = SubTest::test;
        Animal animal = otherInterface.test("fdadf", dog);

        Factory<Bird> birdFactory = Bird::new;
        Bird bird = birdFactory.create("smook", 3);
        bird.behavior();

    }

    @Test
    public void test3() {
        ArrayList<String> array = new ArrayList();
        array.stream();
    }
}
