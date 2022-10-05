package com.itavenues.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OnlineStatusDto {

  private String userId;

  /**
   * Новый онлайн-статус
   */
  private boolean onlineNow;

  /**
   * Предыдущий онлайн-статус
   */
  private boolean onlineBefore;
}
