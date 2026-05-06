package com.automobile.DealerAndVehicleInventory.Repository;

import com.automobile.DealerAndVehicleInventory.Entity.User;
import com.automobile.DealerAndVehicleInventory.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

    boolean existsByTenantIdAndRole(Integer tenantId, Role role);

    boolean existsByTenantId(Integer tenantId);
}
