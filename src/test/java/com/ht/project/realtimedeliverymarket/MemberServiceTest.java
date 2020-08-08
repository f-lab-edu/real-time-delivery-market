package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @DataJpaTest
 *
 * JPA 컴포넌트들에만 집중하는 JPA 테스트에 대한 어노테이션입니다.
 * 해당 어노테이션을 사용하는 것은 모든 빈 설정을 하는 것을 막습니다.
 * 대신에 JPA 테스트에 관련된 설정만 적용하도록 합니다.
 * 기본적으로 @DataJpaTest 어노테이션이 적용된 테스트는 트랜잭션을 적용합니다.
 * 그러므로 각 테스트의 끝에는 작업을 롤백합니다.
 *
 * 또한 기존 설정된 DataSource를 대체하여 내장된 인 메모리 데이터베이스를 사용합니다.
 * (인 메모리 DB가 빠르지만 고려해보아야 할 것은 Hibernate가 지원하는 데이터베이스별 방언이 다르다는 점입니다.)
 * @AutoConfigureTestDatabase 어노테이션을 통해 설정을 변경할 수 있습니다.
 *
 * @AutoConfigureTestDatabase
 * 기존 DataSource를 대신하여 테스트 데이터베이스를 설정하기 위해 테스트 클래스에 적용하는 어노테이션입니다.
 *
 * replace 속성은 기존 DataSource 빈들을 대체할 타입을 결정합니다.
 * NONE의 경우, 기존 DataSource를 그대로 사용한다는 것을 의미합니다.
 *
 * @Import
 * @DataJpaTest 는 JPA 관련 빈들만 설정하기 때문에 MemberService 빈은 자동 설정되지 않습니다.
 * 그러므로 @Import 어노테이션을 사용하여 직접 빈을 주입해야 합니다.
 *
 * @TestPropertySource
 * 테스트에 사용할 properties 파일을 설정합니다.
 *
 * @DisplayName
 * 사용자 정의 이름을 표시합니다.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MemberService.class)
@TestPropertySource("/application-test.properties")
public class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("회원가입")
  public void joinInsertIntoDbShouldPass() {

    MemberJoinDto memberJoinDto = MemberJoinDto.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();

    Long saveId = memberService.join(memberJoinDto);

    assertEquals("test1234", memberRepository.findById(saveId).get().getAccount());
    assertEquals("Test1234!", memberRepository.findById(saveId).get().getPassword());
    assertEquals("홍길동", memberRepository.findById(saveId).get().getName());
    assertEquals("test1234@gmail.com", memberRepository.findById(saveId).get().getEmail());
    assertEquals("010-1234-1234", memberRepository.findById(saveId).get().getPhone());
    assertEquals("서울시 강남구", memberRepository.findById(saveId).get().getAddress().getCity());
    assertEquals("논현동", memberRepository.findById(saveId).get().getAddress().getStreet());
    assertEquals("테스트타워 101호", memberRepository.findById(saveId).get().getAddress().getDetail());
    assertEquals("123-123", memberRepository.findById(saveId).get().getAddress().getZipcode());

  }

  @Test
  @DisplayName("계정 중복 체크")
  public void verifyDuplicatedAccountExceptionThrown() {

    MemberJoinDto memberJoinDto1 = MemberJoinDto.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();

    MemberJoinDto memberJoinDto2 = MemberJoinDto.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();


    memberService.join(memberJoinDto1);

    assertThrows(IllegalStateException.class, () -> memberService.join(memberJoinDto2));
  }

}
