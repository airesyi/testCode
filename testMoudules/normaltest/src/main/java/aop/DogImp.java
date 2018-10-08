package aop;

public class DogImp implements AnimalInterface {
    private String name = "小黑";

    public DogImp() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void say() {
        System.out.println("小狗:汪汪汪汪.....");
    }

    public void getProperty() {
        System.out.println("小狗是陆地动物,但是会游泳哦");
    }
}
