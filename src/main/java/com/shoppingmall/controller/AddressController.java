package com.shoppingmall.controller;

import com.shoppingmall.dto.order.AddressDTO;
import com.shoppingmall.entity.Address;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final OrderService orderService;

    //기본 배송지 설정
    @GetMapping("/address/default")
    public ResponseEntity<AddressDTO> getDefaultAddress(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<Address> defaultAddress = orderService.getDefaultAddress(member.getId());
        if (defaultAddress.isPresent()) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setRecipientName(defaultAddress.get().getRecipientName());
            addressDTO.setPhoneNumber(defaultAddress.get().getPhoneNumber());
            addressDTO.setPostalCode(defaultAddress.get().getPostalCode());
            addressDTO.setAddressLine1(defaultAddress.get().getAddressLine1());
            addressDTO.setAddressLine2(defaultAddress.get().getAddressLine2());
            return ResponseEntity.ok(addressDTO);
        } else {
            return ResponseEntity.ok(null);
        }

    }
}
