package com.obu.tech.poba.personnel_info.research;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private long staffId;

    @Column(nullable = false)
   // @Pattern(regexp = "(นาย\\.|นาง|นางสาว\\.)", message = "Invalid prefix")
    private String prefix;

    @Column(nullable = false)
    @Size(max = 255, message = "Input name is too long")
    private String name;

    @Column(nullable = false)
    @Size(max = 255, message = "Input surname is too long")
    private String surname;

    @Column(name = "r_status", nullable = false)
    @Pattern(regexp = "(อาจารย์|นักวิจัย)", message = "Invalid r_status")
    private String status;

    @Column(name = "r_type", nullable = false)
    @Pattern(regexp = "(Part time|Full time|นักวิจัยหลังปริญญาเอก \\(Postdoctoral\\))", message = "Invalid r_type")
    private String type;

    @Column(nullable = false)
    private LocalDate workStartDate;

    @Column(nullable = false)
    private LocalDate workEndDate;

    private String docOfWork;
    private String noteOfWork;
}
