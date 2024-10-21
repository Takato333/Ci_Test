package com.example.demo.repository;

import com.example.demo.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ServiceEntity findServiceById(long id);
    ServiceEntity findServiceByName(String name);
    List<ServiceEntity> findServiceByIsDeletedFalse();
}
