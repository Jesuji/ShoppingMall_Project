package com.shoppingmall.controller;

import com.shoppingmall.dto.member.*;
import com.shoppingmall.dto.order.OrderResponseDTO;
import com.shoppingmall.dto.review.ReviewDTO;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.CouponService;
import com.shoppingmall.service.MemberService;
import com.shoppingmall.service.OrderService;
import com.shoppingmall.service.ReviewService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final CouponService couponService;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(JoinRequest Dto) {
        memberService.joinMember(Dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    //로그인하면 SESSION_ID를 생성
    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody LoginRequest Dto,
                                        HttpSession session,
                                        HttpServletResponse response) {
        String result = memberService.loginMember(Dto);

        Member member = memberService.findMemberByEmail(Dto.email());
        session.setAttribute("member", member); //세션에 Member 객체를 저장

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken); //세션에 csrfToken이 포함되도록 함
        session.setMaxInactiveInterval(1800); // 세션 만료시간을 30분으로 설정

        Cookie sessionCookie = new Cookie("SESSION_ID", session.getId());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true);
        sessionCookie.setMaxAge(1800); //쿠키 만료시간 30분
        sessionCookie.setPath("/");

        response.addCookie(sessionCookie);
        session.setAttribute("member", member);

        return ResponseEntity.ok(result);
    }

    //이메일 중복확인 메서드(가입 여부 확인)
    @GetMapping("/check-email")
    public ResponseEntity<Map<String,Boolean>> checkEmailDuplicate(@RequestParam String email) {
        boolean isDuplicate = memberService.isEmailDuplication(email);

        // JSON 응답을 위한 Map 객체 생성
        Map<String,Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);

        return ResponseEntity.ok(response);
    }

    //아이디(이메일) 찾기
    @PostMapping("/find-id")
    public ResponseEntity<Map<String, String>> findMemberEmail(@RequestBody FindIdRequest request) {
        return memberService.forgetId(request);
    }

    //비밀번호 찾기
    @PostMapping("/find-password")
    public void findPassword(@RequestBody FindPasswordRequest request) {
        memberService.forgetPassword(request);
    }

    //나의 리뷰 확인
    @GetMapping("/my-page/reviews")
    public String ShowMyReviews(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/login";
        }

        List<ReviewDTO> myReviews = reviewService.MyPageReviews(member.getId());
        model.addAttribute("myReviews", myReviews);

        return "review-history";
    }

    //모든 주문 내역 확인
    @GetMapping("/my-page/orders/history")
    public String getOrderHistory(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/login";
        }

        List<OrderResponseDTO> orders = orderService.findOrderHistoryByMemberId(member.getId());
        model.addAttribute("orders", orders);

        return "order-history";
    }

    //나의 쿠폰 확인
    @GetMapping("/my-page/coupons")
    public String ShowMyCoupons(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/login";
        }

        List<CouponDTO> coupons = couponService.getMemberCoupons(member.getId());
        model.addAttribute("coupons", coupons);

        return "coupon";
    }

    //마이 페이지 조회
    @GetMapping("/my-page")
    public String showMyPage(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/login";
        }

        List<ReviewDTO> myReviews = reviewService.MyPageReviews(member.getId());
        List<OrderResponseDTO> orders = orderService.findOrderHistoryByMemberId(member.getId());

        int orderCount = orders.size();
        int reviewCount = myReviews.size();
        int points = member.getPoints();
        int couponCount = member.getCoupons().size();

        model.addAttribute("points", points);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("orderCount", orderCount);

        return "my-page";
    }

    @GetMapping("/agreement")
    public String showAgreementForm() {
        return "agreement";
    }

    @GetMapping("/signup")
    public String showSignForm() {
        return "signup";
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        return new ModelAndView("login");
    }

    @GetMapping("/find-id")
    public ModelAndView showFindIdForm() {
        return new ModelAndView("find-id");
    }

    @GetMapping("/find-password")
    public ModelAndView showFindPasswordForm() {
        return new ModelAndView("find-password");
    }

    //로그 아웃 -> 로그인 화면으로 리다이렉션
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
            // 클라이언트 측의 세션 쿠키 삭제
            Cookie sessionCookie = new Cookie("SESSION_ID", null); // 세션 ID를 null로 설정
            sessionCookie.setPath("/"); // 유효 경로 설정
            sessionCookie.setMaxAge(0); // 쿠키 만료 시간 0으로 설정 (즉시 삭제)
            sessionCookie.setHttpOnly(true); // HttpOnly 속성 유지
            sessionCookie.setSecure(true);   // HTTPS에서만 전송되는 속성 유지 (필요시)

            response.addCookie(sessionCookie); // 응답에 쿠키 추가

            return "redirect:/login";
        }

    }

