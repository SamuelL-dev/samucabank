package samucabank.apibank.api.dtos.request;


import jakarta.validation.constraints.NotBlank;

public record AddressRequest (@NotBlank  String cep, @NotBlank  String numero){}

