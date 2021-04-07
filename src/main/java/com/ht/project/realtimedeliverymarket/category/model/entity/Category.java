package com.ht.project.realtimedeliverymarket.category.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Getter
@NoArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id", nullable = false)
  private long id;

  @Column(name = "category_name", nullable = false)
  private String name;

  /**
   * xToOne 으로 끝나는 연관관계 매핑의 경우,
   * default 가 EAGER 이기 때문에 연관된 엔티티를 즉시 로딩하게 됩니다.
   * 즉, 조회 시에 당장 필요하지 않은 데이터를 한 번에 조회할 수 있습니다.
   * 그러므로 LAZY 로 변경하여 연관된 엔티티를 필요 시에 조회할 수 있도록 해야합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_Id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "create_at", nullable = false, updatable = false)
  private LocalDateTime createAt;

  @UpdateTimestamp
  @Column(name = "update_at", nullable = false)
  private LocalDateTime updateAt;

  public Category(String name) {
    this.name = name;
  }

  public Category(String name, Category parent) {
    this.name = name;
    this.parent = parent;
  }

  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(child);
  }

  public void setParent(Category parent) {

    if(this.parent != null) {
      this.parent.getChild().remove(this);
    }

    this.parent = parent;
    parent.getChild().add(this);

  }
}
