package KangWCB.comgram.photo;

import KangWCB.comgram.photo.repository.PhotoRepositoryCustom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoRepository extends JpaRepository<Photo,Long>, PhotoRepositoryCustom {

}
