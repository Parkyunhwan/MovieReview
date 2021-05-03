package org.yunhwan.moviereview.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    private LocalDate openDate;

    private String rating;

    private Long runningTime;

    private String country;

    public void changeTitle(String title, LocalDate openDate, Long runningTime, String country) {
        this.title = title;
        this.openDate = openDate;
        this.runningTime = runningTime;
        this.country = country;
    }
}
