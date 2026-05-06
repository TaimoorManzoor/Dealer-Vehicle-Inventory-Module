package com.automobile.DealerAndVehicleInventory.Service;

import com.automobile.DealerAndVehicleInventory.DataTransferObject.DealerDTO;
import com.automobile.DealerAndVehicleInventory.Entity.Dealer;
import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface DealerService {
    public Dealer saveDealer(DealerDTO  dealerDTO, Integer tenantId);

    public Dealer getDealers(String id, Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException;

    public List<Dealer> getAllDealers(Integer PageSize, Integer PageNo, Integer X_TenantId);

    public String removeDealer(String id, Integer X_TenantId) throws TenantAccessDeniedException;

    public  String updatedDealers(String Id, DealerDTO dealer, Integer X_TenantId) throws TenantAccessDeniedException, ResourceNotFoundException;

    public HashMap<String, Integer> countBySubscription(Integer tenantId);
}
