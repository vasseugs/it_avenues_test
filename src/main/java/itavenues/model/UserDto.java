package itavenues.model;

import itavenues.entity.UserEntity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

  @NotBlank(message = "Не заполнено имя пользователя")
  private String name;
  @Email(message = "Некорректно указан адрес электронной почты")
  private String email;
  private String avatarName;

  public UserEntity toEntity() {
    return UserEntity.builder()
        .name(this.name)
        .email(this.email)
        .avatarName(this.avatarName)
        .build();
  }
}
