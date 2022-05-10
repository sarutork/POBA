package com.obu.tech.poba.training;

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
@Table(name = "training_phase", schema = "poba")
public class TrainingPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trainingPhaseId;
    private long trainingId;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล งวดที่")
    private int trainingPhase;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนเงิน")
    private Double trainingAmount;

    @NotNull(message = "โปรดเลือก ทำเรื่องเบิก/นำส่งเงิน")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingWithdraw;
}
