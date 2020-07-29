package com.ht.project.realtimedeliverymarket.member.repository;

import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

  /**
   * Spring Data JPA는 선언한 "도메인 클래스 + .(점) + 메소드 이름" 으로 Named 쿼리를 찾아서 실행합니다.
   * 즉, Member.findByAccount 쿼리를 수행합니다.
   *
   * @param account
   * 해당 어노테이션을 사용하지 않으면 기본값은 위치 기반으로써, 파라미터 순서로 바인딩합니다.
   * 하지만, 어노테이션을 사용하였을 경우, 이름 기반의 파라미터 바인딩을 사용합니다.
   * 이름 기반 파라미터 바인딩을 사용하면 코드 가독성과 유지보수성이 증가합니다.
   *
   * @return
   */
  List<Member> findByAccount(@Param("account") String account);
}
