package samucabank.apibank.infrastructure.viacep;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import samucabank.apibank.domain.model.Address;
import samucabank.apibank.domain.service.customException.user.ZipCodeDoesNotExistException;

@Service
@RequiredArgsConstructor
public class ViaCepClientImpl {

    private final ViaCepClient viaCepClient;
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