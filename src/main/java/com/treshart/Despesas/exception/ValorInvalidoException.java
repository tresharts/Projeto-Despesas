package com.treshart.Despesas.exception;

public class ValorInvalidoException extends RuntimeException
{
    public ValorInvalidoException() {
        super("Valor inválido.");
    }

    public ValorInvalidoException(String message) {
        super(message);
    }
}