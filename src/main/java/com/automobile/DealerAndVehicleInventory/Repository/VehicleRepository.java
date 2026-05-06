package com.automobile.DealerAndVehicleInventory.Repository;

import com.automobile.DealerAndVehicleInventory.Entity.Vehicle;
import com.automobile.DealerAndVehicleInventory.Enum.Status;
import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String> {

        @Query(value =
                "SELECT * FROM vehicle_table v WHERE v.tenant_id = :X_TenantId " +
                        "AND (:model IS NULL OR v.model = :model) " +
                        "AND (:priceMin IS NULL OR v.price >= :priceMin) " +
                        "AND (:priceMax IS NULL OR v.price <= :priceMax) " +
                        "AND (:status IS NULL OR v.status = :status)",
                countQuery =
                        "SELECT COUNT(*) FROM vehicle_table v WHERE v.tenant_id = :X_TenantId " +
                                "AND (:model IS NULL OR v.model = :model) " +
                                "AND (:priceMin IS NULL OR v.price >= :priceMin) " +
                                "AND (:priceMax IS NULL OR v.price <= :priceMax) " +
                                "AND (:status IS NULL OR v.status = :status)",
                nativeQuery = true)
        Page<Vehicle> getVehicleList(
                @Param("priceMin") Double priceMin,
                @Param("priceMax") Double priceMax,
                @Param("model") String model,
                @Param("status") Status status,
                @Param("X_TenantId") Integer X_TenantId,
                Pageable pageable
        );

        @Query(value = "SELECT vt.* FROM vehicle_table vt JOIN dealer_table dt\n" +
                "ON vt.dealer_id=dt.id WHERE (vt.tenant_id=:X_TenantId AND dt.tenant_id=:X_TenantId \n" +
                "AND dt.subscription_type=:subscriptionType)", nativeQuery = true)
        List<Vehicle> getVehicleSubscriptionType(@Param("X_TenantId") Integer X_TenantId,
                                                 @Param("subscriptionType") String subscriptionType);

}

