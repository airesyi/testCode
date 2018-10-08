package lambda;

/**
 * auth: shi yi
 * create date: 2018/8/7
 */
public class SubTest extends Animal {
    private static Animal animal;

    public SubTest(String name, int age) {
        super(name, age);
    }

    public static Animal test(String name, Animal animal) {
        System.out.println("123");
        SubTest.animal = animal;
        animal.behavior();

        return null;
    }

    public void aaa() {
        System.out.println("aaa");
    }
}
