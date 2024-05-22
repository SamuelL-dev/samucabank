package samucabank.apibank.infrastructure.viacep;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import samucabank.apibank.domain.model.Address;
import samucabank.apibank.domain.service.customException.user.ZipCodeDoesNotExistException;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    @Cacheable(value = "cepInfo", key = "#cep")
    public Address saveAddressFromCep(String cep, String number) {
        Address response = viaCepClient.getCepInfo(cep);

        if (response != null) {
            response.setNumero(number);
            return response;
        } else {
            throw new ZipCodeDoesNotExistException("Unable to find the zip code entered.");
        }
    }
}