package com.obu.tech.poba.textbook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "textbook_phase", schema = "poba")
public class TextbookPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long textbookPhaseId;
    private long textbookId;
    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล งวดที่")
    private int textbookPhase;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนเงิน")
    private int textbookAmount;

    @NotNull(message = "โปรดเลือก ทำเรื่องเบิก/นำส่งเงิน")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookWithdraw;
}
