package com.treshart.Despesas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler
{
    @ExceptionHandler (IdNaoEncontradoException.class)
    public ResponseEntity<String> handleId(IdNaoEncontradoException exception)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler (ValorInvalidoException.class)
    public ResponseEntity<String> handleValor(ValorInvalidoException exception)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler (CategoriaInvalidaException.class)
    public ResponseEntity<String> handleCategoria(CategoriaInvalidaException exception)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}