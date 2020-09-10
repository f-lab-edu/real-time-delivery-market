package com.ht.project.realtimedeliverymarket.point.model.entity;

import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.point.enumeration.UsageStatus;
import com.ht.project.realtimedeliverymarket.point.model.dto.PointSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "POINTS")
@NoArgsConstructor
@Getter
public class Point {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "point_id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "value", nullable = false)
  private BigDecimal value;

  @CreationTimestamp
  @Column(name = "create_at", nullable = false, updatable = false)
  private LocalDateTime createAt;

  @Column(name = "expire_at", nullable = false)
  private LocalDateTime expireAt;

  @Column(name = "usage_status", nullable = false)
  private UsageStatus usageStatus;

  public void setMember(Member member) {

    if(this.member != null) {
      this.member.getPoints().remove(this);
    }

    this.member = member;
    member.getPoints().add(this);
  }

  @Builder
  public Point(String content, BigDecimal value,
               LocalDateTime expireAt, UsageStatus usageStatus) {
    this.content = content;
    this.value = value;
    this.expireAt = expireAt;
    this.usageStatus = usageStatus;
  }

  public static Point create(Member member, PointSaveDto pointSaveDto) {

    Point point = Point.builder()
            .content(pointSaveDto.getContent())
            .value(pointSaveDto.getValue())
            .expireAt(LocalDateTime.now().plusDays(pointSaveDto.getExpire()))
            .usageStatus(UsageStatus.NOT_USED)
            .build();

    point.setMember(member);

    return point;
  }
}
