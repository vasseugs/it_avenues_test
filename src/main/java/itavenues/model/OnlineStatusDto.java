package itavenues.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStatusDto {

  private Long userId;

  /**
   * Новый онлайн-статус
   */
  private boolean onlineNow;

  /**
   * Предыдущий онлайн-статус
   */
  private boolean onlineBefore;
}
