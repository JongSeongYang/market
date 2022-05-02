package com.market.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OrderProduct")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderProductEntity extends BaseTimeEntity{

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "orderId")
  private OrderEntity orderEntity;

  @ManyToOne
  @JoinColumn(name = "productId")
  private ProductEntity productEntity;
  private Integer orderPrice;
  private Integer count;
  private LocalDateTime deletedTime;
}

