package com.itavenues.entity;

import com.itavenues.model.UserDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "avatar_name")
  private String avatarName;

  public UserDto toDto() {
    return UserDto.builder()
        .name(this.name)
        .email(this.email)
        .avatarName(this.avatarName)
        .build();
  }
}
