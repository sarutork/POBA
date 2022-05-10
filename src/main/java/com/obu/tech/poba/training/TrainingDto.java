package com.obu.tech.poba.training;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TrainingDto {

    private long trainingId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    private String prefix2;
    private String prefixOther2;
    private String name2;
    private String surname2;

    private String prefix3;
    private String prefixOther3;
    private String name3;
    private String surname3;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สถานะ")
    private String trainingStatus;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อโครงการ")
    private String trainingName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สถานที่จัดการอบรม")
    private String trainingLocation;

    @NotNull(message = "โปรดเลือก วันที่จัดเริ่มต้น")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingDateFrom;

    @NotNull(message = "โปรดเลือก เวลาจัดเริ่มต้น")
    private String trainingTimeFrom;

    @NotNull(message = "โปรดเลือก วันที่จัดถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingDateTo;

    @NotNull(message = "โปรดเลือก เวลาจัดถึง")
    private String trainingTimeTo;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนวันให้บริการ")
    private int trainingTotalDay;

    @Size(min = 1, max = 255, message = "โปรดเลือก ลักษณะกิจกรรม")
    private String trainingType;

    private String trainingAnnounce;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล งบประมาณ")
    private double trainingBudget;

    @NotNull(message="กรุณาตรวจสอบข้อมูล งวดที่")
    private List<@Valid TrainingPhase> phases;

    private double trainingAmountTotal;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงานให้ทุน/ร่วมจัด")
    private String trainingJoin;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ผู้เข้าร่วม (คนไทย)")
    private int trainingThai;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ผู้เข้าร่วม (ต่างชาติ)")
    private int trainingForeign;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ผู้เข้าร่วม (รวม)")
    private int trainingTotalPerson;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับกิจกรรม")
    private String trainingLevel;
}
