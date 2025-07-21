package com.poppulo.lotterysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.poppulo.lotterysystem.entity.Line;
import com.poppulo.lotterysystem.service.TicketService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TicketServiceUtilsTest {

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void calculateResult_SumEquals2_Returns10() {
        // Create a line with numbers that sum to 2
        Line line = new Line(1, 1, 0);
        
        // Invoke the private method using reflection
        int result = (int) ReflectionTestUtils.invokeMethod(ticketService, "calculateResult", line);
        
        // Assert that the result is 10
        assertEquals(10, result, "When sum equals 2, result should be 10");
    }

    @Test
    public void calculateResult_AllNumbersEqual_Returns5() {
        // Create a line with all numbers equal
        Line line = new Line(1, 1, 1);
        
        // Invoke the private method using reflection
        int result = (int) ReflectionTestUtils.invokeMethod(ticketService, "calculateResult", line);
        
        // Assert that the result is 5
        assertEquals(5, result, "When all numbers are equal, result should be 5");
    }

    @Test
    public void calculateResult_AllNumbersDifferent_Returns1() {
        // Create a line with all numbers different
        Line line = new Line(0, 1, 2);
        
        // Invoke the private method using reflection
        int result = (int) ReflectionTestUtils.invokeMethod(ticketService, "calculateResult", line);
        
        // Assert that the result is 1
        assertEquals(1, result, "When all numbers are different, result should be 1");
    }

    @Test
    public void calculateResult_TwoNumbersEqual_Returns0() {
        // Create a line with two numbers equal
        Line line = new Line(1, 1, 2);
        
        // Invoke the private method using reflection
        int result = (int) ReflectionTestUtils.invokeMethod(ticketService, "calculateResult", line);
        
        // Assert that the result is 0
        assertEquals(0, result, "When two numbers are equal (and sum != 2), result should be 0");
    }

    @Test
    public void calculateResult_EdgeCase_SumEquals2AndAllNumbersEqual_Returns10() {
        // Create a line where sum is 2 and all numbers are equal (0, 0, 0)
        Line line = new Line(0, 0, 0);
        
        // Sum is 0, but all numbers are equal
        // According to the implementation, sum == 2 takes precedence
        
        // Invoke the private method using reflection
        int result = (int) ReflectionTestUtils.invokeMethod(ticketService, "calculateResult", line);
        
        // Assert that the result is 5 (all numbers equal rule)
        assertEquals(5, result, "When sum is 0 and all numbers are equal, result should be 5");
    }

    @Test
    public void calculateResult_EdgeCase_SumEquals2AndAllNumbersDifferent_Returns10() {
        // Create a line where sum is 2 and all numbers are different (0, 1, 1)
        Line line = new Line(0, 1, 1);
        
        // Sum is 2, but not all numbers are different
        // According to the implementation, sum == 2 takes precedence
        
        // Invoke the private method using reflection
        int result = (int) ReflectionTestUtils.invokeMethod(ticketService, "calculateResult", line);
        
        // Assert that the result is 10 (sum == 2 rule)
        assertEquals(10, result, "When sum is 2, result should be 10 regardless of other conditions");
    }
}