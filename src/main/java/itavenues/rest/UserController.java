package itavenues.rest;

import itavenues.model.OnlineStatusDto;
import itavenues.model.UserDto;
import itavenues.service.UserService;
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
@RequestMapping(path = "/users")
public class UserController {

  private final UserService userService;

  @PostMapping(path = "/create")
  public Long createNewUser(@Valid @RequestBody UserDto userDto) throws FileNotFoundException, IllegalStateException {
    return userService.createNewUser(userDto);
  }

  @GetMapping(path = "{userId}")
  public UserDto getUserInfo(@PathVariable Long userId) {
    return userService.getUserInfo(userId);
  }

  @PostMapping(path = "/setOnline")
  public OnlineStatusDto setUserOnlineStatus(@RequestParam(name = "id") Long userId, @RequestParam(name = "online") boolean online) {
    return userService.setOnlineStatus(userId, online);
  }
}
