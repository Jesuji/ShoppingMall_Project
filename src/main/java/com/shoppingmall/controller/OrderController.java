package com.shoppingmall.controller;

import com.shoppingmall.dto.order.OrderRequestDTO;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    //주문 페이지
    @PostMapping("/submit")
    public ResponseEntity<Long> submitOrder(@RequestBody OrderRequestDTO Dto, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
            try {
                Long orderId = orderService.createOrder(member.getId(), Dto, session); // 생성된 주문 ID 반환
                return ResponseEntity.ok(orderId); // 주문 ID를 응답으로 반환
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1L);
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
}