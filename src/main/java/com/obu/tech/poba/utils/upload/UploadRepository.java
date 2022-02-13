package com.obu.tech.poba.utils.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {

    @Query(
            value = "SELECT * FROM upload WHERE upload_group = ?1 AND reference_key = ?2 ORDER BY upload_id",
            nativeQuery = true
    )
    List<Upload> getByGroupAndReference(String uploadGroup, Long referenceKey);
}
