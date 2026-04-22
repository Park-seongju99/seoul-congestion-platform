package com.seoul.congestion.repository;

import com.seoul.congestion.domain.PlaceMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceMasterRepository extends JpaRepository<PlaceMaster, String> {
}
