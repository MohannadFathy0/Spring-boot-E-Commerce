package com.fog.e_commerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ECommerceApplicationTests {

	Calculator calculator = new Calculator();
	@Test
	void contextLoads() {

	}

	@Test
	void itAddTwoNumbers(){
		int result = calculator.add(1,1);
		assertThat(result).isEqualTo(2);
	}

	class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}

}
