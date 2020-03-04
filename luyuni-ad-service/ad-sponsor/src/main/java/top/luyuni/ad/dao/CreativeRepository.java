package top.luyuni.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.luyuni.ad.entity.Creative;

public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
