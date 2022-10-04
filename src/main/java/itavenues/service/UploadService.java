package itavenues.service;

import itavenues.util.Consts;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UploadService {

  @PostConstruct
  private void createUploadsCatalog() {
    if (!Files.exists(Consts.AVATARS_CATALOG_PATH)) {
      try {
        Files.createDirectory(Consts.AVATARS_CATALOG_PATH);
        log.info("Created avatars directory");
      } catch (Exception e) {
        log.error("Error in creation avatars directory");
      }
    }
  }

  /**
   * Загружает JPEG-изображение аватара для пользователя. Максимальный размер 5 Мб
   * @return имя файла с аватаром, сгенерированное приложением
   */
  public String uploadAvatar(MultipartFile avatar) {
    var fileName = UUID.randomUUID() + ".jpg";
    var targetPath = Consts.AVATARS_CATALOG_PATH.resolve(fileName);

    try {
      Files.copy(avatar.getInputStream(), targetPath);
    } catch (IOException e) {
      log.error("Error in saving an avatar");
    }

    return fileName;
  }
}
