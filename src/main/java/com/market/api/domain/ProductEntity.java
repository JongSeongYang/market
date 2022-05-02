package com.market.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductEntity extends BaseTimeEntity{

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String category;
  private String description;
  private Integer price;
  private Integer stock;
  private LocalDateTime deletedTime;
}

