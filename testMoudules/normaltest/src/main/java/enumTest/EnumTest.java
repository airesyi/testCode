package enumTest;

import org.junit.jupiter.api.Test;

public class EnumTest {
    @Test
    public void test1() {
        Color color = Color.BLANK;
        color.setIndex(3);
        color.setName("黑色");
        System.out.println(color.toString());
    }
}
