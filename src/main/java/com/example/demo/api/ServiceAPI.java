package com.example.demo.api;

import com.example.demo.entity.ServiceEntity;
import com.example.demo.model.ServiceRequest;
import com.example.demo.service.ServiceEntityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class ServiceAPI {
    @Autowired
    ServiceEntityService serviceEntityService;


    @PostMapping
    public ResponseEntity <ServiceEntity> createService (@Valid @RequestBody ServiceRequest service) {
        ServiceEntity newService = serviceEntityService.createService(service);
        return ResponseEntity.ok(newService);
    }

    @GetMapping
    public ResponseEntity  getAllServices() {
        List<ServiceEntity> services = serviceEntityService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @PutMapping("{serviceId}")
    public ResponseEntity updateService(@PathVariable long serviceId, @Valid @RequestBody ServiceEntity service) {
        ServiceEntity updatedService = serviceEntityService.updateServiceById(serviceId, service);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("{serviceId}")
    public ResponseEntity deleteService(@PathVariable long serviceId) {
        ServiceEntity updatedService = serviceEntityService.deleteServiceById(serviceId);
        return ResponseEntity.ok(updatedService);
    }


}
