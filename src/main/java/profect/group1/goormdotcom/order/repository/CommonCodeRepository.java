package profect.group1.goormdotcom.order.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.order.repository.entity.CommonCodeEntity;

public interface CommonCodeRepository extends JpaRepository<CommonCodeEntity, String> {

    // 공통코드 조회: (그룹, 코드)로 1건
    Optional<CommonCodeEntity> findByCodeGroupAndCode(String codeGroup, String code);
}
