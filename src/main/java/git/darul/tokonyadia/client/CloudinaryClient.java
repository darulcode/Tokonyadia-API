package git.darul.tokonyadia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cloudinary", url = "https://res.cloudinary.com")
public interface CloudinaryClient {


    @GetMapping("/{cloudName}/image/upload/{publicId}")
    ResponseEntity<byte[]> getImage(@PathVariable("cloudName") String cloudName,
                                    @PathVariable("publicId") String publicId);
}
