package lambda;

/**
 * auth: shi yi
 * create date: 2018/8/7
 */
public interface Factory<T extends Animal> {
    T create(String name, int age);
}
