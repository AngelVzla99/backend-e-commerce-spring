package com.example.springboot.Order.contollers;

import com.example.springboot.Order.dtos.OrderAdminDto;
import com.example.springboot.Order.dtos.OrderDTO;
import com.example.springboot.Order.services.OrderService;
import com.example.springboot.User.dto.UserDTO;
import com.example.springboot.User.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Status codes:
 * 0 => Created
 * 1 => The payments are checked
 * 2 => The order was packed
 * 3 => The rider is in coming
 * 4 => The order was delivered and have been completed
 * 5 => The order have been cancelled
 */

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    SimpMessagingTemplate template;

    // ===============
    //    get EPs   //
    // ===============

    /**
     * This function send a message to all the clients connected to the
     * websocket endpoint and Subscribed to the topic of the order
     * The name of connection endpoint is ws-order
     */
    @GetMapping("/websocket/{orderId}")
    @ResponseBody
    public String websocket( @PathVariable Long orderId ){
        this.template.convertAndSend("/topic/order/"+orderId, orderId);
        return "Success";
    }
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/{id}")
    @ResponseBody
    @SendTo("/topic/greetings")
    public OrderDTO get(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping
    @ResponseBody
    public Page<OrderAdminDto> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by( sortDirection, sortBy));
        return orderService.findAllPageable(pageable);
    }

    // ===============
    //   post EPs   //
    // ===============

    @PostMapping("/create")
    @ResponseBody
    public OrderDTO save(Principal principal, @Valid @RequestBody OrderDTO orderDTO) {
        UserDTO user = userService.findByEmailToken(principal.getName());
        orderDTO.setUserId(user.getId());
        return orderService.createOrder(orderDTO);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @PutMapping("/{orderId}/rider-coming")
    @ResponseBody
    public void paymentChecked(@PathVariable Long orderId){
        orderService.paymentChecked(orderId);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @PutMapping("/{orderId}/order-coming")
    @ResponseBody
    public void riderComing(@PathVariable Long orderId){
        orderService.riderComing(orderId);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @PutMapping("/{orderId}/order-completed")
    @ResponseBody
    public void orderCompleted(@PathVariable Long orderId){
        orderService.orderCompleted(orderId);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @PutMapping("/{orderId}/order-cancelled")
    @ResponseBody
    public void orderCancelled(@PathVariable Long orderId){
        orderService.orderCancelled(orderId);
    }

}
