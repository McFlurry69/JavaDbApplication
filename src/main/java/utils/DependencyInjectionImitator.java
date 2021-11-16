package utils;

import infrastructure.Repository.PSQL.PSQLPersonalInfoRepository;
import infrastructure.Repository.PSQL.PSQLUserRepository;
import infrastructure.Repository.PSQL.PSQLVehicleRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.util.function.Consumer;

public class DependencyInjectionImitator {

    private ConnectionProvider helper = new ConnectionProvider();

    public Consumer<String> getConsolePrint() {
        return System.out::println;
    }

    public ConnectionProvider getHelper() {
        return helper;
    }

    public static PSQLUserRepository getUserRepository() {
        return new PSQLUserRepository();
    }

    public static PSQLVehicleRepository getVehicleRepository() {
        return new PSQLVehicleRepository();
    }

    public static PSQLPersonalInfoRepository getPersonalInfoRepository() {
        return new PSQLPersonalInfoRepository();
    }
}
