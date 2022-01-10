package com.obu.tech.poba.personnel_info;

import com.obu.tech.poba.personnel_info.research.Researcher;
import com.obu.tech.poba.personnel_info.research.ResearcherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@DataJpaTest
@Import(ResearcherService.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResearcherServiceTest {

    @Autowired
    private ResearcherService researcherService;

    @Test
    public void search() {
        researcherService.search("duy", LocalDate.now(), null)
                .forEach(System.out::println);
    }

    @Test
    public void findAll() {
        researcherService.findAll().forEach(System.out::println);
    }

    @Test
    public void findById() {
        System.out.println(researcherService.findById("1"));
    }

    @Test
    public void add() {
        LocalDate today = LocalDate.now();
        Researcher researcher = new Researcher();
        researcher.setPrefix("Mr.");
        researcher.setName("John");
        researcher.setSurname("Doe");
        researcher.setStatus("อาจารย์");
        researcher.setType("นักวิจัยหลังปริญญาเอก (Postdoctoral)");
        researcher.setWorkStartDate(today);
        researcher.setWorkEndDate(today.plus(12, ChronoUnit.MONTHS));
        researcher.setDocOfWork(null);
        researcher.setNoteOfWork(null);
        researcherService.save(researcher);
        System.out.println(researcher); // Generated staff_id
    }

    @Test
    public void update() {
        Researcher before = researcherService.findById("1");
        System.out.println("Before: " + before);

        before.setNoteOfWork("Test update");
        researcherService.save(before);

        Researcher after = researcherService.findById("1");
        System.out.println("After: " + after);

        Assertions.assertEquals(before.getNoteOfWork(), after.getNoteOfWork());
    }
}
