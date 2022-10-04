package itavenues.rest;

import itavenues.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/upload")
public class UploaderController {

  private final UploadService uploadService;

  @PostMapping(path = "/avatar")
  public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
    return uploadService.uploadAvatar(avatar);
  }
}
