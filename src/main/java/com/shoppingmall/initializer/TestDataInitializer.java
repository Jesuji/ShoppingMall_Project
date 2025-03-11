package com.shoppingmall.initializer;

import com.shoppingmall.dto.member.JoinRequest;
import com.shoppingmall.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        // 이미 존재하는지 확인 후, 없으면 생성
        if (!memberService.existsByEmail("test@test")) {
            memberService.joinMember(new JoinRequest("Test", "test@test", "test"));
        }
    }

}
