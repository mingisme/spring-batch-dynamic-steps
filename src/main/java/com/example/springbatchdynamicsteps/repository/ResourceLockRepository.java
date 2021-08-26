package com.example.springbatchdynamicsteps.repository;

import com.example.springbatchdynamicsteps.model.ResourceLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceLockRepository extends JpaRepository<ResourceLock, Long> {
}
