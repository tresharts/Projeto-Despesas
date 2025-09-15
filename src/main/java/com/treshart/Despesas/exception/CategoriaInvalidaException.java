package com.treshart.Despesas.exception;

public class CategoriaInvalidaException extends RuntimeException
{
    public CategoriaInvalidaException()
    {
        super("Categoria inválida ou não definida.");
    }

    public CategoriaInvalidaException(String message)
    {
        super(message);
    }
}