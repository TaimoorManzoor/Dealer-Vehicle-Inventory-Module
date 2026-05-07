package com.automobile.DealerAndVehicleInventory.Repository;

import com.automobile.DealerAndVehicleInventory.Entity.Dealer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, String> {

    @Query(value = "SELECT d.subscription_type, COUNT(*) FROM dealer_table d " +
            "WHERE (:tenantId IS NULL OR d.tenant_id = :tenantId) " +
            "GROUP BY d.subscription_type",
            nativeQuery = true)
    List<Object[]> countBySubscription(@Param("tenantId") Integer tenantId);
}
