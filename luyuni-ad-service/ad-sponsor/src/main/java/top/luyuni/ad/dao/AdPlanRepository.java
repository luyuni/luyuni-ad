package top.luyuni.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.luyuni.ad.entity.AdPlan;

import java.util.List;

public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    AdPlan findByIdAndUserId(Long id, Long userId);

    List<AdPlan> findAllByInAndUserId(List<Long> ids, Long userId);

    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    List<AdPlan> findAllByPlanStatus(Integer status);
}
