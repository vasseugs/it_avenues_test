package com.itavenues.service;

import com.itavenues.entity.OnlineStatusEntity;
import com.itavenues.entity.UserEntity;
import com.itavenues.model.UserDto;
import com.itavenues.repository.OnlineStatusRepository;
import com.itavenues.util.Consts;
import com.itavenues.util.Message;
import com.itavenues.model.OnlineStatusDto;
import com.itavenues.repository.UserRepository;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final OnlineStatusRepository onlineStatusRepository;

  /**
   * Сохраняет пользователя в БД, возвращает его идентификатор
   */
  public String createNewUser(UserDto userDto) throws IllegalStateException, FileNotFoundException {
    validateNewUser(userDto);
    var newUserId = userRepository.save(userDto.toUserEntity()).getId();
    onlineStatusRepository.save(new OnlineStatusEntity(newUserId, true));
    return newUserId;
  }

  private void validateNewUser(UserDto userDto) throws IllegalStateException, FileNotFoundException {
    validateAvatarId(userDto.getAvatarName());
    validateUserPresence(userDto);
  }

  /**
   * Проверяет, есть ли уже пользователь с таким именем и адресом почты
   */
  private void validateUserPresence(UserDto userDto) {
    var userWithSameEmail = userRepository.findByEmail(userDto.getEmail());

    if (userWithSameEmail.isPresent()) {
      if (Objects.equals(userDto.getName(), userWithSameEmail.get().getName())) {
        throw new IllegalArgumentException("Пользователь с таким именем и почтой уже существует");
      }

      throw new IllegalArgumentException("Пользователь с таким адресом почты уже существует");
    }
  }

  /**
   * Проверяет, что в каталоге с аватарами есть файл с таким идентификатором
   */
  private void validateAvatarId(String avatarId) throws IllegalStateException, FileNotFoundException {
    var avatarsDirectory = Consts.AVATARS_CATALOG_PATH.toFile();
    var filesInDirectory = avatarsDirectory.listFiles();

    if (filesInDirectory == null) {
      throw new IllegalStateException("Указан неправильный путь к каталогу с аватарами");
    }

    var avatarIdIsPresent = Arrays.stream(filesInDirectory)
        .anyMatch(file -> Objects.equals(file.getName(), avatarId));

    if (!avatarIdIsPresent) {
      throw new FileNotFoundException("Отсутствует аватар с идентификатором " + avatarId);
    }
  }

  /**
   * Возвращает информацию о пользователе по его идентификатору
   */
  public UserDto getUserInfo(String userId) {
    return userRepository.findById(userId)
        .map(UserEntity::toDto)
        .orElseThrow(() -> new NoSuchElementException(String.format(Message.USER_NOT_FOUND_BY_ID, userId)));
  }

  /**
   * Устанавливает онлайн-статус пользователя с указанным идентификатором и
   * возвращает данные о его изменении
   *
   * @param online новый онлайн-статус пользователя
   */
  public OnlineStatusDto setOnlineStatus(String userId, boolean online) {
    var currentStatusEntity = onlineStatusRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException(String.format(Message.USER_NOT_FOUND_BY_ID, userId)));

    var onlineStatusDto = OnlineStatusDto.builder()
        .userId(userId)
        .onlineNow(online)
        .onlineBefore(currentStatusEntity.isOnline())
        .build();

    currentStatusEntity.setOnline(online);
    onlineStatusRepository.save(currentStatusEntity);

    return onlineStatusDto;
  }
}
