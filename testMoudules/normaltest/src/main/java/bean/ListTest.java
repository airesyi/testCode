package bean;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    private String name;
    private List<ListTest> list;

    public ListTest() {
        this.list = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListTest> getList() {
        return list;
    }

    public void setList(List<ListTest> list) {
        this.list = list;
    }
}
