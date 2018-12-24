package model;

import lombok.Data;

/**
 * auth: shi yi
 * create date: 2018/9/18
 */
@Data
public class CsdnBlog {
    private String author;
    private String titile;
    private String content;
    private String date;
    private String view;
    private String tag;
    private Integer num;
    private double price;
}
