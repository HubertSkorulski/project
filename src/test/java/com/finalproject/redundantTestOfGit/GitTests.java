package com.finalproject.redundantTestOfGit;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitTests {

    @Test
    void revertTest() {
        //Given
        int someNumber = 1;
        //When
        //Then
        Assertions.assertEquals(1,someNumber);
    }

}
