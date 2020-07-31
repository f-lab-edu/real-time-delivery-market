package com.ht.project.realtimedeliverymarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.project.realtimedeliverymarket.member.controller.MemberController;
import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @WebMvcTest
 *
 * 일반적으로 사용하는 MVC테스트용 어노테이션입니다.
 * 해당 어노테이션을 명시하고 MockMvc를 @Autowired하면 해당 객체를 통해 MVC테스트가 가능합니다.
 * @SpringBootTest 을 같이 사용할 수 없습니다.
 * MVC 관련 빈들만 설정하기 때문에 @Service, @Repository, @Component 등에는 빈을 주입하지 않습니다.
 * 그러므로 해당 어노테이션을 직접 사용하지 않더라도
 * Controller 에는 해당 빈이 필요하기 때문에 @MockBean 어노테이션을 통해 Mock 빈을 주입하여야만 합니다.
 * 위의 빈들을 사용해서 테스트를 해야하는 경우에는 @Import 어노테이션을 통해 직접 빈을 주입해주어야만 합니다.
 *
 */
@WebMvcTest(MemberController.class)
public class MemberJoinDtoValidationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MemberService memberService;

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  @DisplayName("회원가입_요청_유효성_검증_Ok")
  public void join_CreateMemberJoinDto_Ok() throws Exception {

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

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isOk());
  }

  @Test
  @DisplayName("회원가입_Id_유효문자검증_BadRequest")
  public void join_InvalidCharIntoAccount_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithAccount("Test1234");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_Id_글자수검증_BadRequest")
  public void join_InvalidLengthAccount_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithAccount("test1234567890");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_Id_Null_삽입_BadRequest")
  public void join_NullIntoAccount_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithAccount(null);

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_Id_빈문자열삽입_BadRequest")
  public void join_BlankIntoAccount_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithAccount("");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_password_공백삽입_BadRequest")
  public void join_WhiteSpaceIntoPassword_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPassword("Test 1234!");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_password_소문자누락_BadRequest")
  public void join_MissLowercasePassword_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPassword("TEST1234!");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_password_대문자누락_BadRequest")
  public void join_MissUppercasePassword_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPassword("test1234!");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_password_Null삽입_BadRequest")
  public void join_NullIntoPassword_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPassword(null);

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_password_공백삽입_BadRequest")
  public void join_BlankIntoPassword_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPassword("");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_email_잘못된형식_BadRequest")
  public void join_InvalidFormatEmail_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithEmail("test.com");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_email_Null삽입_BadRequest")
  public void join_NullIntoEmail_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithEmail(null);

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_email_공백삽입_BadRequest")
  public void join_BlankIntoEmail_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithEmail("");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_phone_잘못된형식_BadRequest")
  public void join_InvalidFormatPhone_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPhone("010123123");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_phone_Null삽입_BadRequest")
  public void join_NullIntoPhone_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPhone(null);

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입_phone_공백삽입_BadRequest")
  public void join_BlankIntoPhone_BadRequest() throws Exception {

    MemberJoinDto memberJoinDto = createTestcaseWithPhone("");

    ResultActions res = join_request_action(memberJoinDto);

    res.andExpect(status().isBadRequest());
  }

  private MemberJoinDto createTestcaseWithAccount(String account) {

    return MemberJoinDto.builder()
            .account(account)
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();
  }

  private MemberJoinDto createTestcaseWithPassword(String password) {

    return MemberJoinDto.builder()
            .account("test1234")
            .password(password)
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();
  }

  private MemberJoinDto createTestcaseWithEmail(String email) {

    return MemberJoinDto.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email(email)
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();
  }

  private MemberJoinDto createTestcaseWithPhone(String phone) {

    return MemberJoinDto.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone(phone)
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();
  }

  /**
   * MockMvc 객체 :
   * 애플리케이션 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있는 클래스
   *
   * perform() :
   * 요청을 수행하고 연쇄적으로 더 많은 작업들을 할 수 있는 ResultAction Type을 반환합니다.
   * 더 많은 작업에는 기대 결과를 단언하는 등의 작업이 있습니다.
   *
   * ResultActions :
   * 특정 동작을 수행하거나, 기대 결과를 단언하거나,
   * 결과에 직접 액세스하기 위해 실행 된 요청의 결과를 반환하는 등의 작업을 할 수 있는 클래스입니다.
   *
   * MockMvcRequestBuilders:
   * MockHttpServletRequest 클래스를 빌드합니다. 즉 요청을 만듭니다.
   * post()의 경우, 파라미터 url에 대한 post 요청을 만듭니다.
   *
   * @param memberJoinDto
   * @return
   * @throws Exception
   */
  private ResultActions join_request_action(MemberJoinDto memberJoinDto) throws Exception {

    return mockMvc.perform(post("/members")
            .content(mapper.writeValueAsString(memberJoinDto))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
