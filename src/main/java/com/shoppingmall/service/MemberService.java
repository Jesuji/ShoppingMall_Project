package com.shoppingmall.service;

import com.shoppingmall.dto.member.FindIdRequest;
import com.shoppingmall.dto.member.FindPasswordRequest;
import com.shoppingmall.dto.member.JoinRequest;
import com.shoppingmall.dto.member.LoginRequest;
import com.shoppingmall.entity.Coupon;
import com.shoppingmall.entity.Member;
import com.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String joinMember(JoinRequest request) {
        String joinname = request.getName();
        String joinemmail = request.getEmail();
        String joinpassword = request.getPassword();

        Member newMember = new Member(joinname, joinemmail, joinpassword);
        Coupon coupon = getCoupon();
        newMember.addCoupon(coupon);

        memberRepository.save(newMember);

        return "회원가입 성공";
    }

    public String loginMember(LoginRequest request) {
        Optional<Member> value = memberRepository.findByEmailAndPassword(request.email(), request.password());
        if(value.isPresent()) {
            return "로그인 성공";
        } else {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }
    }

    public Member findMemberByEmail(String email) {
        Optional<Member> value = memberRepository.findByEmail(email);

        if(value.isPresent()) {
            return value.get();
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다");
        }
    }

    public ResponseEntity<Map<String, String>> forgetId(FindIdRequest request) {
        String name = request.name();

        Optional<Member> member = memberRepository.findByName(name);

        if(member.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("email", member.get().getEmail());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "이름과 일치하는 회원을 찾을 수 없습니다."));
        }
    }

    public void forgetPassword(FindPasswordRequest request) {
        String email = request.email();
        String newPassword = request.newPassword();

        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            member.get().setPassword(newPassword);
            memberRepository.save(member.get());
        } else {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }
    }

    public boolean isEmailDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Coupon getCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCouponDiscount(6000);
        coupon.setDescription("신규 회원 전상품 6000원 할인 쿠폰");
        coupon.setExpirationDate("유호 기간 없음");
        return coupon;
    }
}
