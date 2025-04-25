package samucabank.apibank.infrastructure.viacep;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import samucabank.apibank.domain.model.Address;
import samucabank.apibank.domain.service.customException.user.ZipCodeDoesNotExistException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViaCepService implements ViaCepClient {

    private final ViaCepClient viaCepClient;

    @Cacheable(value = "cepInfo", key = "#cep")
    public Address saveAddressByCep(String cep, String number) {
        Address address = this.getCepInfo(cep);
        address.setNumero(number);
        return address;
    }

    @Override
    public Address getCepInfo(String cep) {
        return Optional.of(viaCepClient)
            .map(it -> it.getCepInfo(cep))
            .orElseThrow(() -> new ZipCodeDoesNotExistException("Unable to find the zip code entered"));
    }
}