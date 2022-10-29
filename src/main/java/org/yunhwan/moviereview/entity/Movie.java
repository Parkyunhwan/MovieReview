package org.yunhwan.moviereview.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate openDate;

    private String rating;

    private Long runningTime;

    private String country;

    @OneToMany
    @JoinColumn(name = "inum", insertable = false, updatable = false)
    private List<MovieImage> movieImages;

    public void changeTitle(String title, LocalDate openDate, Long runningTime, String country, String rating) {
        this.title = title;
        this.openDate = openDate;
        this.runningTime = runningTime;
        this.country = country;
        this.rating = rating;
    }
}
