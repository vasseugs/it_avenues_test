package com.itavenues.model;

import com.itavenues.entity.UserEntity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {

  @NotBlank(message = "Не заполнено имя пользователя")
  private String name;
  @Email(message = "Некорректно указан адрес электронной почты")
  private String email;
  private String avatarName;

  public UserEntity toUserEntity() {
    return UserEntity.builder()
        .id(DigestUtils.sha256Hex(this.getEmail()))
        .name(this.getName())
        .email(this.getEmail())
        .avatarName(this.getAvatarName())
        .build();
  }
}
