package com.lcwd.electronic.store.dtos;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class ProductDto {



        private String productId;
        private String title;
        private String description;
        private int price;
        private int quantity;
        private int discountedPrice;
        private Date addedDate;
        private boolean isLive;
        private boolean stock;



}
