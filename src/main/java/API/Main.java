package API;

import Sturtup.Helper;
import Sturtup.ProjectSetup;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args)
    {
        ProjectSetup s = new ProjectSetup();
        s.SeedData();
    }
}
