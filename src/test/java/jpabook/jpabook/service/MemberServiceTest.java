package jpabook.jpabook.service;

import jakarta.persistence.EntityManager;
import jpabook.jpabook.domain.Member;
import jpabook.jpabook.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest // container를 하나 띄워서 test를 진행. spring bean 전부 사용
@Transactional // default로 t
/**
 * ctrl + shift + t로 test 빠르게 생성가능
 * @Runwith 는 생략가능 (Junit5 이후)
 */
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(false)
    public void sign_up() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //that
        assertEquals(member,memberRepository.findOne(saveId));
    }


    @Test
    public void check() throws Exception{
        //given
        final Member member1 = new Member();
        member1.setName("kim");

        final Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        } );
    }


}