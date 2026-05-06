package com.automobile.DealerAndVehicleInventory.DataTransferObject;

import com.automobile.DealerAndVehicleInventory.Enum.SubscriptionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DealerDTO {

    @NotBlank(message = "Name can not be Blank")
    @NotEmpty(message = "Name can not be Empty")
    @NotNull(message = "Name is Required")
    private String name;

    @NotNull(message = "Email is Required")
    @Email(message = "Please Enter the Correct Email")
    private String email;

    @NotNull
    private SubscriptionType subscriptionType;

}
