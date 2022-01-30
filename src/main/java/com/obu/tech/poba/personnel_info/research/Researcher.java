package com.obu.tech.poba.personnel_info.research;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "researcher", schema = "poba")
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    @Pattern(regexp = "(นาย\\.|นาง|นางสาว\\.|Mr(s)?\\.|Miss)", message = "Invalid prefix")
    private String prefix;

    @Size(max = 255, message = "Input name is too long")
    private String name;

    @Size(max = 255, message = "Input surname is too long")
    private String surname;

    @Column(name = "r_status")
    @Pattern(regexp = "(อาจารย์|นักวิจัย)", message = "Invalid status")
    private String status;

    @Column(name = "r_type")
    @Pattern(regexp = "(Part time|Full time|นักวิจัยหลังปริญญาเอก \\(Postdoctoral\\))", message = "Invalid type")
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workEndDate;

    private String docOfWork;
    private String noteOfWork;
}
