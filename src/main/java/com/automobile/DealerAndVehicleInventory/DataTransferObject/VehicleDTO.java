package com.automobile.DealerAndVehicleInventory.DataTransferObject;

import com.automobile.DealerAndVehicleInventory.Entity.Dealer;
import com.automobile.DealerAndVehicleInventory.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleDTO {

    @NotNull(message = "Model is Required")
    private String model;

    @NotNull(message = "Price is Required")
    @Min(value = 0, message = "Price Can not be Negative")
    private Double price;

    @NotNull(message = "Status IS Required")
    private Status status;

    @NotNull(message = "Dealer Id  is Required")
    private String dealerId;
}
