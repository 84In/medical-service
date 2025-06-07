package com.vasd.medical_service.doctors.entities;
import com.vasd.medical_service.Enum.DayOfWeekEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "working_hours")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkingHour {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeekEnum dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}
