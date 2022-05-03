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
  private Integer productPrice; // 상품 구매 가격
  private Integer totalPrice; // 상품별 총액
  private Integer count; // 수량
  private Integer status; // 상품별 상태
  private LocalDateTime cancelTime; // 취소 시간
}

