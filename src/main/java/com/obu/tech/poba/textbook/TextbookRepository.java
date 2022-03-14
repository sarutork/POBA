package com.obu.tech.poba.textbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long>, JpaSpecificationExecutor<Textbook> {
}
