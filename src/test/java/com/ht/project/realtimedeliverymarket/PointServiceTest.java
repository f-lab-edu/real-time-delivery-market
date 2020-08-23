package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.point.model.dto.PointSaveDto;
import com.ht.project.realtimedeliverymarket.point.model.entity.Point;
import com.ht.project.realtimedeliverymarket.point.repository.PointRepository;
import com.ht.project.realtimedeliverymarket.point.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private PointRepository pointRepository;

  @InjectMocks
  private PointService pointService;

  @Test
  @DisplayName("회원가입 후 1000 포인트를 지급합니다.")
  public void savePointForJoinSuccess() {

    Member member = Member.builder()
            .account("test1111")
            .name("홍길동")
            .build();

    PointSaveDto pointSaveDto = new PointSaveDto("가입축하 포인트 지급",
            BigDecimal.valueOf(1000), 30L);

    Mockito.when(memberRepository.findById(any(Long.class)))
            .thenReturn(java.util.Optional.of(member));

    Mockito.when(pointRepository.save(any(Point.class))).thenReturn(Point
            .create(member, pointSaveDto));

    Point point = pointService.savePoint(1L, pointSaveDto);

    assertEquals("test1111", point.getMember().getAccount());
    assertEquals(BigDecimal.valueOf(1000), point.getValue());
  }
}
