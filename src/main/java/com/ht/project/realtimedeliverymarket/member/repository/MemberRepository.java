package com.ht.project.realtimedeliverymarket.member.repository;

import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

}
