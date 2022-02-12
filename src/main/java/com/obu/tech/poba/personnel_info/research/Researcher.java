package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.utils.upload.Upload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "researcher", schema = "poba")
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    @Pattern(regexp = "(Mr(s)?\\.|Miss)", message = "โปรดเลือก คำนำหน้า ที่ถูกต้อง")
    private String prefix;

    @Size(min = 1, max = 255, message = "โปรดป้อน ชื่อ (1 - 255)")
    private String name;

    private String surname;

    @Column(name = "r_status")
    @Pattern(regexp = "(อาจารย์|นักวิจัย)", message = "โปรดเลือก สถานะ ที่ถูกต้อง")
    private String status;

    @Column(name = "r_type")
    @Pattern(regexp = "(Part time|Full time|นักวิจัยหลังปริญญาเอก \\(Postdoctoral\\))", message = "โปรดเลือก ประเภท ที่ถูกต้อง")
    private String type;

    // TODO: Validate start & end days' format, end >= start
    @NotNull(message = "โปรดเลือก วันที่เริ่มต้น")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workStartDate;

    @NotNull(message = "โปรดเลือก วันที่สิ้นสุด")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workEndDate;

    private String noteOfWork;
    private String teacher1;
    private String teacher2;
    private String subSegment;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "reference_key", referencedColumnName = "staffId") // upload.reference_key = researcher.staff_id
    // @Where(clause = "upload_group = 'researcher'")
    // @OrderBy("upload_id")
    @Transient
    private List<Upload> uploads;

    @Transient
    @ToString.Exclude
    private Long[] filesToKeep;

    @Transient
    @ToString.Exclude
    private MultipartFile[] newFiles;
}
