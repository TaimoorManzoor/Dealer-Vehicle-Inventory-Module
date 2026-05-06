package com.automobile.DealerAndVehicleInventory.Entity;

import com.automobile.DealerAndVehicleInventory.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_table")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer tenantId;
}
