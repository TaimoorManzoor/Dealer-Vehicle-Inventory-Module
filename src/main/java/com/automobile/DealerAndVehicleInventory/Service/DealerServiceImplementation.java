package com.automobile.DealerAndVehicleInventory.Service;

import com.automobile.DealerAndVehicleInventory.DataTransferObject.DealerDTO;
import com.automobile.DealerAndVehicleInventory.Entity.Dealer;
import com.automobile.DealerAndVehicleInventory.Enum.Role;
import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Repository.DealerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DealerServiceImplementation implements DealerService{

    private DealerRepository dealerRepository;

    public DealerServiceImplementation(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    @Override
    public Dealer saveDealer(DealerDTO dealerDTO,Integer tenantId) {

        Dealer dealer=new Dealer();
        dealer.setName(dealerDTO.getName());
        dealer.setEmail(dealerDTO.getEmail());
        dealer.setSubscriptionType(dealerDTO.getSubscriptionType());
        dealer.setTenantId(tenantId);

        Dealer saveDealer=dealerRepository.save(dealer);
        return saveDealer;
    }

    @Override
    public Dealer getDealers(String id,Integer X_TenantId) throws ResourceNotFoundException, TenantAccessDeniedException {
        Optional<Dealer> dealerOptional=dealerRepository.findById(id);
        if (dealerOptional.isEmpty()){
            throw new ResourceNotFoundException("Dealer Not Found");
        }else{
            Dealer dealer = dealerOptional.get();
            if (dealer.getTenantId().equals(X_TenantId)){
                return dealer;
            }else {
                throw new TenantAccessDeniedException("Access denied for this tenant "+X_TenantId);
            }
        }
    }

    @Override
    public List<Dealer> getAllDealers(Integer PageSize, Integer PageNo, Integer X_TenantId) {

        Dealer dealer = new Dealer();
        dealer.setTenantId(X_TenantId);
        Example<Dealer> dealWithFilter = Example.of(dealer);

        PageRequest request=PageRequest.of(PageNo-1,PageSize, Sort.by("tenantId"));
        Page<Dealer> dealerPage= dealerRepository.findAll(dealWithFilter,request);
        List<Dealer> dealerList=dealerPage.getContent();

        return dealerList;
    }

    @Override
    public String removeDealer(String id, Integer X_TenantId) throws TenantAccessDeniedException {
        Optional<Dealer> dealerOptional = dealerRepository.findById(id);
        if (!dealerOptional.isEmpty()) {
            Dealer dealer = dealerOptional.get();
            if (dealer.getTenantId().equals(X_TenantId)) {
                dealerRepository.deleteById(id);
                return "Delete SuccessFully";
            } else {
                throw new TenantAccessDeniedException("Access denied for this tenant " + X_TenantId);
            }
        } else {
            return "Dealer Is not exist";
        }
    }

    @Override
    public String updatedDealers(String Id, DealerDTO dealer, Integer X_TenantId) throws TenantAccessDeniedException, ResourceNotFoundException {
        Optional<Dealer> dealerLoad = dealerRepository.findById(Id);
        if (dealerLoad.isEmpty()) {
            throw new ResourceNotFoundException("Deal did not exist");
        }
        Dealer dealerResponse = dealerLoad.get();
        if (!X_TenantId.equals(dealerLoad.get().getTenantId())) {
            throw new TenantAccessDeniedException("Access denied for this tenant " + X_TenantId);
        }
        if (dealer.getName() != null) {
            dealerResponse.setName(dealer.getName());
        }
        if (dealer.getEmail() != null) {
            dealerResponse.setEmail(dealer.getEmail());
        }
        if (dealer.getSubscriptionType() != null) {
            dealerResponse.setSubscriptionType(dealer.getSubscriptionType());
        }
        dealerRepository.save(dealerResponse);
        return "Dealer Info Updated SuccessFully";
    }

    @Override
    public HashMap<String, Integer> countBySubscription(Integer tenantId) {
        HashMap<String, Integer> map=new HashMap<>();
        map.put(SubscriptionType.BASIC.toString(),0);
        map.put(SubscriptionType.PREMIUM.toString(),0);
        List<Object[]> countedBySubscription=dealerRepository.countBySubscription(tenantId);
        for (Object[] row:  countedBySubscription){
            String subscription = row[0].toString();
            Integer count = Integer.valueOf(row[1].toString());
            map.put(subscription, count);
        }
        return map;
    }

}
