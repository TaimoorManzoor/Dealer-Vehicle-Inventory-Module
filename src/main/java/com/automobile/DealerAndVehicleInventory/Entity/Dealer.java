package com.automobile.DealerAndVehicleInventory.Entity;

import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dealer_table")
@Data
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "tenantId is Required")
    @Column(name = "tenant_id")
    private Integer tenantId;

    @NotBlank(message = "Name can not be Blank")
    private String name;

    @Email(message = "Please Enter the Correct Email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @CreationTimestamp
    @Column(name ="dealer_created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name ="dealer_updated_at", insertable = false)
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "dealer")
    @ToString.Exclude
    @JsonIgnore
    private List<Vehicle> vehicle=new ArrayList<>();

}
