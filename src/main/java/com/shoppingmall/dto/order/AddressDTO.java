package com.shoppingmall.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String recipientName;
    private String phoneNumber;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
}
