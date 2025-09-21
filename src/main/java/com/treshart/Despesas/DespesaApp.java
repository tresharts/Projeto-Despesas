package com.treshart.Despesas;

import com.treshart.Despesas.config.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DespesaApp
{
    public static void main(String[] args)
    {
        new DotenvConfig();
        SpringApplication.run(DespesaApp.class, args);
    }
}
