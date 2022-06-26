package com.obu.tech.poba.press_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "press_info", schema = "poba")
public class Press {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pressId;

    @NotEmpty(message = "โปรดเลือก คำนำหน้า และ ชื่อ-นามสกุล ")
    private String persNo;

    @Transient
    private String prefix;
    @Transient
    private String name;

    private String pressName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pressDate;

    private String pressTopic;

    private String pressSiteRef;

    private String pressPrint;

    private String pressTv;

    private String pressSatellite;

    private String pressOnline;

    private String guestPrefix1;
    private String guestPrefixOther1;

    private String guestName1;
    private String guestSurname1;

    private String guestPrefix2;
    private String guestPrefixOther2;

    private String guestName2;
    private String guestSurname2;

    private String guestPrefix3;
    private String guestPrefixOther3;

    private String guestName3;
    private String guestSurname3;

}
