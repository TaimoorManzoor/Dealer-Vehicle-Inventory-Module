package com.automobile.DealerAndVehicleInventory.Controller;

import com.automobile.DealerAndVehicleInventory.DataTransferObject.DealerDTO;
import com.automobile.DealerAndVehicleInventory.DataTransferObject.VehicleDTO;
import com.automobile.DealerAndVehicleInventory.Entity.Vehicle;
import com.automobile.DealerAndVehicleInventory.Enum.Status;
import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import com.automobile.DealerAndVehicleInventory.Service.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Vehicle EndPoint")
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/vehicles")
    public ResponseEntity<String> saveVehicle(@Valid @RequestBody VehicleDTO vehicle,
                                              @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        vehicleService.saveVehicle(vehicle,X_TenantId);
        return new ResponseEntity<>("New Vehicle SuccessFully", HttpStatus.CREATED);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String id, @RequestHeader("X-Tenant-Id")  Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        Vehicle vehicle = vehicleService.getVehicle(id, X_TenantId);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }


    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<String> removeVehicle(@PathVariable String id, @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        return new ResponseEntity<>(vehicleService.removeVehicle(id,X_TenantId), HttpStatus.OK);
    }

    @PatchMapping("/vehicles/{id}")
    public ResponseEntity<String> updatedVehicle(@PathVariable String id,
                                                 @RequestBody VehicleDTO vehicle,
                                                 @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId) throws TenantAccessDeniedException, ResourceNotFoundException {
        return new ResponseEntity<>(vehicleService.updatedVehicle(id, vehicle, X_TenantId), HttpStatus.OK);
    }
    @GetMapping(value = "/vehicles")
    public ResponseEntity<List<Vehicle>> getVehicleFilter(
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "priceMin", required = false) Double priceMin,
            @RequestParam(value = "priceMax", required = false) Double priceMax,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "price,asc") String sort,
            @RequestHeader(value = "X-Tenant-Id") Integer X_TenantId
    ) throws ResourceNotFoundException {

        return new ResponseEntity<>(
                vehicleService.getVehiclesByFilter(
                        priceMin,
                        priceMax,
                        status,
                        model,
                        X_TenantId,
                        pageNo,
                        pageSize,
                        sort
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/vehicles/subscriptionType")
    public ResponseEntity<List<Vehicle>> getVehicleSubscriptionType(
            @RequestParam(value = "subscriptionType", required = false) SubscriptionType subscriptionType,
            @RequestHeader(value = "X-Tenant-Id") Integer X_TenantId
    ) throws ResourceNotFoundException {
        return new ResponseEntity<>(vehicleService.getVehicleSubscriptionType(subscriptionType,X_TenantId),HttpStatus.OK);
    }
}
