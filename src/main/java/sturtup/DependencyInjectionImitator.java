package sturtup;

import dao.*;

import java.util.function.Consumer;

public class DependencyInjectionImitator {
    private Helper helper = new Helper();

    public Consumer<String> getConsolePrint() {
        return System.out::println;
    }

    public Helper getHelper() {
        return helper;
    }

    public static DatabaseUserRepository get_UserRepository() {
        return new DatabaseUserRepository();
    }

    public static DatabaseVehicleRepository get_VehicleRepository() {
        return new DatabaseVehicleRepository();
    }

    public static DatabasePersonalInfoRepository get_PersonalInfoRepository() {
        return new DatabasePersonalInfoRepository();
    }
}
