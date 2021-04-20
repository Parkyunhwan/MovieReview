package org.yunhwan.moviereview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yunhwan.moviereview.entity.Member;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() throws Exception {
        //given
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("email_" + i + "@gmail.com")
                    .pw("1111")
                    .nickname("Reviewer" + i)
                    .build();

            memberRepository.save(member);
        });
        //when

        //then
    }
}