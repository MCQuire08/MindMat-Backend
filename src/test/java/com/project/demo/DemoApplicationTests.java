package com.project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
