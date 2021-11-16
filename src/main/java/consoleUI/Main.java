package consoleUI;

import domain.User;
import infrastructure.Services.PSQL.PSQLUserService;
import utils.ServiceAllowedOperation;

import java.lang.reflect.Method;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {


        PSQLUserService us = new PSQLUserService();
        Class<?> clazz = PSQLUserService.class;
        Method[] methods = clazz.getMethods();
        for (var a: methods)
            if(a.isAnnotationPresent(ServiceAllowedOperation.class)) {
                ServiceAllowedOperation s = a.getAnnotation(ServiceAllowedOperation.class);
                System.out.println(s.AllowedOperationDescription() + s.AllowedOperationName());
            }
        List<User> d = us.getUserByFullName("d");


            for (var t: d) {
                System.out.println(t.getName());
            }
    }
}
