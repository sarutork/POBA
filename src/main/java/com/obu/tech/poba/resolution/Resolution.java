package com.obu.tech.poba.resolution;

import com.obu.tech.poba.utils.upload.Upload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board_resolution", schema = "poba")
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bordId;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล การประชุมครั้งที่")
    private int bordNo1;
    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล การประชุมครั้งที่ /")
    private int bordNo2;

    @NotNull(message = "โปรดเลือก วัน เดือน ปี")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bordDate;

    @Size(min = 1, max = 255, message = "โปรดเลือก การประชุม")
    private String bordType;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล รายงานการประชุม")
    private String bordAttach;

    private String noteOfBoard;

    @Transient
    private List<Upload> uploads;

    @Transient
    @ToString.Exclude
    private Long[] filesToKeep;

    @Transient
    @ToString.Exclude
    private MultipartFile[] newFiles;

}
