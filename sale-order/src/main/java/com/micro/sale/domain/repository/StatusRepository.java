package com.micro.sale.domain.repository;

import com.micro.sale.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository  extends JpaRepository<Status, Long> {
}
