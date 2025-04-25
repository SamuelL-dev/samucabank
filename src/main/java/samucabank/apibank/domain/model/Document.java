package samucabank.apibank.domain.model;

import lombok.Getter;
import lombok.Setter;


import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Getter
@Setter
public class Document {

    private final Pattern CPF_REGEX = Pattern.compile("^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2})$");

    private final Pattern CNPJ_REGEX = Pattern.compile("^(\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}-?\\d{2})$");

    private final String cpf;

    private final String cnpj;

    public Document(String cpf, String cnpj) {
        this.validateCpf(cpf);
        this.validateCnpj(cnpj);

        this.cpf = cpf;
        this.cnpj = cnpj;
    }

    private String validateCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF cannot be null");
        }

        final String formattedCpf = cpf.replaceAll("[^\\d]", EMPTY);

        if (!CPF_REGEX.matcher(formattedCpf).matches()) {
            throw new IllegalArgumentException("CPF invalid format");
        }

        return formattedCpf;
    }

    private String validateCnpj(String cnpj) {
        if (cnpj == null) {
            throw new IllegalArgumentException("CNPJ cannot be null");
        }

        final String formattedCnpj = cnpj.replaceAll("[^\\d]", EMPTY);

        if (!CNPJ_REGEX.matcher(formattedCnpj).matches()) {
            throw new IllegalArgumentException("CNPJ invalid format");
        }

        return formattedCnpj;
    }
}
