package com.API.Empresa.Exception;

import java.time.LocalDateTime;

public record ErroResposta(

        String mensagem,
        int status,
        LocalDateTime dataHora

) {
}
