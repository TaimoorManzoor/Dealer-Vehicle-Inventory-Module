package com.automobile.DealerAndVehicleInventory.Service;

import com.automobile.DealerAndVehicleInventory.DataTransferObject.DealerDTO;
import com.automobile.DealerAndVehicleInventory.DataTransferObject.VehicleDTO;
import com.automobile.DealerAndVehicleInventory.Entity.Vehicle;
import com.automobile.DealerAndVehicleInventory.Enum.Status;
import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleService{

     Vehicle saveVehicle(VehicleDTO vehicle, Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException;

     Vehicle getVehicle(String id,  Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException;

     String removeVehicle(String id, Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException;

     String updatedVehicle(String Id, VehicleDTO vehicle, Integer X_TenantId) throws TenantAccessDeniedException, ResourceNotFoundException;

     List<Vehicle> getVehiclesByFilter(
             Double priceMin,
             Double priceMax,
             Status status,
             String model,
             Integer currentTenantId,
             Integer pageNo,
             Integer pageSize,
             String sort
     );

      List<Vehicle> getVehicleSubscriptionType(
             SubscriptionType subscriptionType,
             Integer X_TenantId
      );
}
