package lambda;

/**
 * auth: shi yi
 * create date: 2018/8/7
 */
public class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public void behavior() {
        System.out.println("run");
    }
}
