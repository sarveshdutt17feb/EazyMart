package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.*;
import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.OrderRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        //fetch user
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User of given id not found !!"));
        //fetch cart
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not found on server !!"));
        List<CartItem> cartItems = cart.getItems();
        if(cartItems.size()<=0){
            throw new BadApiRequestException("Invalid number of items in cart !!");
        }
        //other checks
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        //orderAmount not set ,orderItem not set -not done

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            //CartItem-->OrderItem coversion
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());

            return orderItem;
        }).collect(Collectors.toList());


        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
        //ab hamare pass order aa gya then cart ko clear kr denge
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pagenumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
