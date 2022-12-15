package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Book implements Serializable {
    public Book(){}
    private Long id;
    private Long authorId;
    private String name;
    private BigDecimal price;
}
