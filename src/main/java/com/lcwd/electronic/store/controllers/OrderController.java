package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    //create
    @Autowired
    private OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request){
        OrderDto orderDto = orderService.createOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }
    //remove order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("order deleted successfully !!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
    //get orders of user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId){
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    //get all orders

    @GetMapping("/users/{userId}")
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)  int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy

    ){
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
