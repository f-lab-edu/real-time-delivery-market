package com.ht.project.realtimedeliverymarket.point.service;

import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.point.model.dto.PointSaveDto;
import com.ht.project.realtimedeliverymarket.point.model.entity.Point;
import com.ht.project.realtimedeliverymarket.point.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PointService {

  @Autowired
  private PointRepository pointRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Transactional
  public Point savePoint(Long memberId, PointSaveDto pointSaveDto) {

    return pointRepository.save(Point.create(
            memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new),
            pointSaveDto));
  }
}
