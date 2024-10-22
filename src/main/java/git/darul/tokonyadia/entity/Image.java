package git.darul.tokonyadia.entity;

import git.darul.tokonyadia.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constant.IMAGE_TABLE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "url_path", nullable = false)
    private String urlPath;

    @Column(name = "public_id", nullable = false)
    private String publicId;

    @Column(name = "media_type", nullable = false)
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
