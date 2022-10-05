package com.itavenues.rest;

import static com.itavenues.util.Consts.API_V1;

import com.itavenues.model.OnlineStatusDto;
import com.itavenues.model.UserDto;
import com.itavenues.service.UserService;
import java.io.FileNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = API_V1 + "/users")
public class UserController {

  private final UserService userService;

  @PostMapping(path = "/create")
  public String createNewUser(@Valid @RequestBody UserDto userDto) throws FileNotFoundException, IllegalStateException {
    return userService.createNewUser(userDto);
  }

  @GetMapping(path = "/{userId}")
  public UserDto getUserInfo(@PathVariable String userId) {
    return userService.getUserInfo(userId);
  }

  @PostMapping(path = "/setOnline")
  public OnlineStatusDto setUserOnlineStatus(@RequestParam(name = "id") String userId, @RequestParam(name = "online") boolean online) {
    return userService.setOnlineStatus(userId, online);
  }
}
