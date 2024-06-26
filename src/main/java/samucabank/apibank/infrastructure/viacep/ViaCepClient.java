package samucabank.apibank.infrastructure.viacep;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import samucabank.apibank.domain.model.Address;

@FeignClient(name = "viacep", url = "https://viacep.com.br")
public interface ViaCepClient {
    @GetMapping("/ws/{cep}/json/")
    Address getCepInfo(@PathVariable("cep") String cep);
}
