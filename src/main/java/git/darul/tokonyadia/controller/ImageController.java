package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.response.GetImageResponse;
import git.darul.tokonyadia.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(Constant.IMAGE_API)
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Get Image")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        GetImageResponse imageResult = imageService.getImageBytes(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(imageResult.getMediaType()))
                .body(imageResult.getImage());
    }
}
