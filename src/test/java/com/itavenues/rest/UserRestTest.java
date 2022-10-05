package com.itavenues.rest;

import static com.itavenues.util.Consts.API_V1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.itavenues.initializer.AbstractTestContainers;
import com.itavenues.model.OnlineStatusDto;
import com.itavenues.model.UserDto;
import com.itavenues.service.UploadService;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "/sql/truncate_all_procedure_call.sql",
    "/sql/data.sql"
})
public class UserRestTest extends AbstractTestContainers {

  @Autowired
  private UploadService uploadService;

  /*
  Проверяется создание нового пользователя и одновременно загрузка аватара
   */
  @Test
  void createNewUser() {
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    var body = new LinkedMultiValueMap<String, Object>();
    body.put("avatar", List.of(new FileSystemResource("src/test/resources/avatars/avatar.jpg")));

    var uploadAvatar = testRestTemplate.exchange(
        API_V1 + "/upload/avatar", HttpMethod.POST,
        new HttpEntity<>(body, headers), String.class);
    assertThat(uploadAvatar.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(uploadAvatar.getBody()).isNotNull();

    var userDto = UserDto.builder()
        .name("a")
        .email("email@email.com")
        .avatarName(uploadAvatar.getBody())
        .build();

    var createNewUser = testRestTemplate.exchange(
        API_V1 + "/users/create", HttpMethod.POST,
        new HttpEntity<>(userDto), String.class);

    assertThat(createNewUser.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(createNewUser.getBody()).isEqualTo(DigestUtils.sha256Hex(userDto.getEmail()));

    assertThat(uploadService.deleteAvatar(userDto.getAvatarName())).isTrue();
  }

  /*
  Получаем информацию о пользователе
   */
  @Test
  void getUserInfo() {
    var userId = "13832fadc1a45b5d663886cd08963d68c1d4164189b58a84cd41bbbbff1385c1";

    var getUserInfo = testRestTemplate.exchange(
        API_V1 + "/users/" + userId, HttpMethod.GET, null, UserDto.class);
    assertThat(getUserInfo.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(getUserInfo.getBody()).isNotNull();

    var expectedUser = UserDto.builder()
        .name("hello")
        .email("hello@mail.com")
        .avatarName(null)
        .build();

    assertEquals(getUserInfo.getBody(), expectedUser);
  }

  /*
  Устанавливаем онлайн-статус пользователя
   */
  @Test
  void setUserOnlineStatus() {
    var userId = "13832fadc1a45b5d663886cd08963d68c1d4164189b58a84cd41bbbbff1385c1";
    var url = UriComponentsBuilder.newInstance()
        .path(API_V1 + "/users/setOnline")
        .queryParam("id", userId)
        .queryParam("online", false)
        .build()
        .toString();

    var setOnlineStatus = testRestTemplate.exchange(
        url, HttpMethod.POST, null, OnlineStatusDto.class);
    assertThat(setOnlineStatus.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(setOnlineStatus.getBody()).isNotNull();

    var expectedOnlineStatus = OnlineStatusDto.builder()
        .userId(userId)
        .onlineNow(false)
        .onlineBefore(true)
        .build();

    assertEquals(expectedOnlineStatus, setOnlineStatus.getBody());
  }
}
