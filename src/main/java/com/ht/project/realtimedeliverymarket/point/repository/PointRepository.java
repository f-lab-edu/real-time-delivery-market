package com.ht.project.realtimedeliverymarket.point.repository;

import com.ht.project.realtimedeliverymarket.point.model.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
}
