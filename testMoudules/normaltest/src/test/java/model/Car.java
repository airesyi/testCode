package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * auth: shi yi
 * create date: 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Cloneable , Serializable {
    private String mani;
    private int size;
    private int price;

    public String getMani() {
        return mani;
    }

    public void setMani(String mani) {
        this.mani = mani;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
