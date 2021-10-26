package Sturtup;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.util.Optional;

import static org.testng.Assert.*;

public class HelperTest {

    @Test
    public void testGetFileThrowsErrorIfFileDoesNotContainRequiredInformation() {
        Helper h = new Helper();

        assertThrows(NullPointerException.class,()-> h.getFile("wrongPath").get());
    }

}