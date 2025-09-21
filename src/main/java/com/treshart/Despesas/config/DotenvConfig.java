package com.treshart.Despesas.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig
{
    static
    {
        Dotenv env = Dotenv
                .configure()
                .ignoreIfMissing()
                .load();

        env.entries()
                .forEach(dotenvEntry ->
                    {System
                    .setProperty
                        (dotenvEntry
                        .getKey(),
                        dotenvEntry
                        .getValue());
                    });
    }
}
