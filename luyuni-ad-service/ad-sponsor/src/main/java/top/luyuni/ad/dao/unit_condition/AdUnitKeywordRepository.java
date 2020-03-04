package top.luyuni.ad.dao.unit_condition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.luyuni.ad.entity.unit_condition.AdUnitKeyword;

public interface AdUnitKeywordRepository extends JpaRepository<AdUnitKeyword, Long> {
}
