package top.luyuni.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.luyuni.ad.entity.AdUser;

import java.util.Optional;

public interface AdUserRepository extends JpaRepository<AdUser, Long> {
    /**
     * 根据用户名查找用户记录
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
