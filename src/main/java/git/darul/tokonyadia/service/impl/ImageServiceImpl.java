package git.darul.tokonyadia.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import git.darul.tokonyadia.client.CloudinaryClient;
import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.response.GetImageResponse;
import git.darul.tokonyadia.dto.response.ImageResponse;
import git.darul.tokonyadia.entity.Image;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.repository.ImageRepository;
import git.darul.tokonyadia.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final CloudinaryClient cloudinaryClient;

    @Value("${cloudinary.name}")
    private String CLOUDINARY_NAME;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ImageResponse> uploadImage(List<MultipartFile> file, Product product) {
        List<ImageResponse> imageResponses = new ArrayList<>();
        file.forEach(image -> {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                String format = (String) uploadResult.get("format");
                String mediaType = (String) uploadResult.get("resource_type");
                Image imageRequest = Image.builder()
                        .product(product)
                        .urlPath((String) uploadResult.get("secure_url"))
                        .publicId((String) uploadResult.get("public_id"))
                        .mediaType(mediaType + "/" + format)
                        .build();
                imageRepository.saveAndFlush(imageRequest);
                imageResponses.add(getImageResponse(imageRequest));
            } catch (IOException e) {
                log.error(Constant.ERROR_WHILE_UPLOAD_IMAGE,e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.ERROR_WHILE_UPLOAD_IMAGE);
            }
        });
        return imageResponses;
    }

    @Override
    public List<ImageResponse> getImageByProduct(Product product) {
        List<Image> imageList = imageRepository.findAllByProduct(product);
        return imageList.stream().map(this::getImageResponse).toList();
    }

    @Override
    public Image getOne(String id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.IMAGE_NOT_FOUND));
    }

    @Override
    public GetImageResponse getImageBytes(String id) {
        Image image = getOne(id);
        ResponseEntity<byte[]> imageResult = cloudinaryClient.getImage(CLOUDINARY_NAME, image.getPublicId());
        return GetImageResponse.builder()
                .image(imageResult.getBody())
                .mediaType(image.getMediaType())
                .build();
    }

    private ImageResponse getImageResponse(Image image) {
        return ImageResponse.builder()
                .urlImage("/api/image/" + image.getId())
                .build();
    }
}
