package Sturtup;

import Repository.*;

import java.util.function.Consumer;

public class DependencyInjectionImitator {
    private Helper _helper = new Helper();

    public Consumer<String> getConsolePrint() {
        return System.out::println;
    }

    public Helper get_helper() {
        return _helper;
    }

    public UserRepository get_UserRepository() {
        return new UserRepository();
    }

    public VehicleRepository get_VehicleRepository() {
        return new VehicleRepository();
    }

    public PersonalInfoRepository get_PersonalInfoRepository() {
        return new PersonalInfoRepository();
    }
}
