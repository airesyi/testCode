package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * auth: shi yi
 * create date: 2018/11/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetClass implements Serializable {
    private int id;
    private String name;
    private String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetClass)) return false;

        SetClass setClass = (SetClass) o;

        return getId() == setClass.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
