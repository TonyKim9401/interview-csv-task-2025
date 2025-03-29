package ticketmaster.demo.posting.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Posting")
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @NotNull
    @Comment("Title")
    @Column(name = "TITLE")
    private String title;

    @Comment("Image URL")
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Comment("Content")
    @Column(name = "CONTENT")
    private String content;

    @Comment("Upload Date")
    @Column(name = "UPLOAD_DATE")
    @CreatedDate
    private LocalDateTime uploadDate;

    @Comment("Modify Date")
    @Column(name = "MODIFY DATE")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @PrePersist
    public void prePersist() {
        if (modifyDate == null) {
            modifyDate = uploadDate;
        }
    }
}

