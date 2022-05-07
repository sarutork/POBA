package com.obu.tech.poba;

import com.obu.tech.poba.utils.YearGeneratorUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void yearGenTest(){
		YearGeneratorUtils yearGen = new YearGeneratorUtils();
		List<Integer> years = yearGen.genYears(10,10);
		for(int year: years){
			System.out.println(year);
		}

	}

}
