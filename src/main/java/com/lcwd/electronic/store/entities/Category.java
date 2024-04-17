package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name="category_title",length = 60,nullable = false)
    private String title;
    @Column(name="category_desc",length = 500)
    private String description;
    private String coverImage;
    //fetch type meaning when we fetch category us time product na fetch ho product fetch honge on demand
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    //other property if has

}
