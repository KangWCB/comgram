package KangWCB.comgram.photo;

import KangWCB.comgram.config.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Photo")
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="photo_id")
    private Long id;
    private String orgNm;

    private String savedNm;

    private String savedPath;
    @Builder
    public Photo(Long id, String orgNm, String savedNm, String savedPath) {
        this.id = id;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
    }
}
