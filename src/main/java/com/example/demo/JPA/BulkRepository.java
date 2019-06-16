package com.example.demo.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BulkRepository extends JpaRepository<BulkEntity, Long> {

}
