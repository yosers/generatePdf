package com.test.maybank.demo.dto;

import com.test.maybank.demo.dto.CommonRs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommonRsTest {

    @Test
    public void testNoArgsConstructor() {
        // Arrange & Act
        CommonRs<String> commonRs = new CommonRs<>();

        // Assert
        assertEquals(0, commonRs.getStatus());
        assertNull(commonRs.getMessage());
        assertNull(commonRs.getTotal());
        assertNull(commonRs.getData());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        int status = 200;
        String message = "Success";
        Long total = 10L;
        String data = "Test data";

        // Act
        CommonRs<String> commonRs = new CommonRs<>(status, message, total, data);

        // Assert
        assertEquals(status, commonRs.getStatus());
        assertEquals(message, commonRs.getMessage());
        assertEquals(total, commonRs.getTotal());
        assertEquals(data, commonRs.getData());
    }

    @Test
    public void testBuilder() {
        // Arrange
        int status = 200;
        String message = "Success";
        Long total = 10L;
        String data = "Test data";

        // Act
        CommonRs<String> commonRs = CommonRs.<String>builder()
                .status(status)
                .message(message)
                .total(total)
                .data(data)
                .build();

        // Assert
        assertEquals(status, commonRs.getStatus());
        assertEquals(message, commonRs.getMessage());
        assertEquals(total, commonRs.getTotal());
        assertEquals(data, commonRs.getData());
    }

    @Test
    public void testConstructorWithStatusAndMessage() {
        // Arrange
        int status = 200;
        String message = "Success";

        // Act
        CommonRs<String> commonRs = new CommonRs<>(status, message);

        // Assert
        assertEquals(status, commonRs.getStatus());
        assertEquals(message, commonRs.getMessage());
        assertNull(commonRs.getTotal());
        assertNull(commonRs.getData());
    }
}
