package com.shoppingmall.controller;

import com.shoppingmall.dto.order.OrderResponseDTO;
import com.shoppingmall.entity.Coupon;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderService orderService;

    @GetMapping("/order/submit")
    public String showSubmitPage() {
        return "order";
    }

    //주문 페이지 뷰(장바구니에서 넘어옴)
    @GetMapping("/order")
    public String showOrderPage(
            @RequestParam("totalAmount") String totalAmount,
            @RequestParam("selectedItems") String selectedItems,
            Model model,
            HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        List<Coupon> coupons = member.getCoupons();
        int points = member.getPoints();

        model.addAttribute("coupons", coupons);
        model.addAttribute("points", points);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("selectedItems", selectedItems);

        return "order";
    }

    //주문 완료 확인 페이지
    @GetMapping("/order/confirmation")
    public String showOrderConfirmation(@RequestParam Long orderId, Model model) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderDetails(orderId);

        model.addAttribute("orderId", orderResponseDTO.getId());
        model.addAttribute("paymentMethod", orderResponseDTO.getPaymentMethod());
        model.addAttribute("paymentStatus", orderResponseDTO.getPaymentMethod().equals("신용카드") ? "결제 완료" : "결제 대기");
        if (orderResponseDTO.getPaymentMethod().equals("무통장 입금")) {
            model.addAttribute("bankInfo", "농협은행 123-4567-890123");
        }
        model.addAttribute("recipientName", orderResponseDTO.getRecipientName());
        model.addAttribute("phoneNumber", orderResponseDTO.getPhoneNumber());
        model.addAttribute("addressLine1", orderResponseDTO.getAddressLine1());
        model.addAttribute("addressLine2", orderResponseDTO.getAddressLine2());
        model.addAttribute("orderItems", orderResponseDTO.getItems());
        model.addAttribute("totalAmount", orderResponseDTO.getTotalAmount());
        model.addAttribute("finalAmount", orderResponseDTO.getFinalAmount());

        return "confirmation";
    }
}
