package com.shoppingmall.service;

import com.shoppingmall.dto.order.*;
import com.shoppingmall.entity.*;
import com.shoppingmall.repository.AddressRepository;
import com.shoppingmall.repository.CouponRepository;
import com.shoppingmall.repository.MemberRepository;
import com.shoppingmall.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public Long createOrder(Long memberId, OrderRequestDTO Dto, HttpSession session) {
        if (Dto.getIsDefault()) {
            addressRepository.resetDefaultAddress(memberId); // 기존 기본 배송지 초기화
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Address address = new Address();
        address.setMember(new Member(memberId));
        address.setRecipientName(Dto.getRecipientName());
        address.setPhoneNumber(Dto.getPhoneNumber());
        address.setPostalCode(Dto.getPostalCode());
        address.setAddressLine1(Dto.getAddressLine1());
        address.setAddressLine2(Dto.getAddressLine2());
        address.setIsDefault(Dto.getIsDefault()); // 기본 배송지 여부 설정
        address.setDeliveryRequest(Dto.getDeliveryRequest()); // 배송 요청 사항 설정
        Address savedAddress = addressRepository.save(address);

        int totalProductAmount = Dto.getSelectedItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity()).sum();

        int pointsUsed = Dto.getPoints();
        if (pointsUsed > member.getPoints()) {
            throw new IllegalArgumentException("사용 가능한 적립금을 초과했습니다.");
        }

        member.setPoints(member.getPoints() - pointsUsed);
        memberRepository.save(member);

        // 쿠폰 처리
        int couponDiscount = 0;
        if (Dto.getCouponId() != null) { // 쿠폰 ID가 있는 경우에만 처리
            Optional<Coupon> optionalCoupon = couponRepository.findById(Dto.getCouponId());
            if (optionalCoupon.isPresent()) {
                Coupon coupon = optionalCoupon.get();
                if (!coupon.getIsUsed()) { // 이미 사용된 쿠폰이 아닌 경우만 처리
                    coupon.setIsUsed(true);
                    couponDiscount = coupon.getCouponDiscount();
                } else {
                    throw new IllegalArgumentException("이미 사용된 쿠폰입니다.");
                }
            } else {
                throw new IllegalArgumentException("유효하지 않은 쿠폰입니다.");
            }
        }

        // 최종 결제 금액 계산
        int finalAmount = totalProductAmount - couponDiscount - pointsUsed;
        if (finalAmount < 0) finalAmount = 0;

        Order order = new Order();
        order.setMember(new Member(memberId)); // 회원 정보
        order.setAddress(savedAddress);
        order.setPaymentMethod(Dto.getPaymentMethod());
        order.setTotalAmount(totalProductAmount);
        order.setFinalAmount(finalAmount);
        if(Dto.getPaymentMethod().equalsIgnoreCase("계좌이체")) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else if (Dto.getPaymentMethod().equalsIgnoreCase("신용카드")) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        } else {
            throw new IllegalArgumentException("유효하지 않은 결제 방법입니다.");
        }

        order.setShippingStatus(ShippingStatus.PROCESSING);
        order.setOrderDate(LocalDateTime.now());
        order.setAddress(address);

        // 주문 항목 설정
        List<OrderDetails> orderDetailsList = Dto.getSelectedItems().stream().map(item -> {
            OrderDetails details = new OrderDetails();
            details.setOrder(order); // Order와 연결
            details.setProduct(new Product(item.getProductId())); // 상품 ID
            details.setQuantity(item.getQuantity());
            details.setPrice(item.getPrice());
            return details;
        }).collect(Collectors.toList());
        order.setOrderDetailsList(orderDetailsList);

        int rewardPoints = (int) (totalProductAmount * 0.03);

        //적립금 저장
        member.setPoints(member.getPoints() + rewardPoints);
        memberRepository.save(member);
        session.setAttribute("member", member);

        orderRepository.save(order);

        return order.getId();
    }

    /* 주문 완료하고 confirmation에서 보여주는 메서드 */
    public OrderResponseDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate().format(formatter));
        orderDTO.setPaymentMethod(order.getPaymentMethod());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        orderDTO.setShippingStatus(order.getShippingStatus());
        orderDTO.setRecipientName(order.getAddress().getRecipientName());
        orderDTO.setPhoneNumber(order.getAddress().getPhoneNumber());
        orderDTO.setAddressLine1(order.getAddress().getAddressLine1());
        orderDTO.setAddressLine2(order.getAddress().getAddressLine2());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setFinalAmount(order.getFinalAmount());

        // 주소 설정 (null 체크 추가)
        Address address = order.getAddress();
        if (address != null) {
            orderDTO.setAddressLine1(address.getAddressLine1());
            orderDTO.setAddressLine2(address.getAddressLine2());
        }

        // 주문 항목 추가
        List<OrderItemDTO> orderItemDTOs = order.getOrderDetailsList().stream()
                .map(orderDetails -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setProductName(orderDetails.getProduct().getName());
                    orderItemDTO.setImagePath(orderDetails.getProduct().getImagePath());
                    orderItemDTO.setQuantity(orderDetails.getQuantity());
                    orderItemDTO.setPrice(orderDetails.getPrice());
                    return orderItemDTO;
                }).collect(Collectors.toList());
        orderDTO.setItems(orderItemDTOs);

        return orderDTO;
    }

    /* 마이 페이지 - 주문 내역에서 모든 주문 내역을 확인할 수 있는 메서드 */
    public List<OrderResponseDTO> findOrderHistoryByMemberId(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);

        return orders.stream()
                .map(order -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    OrderResponseDTO orderDTO = new OrderResponseDTO();
                    orderDTO.setId(order.getId());
                    orderDTO.setOrderDate(order.getOrderDate().format(formatter));
                    orderDTO.setDeliveryRequest(order.getAddress().getDeliveryRequest());
                    orderDTO.setPaymentMethod(order.getPaymentMethod());
                    orderDTO.setPaymentStatus(order.getPaymentStatus());
                    orderDTO.setShippingStatus(order.getShippingStatus());
                    orderDTO.setRecipientName(order.getAddress().getRecipientName());
                    orderDTO.setPhoneNumber(order.getAddress().getPhoneNumber());
                    orderDTO.setAddressLine1(order.getAddress().getAddressLine1());
                    orderDTO.setAddressLine2(order.getAddress().getAddressLine2());
                    orderDTO.setTotalAmount(order.getTotalAmount());
                    orderDTO.setFinalAmount(order.getFinalAmount());

                    List<OrderItemDTO> orderItemDTOs = order.getOrderDetailsList().stream()
                            .map(orderDetails -> {
                                OrderItemDTO orderItemDTO = new OrderItemDTO();
                                orderItemDTO.setProductName(orderDetails.getProduct().getName());
                                orderItemDTO.setImagePath(orderDetails.getProduct().getImagePath());
                                orderItemDTO.setQuantity(orderDetails.getQuantity());
                                orderItemDTO.setPrice(orderDetails.getPrice());
                                return orderItemDTO;
                            }).collect(Collectors.toList());
                    orderDTO.setItems(orderItemDTOs);

                    return orderDTO;
                }).collect(Collectors.toList());
    }

    public Optional<Address> getDefaultAddress(Long memberId) {
        return addressRepository.findByMemberIdAndIsDefaultTrue(memberId);
    }
}
