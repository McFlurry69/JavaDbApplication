package api;


import service.UserService;
import sturtup.DbSimpleOperations;
import sturtup.Helper;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
/*        Helper qd = new Helper();
        qd.getLog4JConfigFile();*/
        DbSimpleOperations d = new DbSimpleOperations();
        d.EnsureConnectionExists();
    }
}
