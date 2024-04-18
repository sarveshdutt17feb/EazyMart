package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {
    //add items to cart

    //case1 :cart for that user is not available we will create the cart and add item
   //case2:cart available add the items to cart
   CartDto addItemToCart(String userId, AddItemToCartRequest request);

   //remove item from cart
    //String userId because user has cart
    void removeItemFromCart(String userId,int cartItemId );

    //clear cart
    void clearCart(String userId);
    //fetch cart of particular user
    CartDto getCartByUser(String userId);

}
