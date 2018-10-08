package lambda;

/**
 * auth: shi yi
 * create date: 2018/8/7
 */
public interface OtherInterface<T extends Animal> {
    T test(String name, Animal a);
}
