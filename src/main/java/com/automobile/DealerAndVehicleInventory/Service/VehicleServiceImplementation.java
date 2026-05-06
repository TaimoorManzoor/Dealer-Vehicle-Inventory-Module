package com.automobile.DealerAndVehicleInventory.Service;

import com.automobile.DealerAndVehicleInventory.DataTransferObject.VehicleDTO;
import com.automobile.DealerAndVehicleInventory.Entity.Dealer;
import com.automobile.DealerAndVehicleInventory.Entity.Vehicle;
import com.automobile.DealerAndVehicleInventory.Enum.Status;
import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import com.automobile.DealerAndVehicleInventory.Repository.DealerRepository;
import com.automobile.DealerAndVehicleInventory.Repository.VehicleRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImplementation implements VehicleService {
    private VehicleRepository vehicleRepository;
    private DealerRepository dealerRepository;

    public VehicleServiceImplementation(VehicleRepository vehicleRepository, DealerRepository dealerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.dealerRepository = dealerRepository;
    }


    @Override
    public Vehicle saveVehicle(VehicleDTO vehicle, Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        Dealer dealer =dealerRepository.findById(vehicle.getDealerId()).
                orElseThrow(() ->
                        new ResourceNotFoundException("Dealer not found for the provided dealer id "));

        Vehicle vehicle1=new Vehicle();
        vehicle1.setStatus(vehicle.getStatus());
        vehicle1.setModel(vehicle.getModel());
        vehicle1.setPrice(vehicle.getPrice());
        vehicle1.setTenantId(X_TenantId);
        if (dealer.getTenantId()==X_TenantId)
            vehicle1.setDealer(dealer);
        else {
            throw new TenantAccessDeniedException(
                    "Access denied. The specified dealer does not belong to the provided tenant."
            );
        }

        return vehicleRepository.save(vehicle1);
    }

    @Override
    public Vehicle getVehicle(String id, Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isEmpty()) {
            throw new ResourceNotFoundException("Vehicle Not Found");
        }else {
            Vehicle vehicle1=vehicle.get();
            if (vehicle1.getTenantId().equals(X_TenantId)){
                return vehicle.get();
            }else {
                throw new TenantAccessDeniedException("Access denied for this tenant "+X_TenantId);
            }
        }

    }

    @Override
    public String removeVehicle(String id,Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        Optional<Vehicle> vehicle=vehicleRepository.findById(id);

        if (vehicle.isEmpty()) {
            throw new ResourceNotFoundException("Vehicle Not Found");
        }else {
            Vehicle vehicle1=vehicle.get();
            if (vehicle1.getTenantId().equals(X_TenantId)){
                 vehicleRepository.deleteById(id);
                 return "Vehicle Deleted SuccessFully";
            }else {
                throw new TenantAccessDeniedException("Access denied for this tenant "+X_TenantId);
            }
        }

    }

    public String updatedVehicle(String Id, VehicleDTO vehicle, Integer X_TenantId) throws TenantAccessDeniedException, ResourceNotFoundException {
        Optional<Vehicle> vehicleLoad = vehicleRepository.findById(Id);
        if (vehicleLoad.isEmpty()) {
            throw  new ResourceNotFoundException("Vehicle data is not exist");
        }

        Vehicle vehicleResponse = vehicleLoad.get();

        if (!X_TenantId.equals(vehicleLoad.get().getTenantId())) {
            throw new TenantAccessDeniedException("Access denied for this tenant " + X_TenantId);
        }

        if (vehicle.getStatus() != null) {
            vehicleResponse.setStatus(vehicle.getStatus());
        }
        if (vehicle.getModel() != null) {
            vehicleResponse.setModel(vehicle.getModel());
        }
        if (vehicle.getPrice() != null) {
            vehicleResponse.setPrice(vehicle.getPrice());
        }

        vehicleRepository.save(vehicleResponse);
        return "Dealer Info Updated SuccessFully";
    }

    @Override
    public List<Vehicle> getVehiclesByFilter(
            Double priceMin,
            Double priceMax,
            Status status,
            String model,
            Integer X_TenantId,
            Integer pageNo,
            Integer pageSize,
            String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField)
        );

        Page<Vehicle> vehiclePage = vehicleRepository.getVehicleList(
                priceMin,
                priceMax,
                model,
                status,
                X_TenantId,
                pageable
        );

        return vehiclePage.getContent();
    }
    public List<Vehicle> getVehicleSubscriptionType(
            SubscriptionType subscriptionType,
            Integer X_TenantId) {
        return vehicleRepository.getVehicleSubscriptionType(X_TenantId,subscriptionType.toString());
    }


}
