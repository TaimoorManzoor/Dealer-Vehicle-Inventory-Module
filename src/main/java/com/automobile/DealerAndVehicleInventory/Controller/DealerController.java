package com.automobile.DealerAndVehicleInventory.Controller;

import com.automobile.DealerAndVehicleInventory.DataTransferObject.DealerDTO;
import com.automobile.DealerAndVehicleInventory.Entity.Dealer;
import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import com.automobile.DealerAndVehicleInventory.Service.DealerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@Tag(name = "Dealer EndPoint")
public class DealerController {
    DealerService dealerService;

    public DealerController(DealerService dealerService) {
        this.dealerService = dealerService;
    }

    @PostMapping("/dealers")
    @Operation(summary = "Dealer Save Api")
    public ResponseEntity<String> saveDealers(@Valid @RequestBody DealerDTO dealerDTO,
                                              @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId){
            dealerService.saveDealer(dealerDTO,X_TenantId);
            return new ResponseEntity<>("New Dealer Added SuccessFully", HttpStatus.CREATED);
    }

    @GetMapping("/dealers/{id}")
    public ResponseEntity<Dealer> getDealers(@PathVariable String id,
                                             @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
            return new ResponseEntity<>(dealerService.getDealers(id,X_TenantId),HttpStatus.OK);
    }

    @GetMapping("/dealers/{pageSize}/{pageNo}")
    public ResponseEntity<List<Dealer>> getAllDealers(@PathVariable Integer pageSize,
                                                      @PathVariable  Integer pageNo,
                                                      @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId){
        return new ResponseEntity<>(dealerService.getAllDealers(pageSize, pageNo, X_TenantId),HttpStatus.OK);
    }

    @DeleteMapping("/dealers/{id}")
    public ResponseEntity<String> removeDealer(@PathVariable String id,
                                               @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId) throws TenantAccessDeniedException {
        return new ResponseEntity<>(dealerService.removeDealer(id,X_TenantId),HttpStatus.OK);
    }

    @PatchMapping("/dealers/{id}")
    public ResponseEntity<String> updatedDealers(@PathVariable String id, @RequestBody DealerDTO dealer,
                                                 @RequestHeader(value = "X-Tenant-Id", required = true) Integer X_TenantId) throws TenantAccessDeniedException, ResourceNotFoundException {
        return new ResponseEntity<>(dealerService.updatedDealers(id,dealer, X_TenantId),HttpStatus.OK);
    }


    @GetMapping("/admin/dealers/countBySubscription")
    public ResponseEntity<HashMap<String, Integer>> countBySubscription(@RequestParam(value = "tenantId",required = false) Integer tenantId){
        return new ResponseEntity<>(dealerService.countBySubscription(tenantId),HttpStatus.OK);
    }
}
