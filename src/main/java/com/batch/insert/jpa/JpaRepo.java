package com.batch.insert.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JpaRepo extends JpaRepository<JpaEntity, Long> {



}
