package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.response.GetImageResponse;
import git.darul.tokonyadia.dto.response.ImageResponse;
import git.darul.tokonyadia.entity.Image;
import git.darul.tokonyadia.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageResponse> uploadImage(List<MultipartFile> file, Product product);
    List<ImageResponse> getImageByProduct(Product product);
    Image getOne(String id);
    GetImageResponse getImageBytes(String id);
}
