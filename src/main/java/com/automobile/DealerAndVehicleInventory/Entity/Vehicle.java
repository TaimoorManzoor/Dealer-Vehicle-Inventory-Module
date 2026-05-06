package com.automobile.DealerAndVehicleInventory.Entity;

import com.automobile.DealerAndVehicleInventory.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "vehicle_table")
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Tenant Id is Required")
    @Column(name = "tenant_id")
    private Integer tenantId;

    @NotNull(message = "Model is Required")
    private String model;

    @NotNull(message = "Price is Required")
    @Min(value = 0, message = "Price Can not be Negative")
    private Double price;

    @NotNull(message = "Status IS Required")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Dealer is Required")
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "dealer_Id" )
    private Dealer dealer;

    @CreationTimestamp
    @Column(name ="vehicle_created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name ="vehicle_updated_at", insertable = false)
    private LocalDateTime updatedAt;

}
