package com.ht.project.realtimedeliverymarket.category.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Getter
@NoArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categories_id", nullable = false)
  private long id;

  @Column(name = "categories_name", nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "parent_Id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>();

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
