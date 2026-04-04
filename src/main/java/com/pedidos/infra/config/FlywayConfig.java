package com.pedidos.infra.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig{
        public static void migrate() {
            Flyway flyway = Flyway.configure()
                    .dataSource(
                            "jdbc:postgresql://localhost:5432/postgres",
                            "postgres",
                            "postgres"
                    )
                    .locations("classpath:db/migration")
                    .load();

            flyway.migrate();
    }
}
