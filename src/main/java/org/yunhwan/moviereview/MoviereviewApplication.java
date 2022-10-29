package org.yunhwan.moviereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing // BaseEntity의 동작을 위해 설정
//@EnableJpaRepositories(basePackages = "org.yunhwan.moviereview.repository", repositoryImplementationPostfix = "Custom")
public class MoviereviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviereviewApplication.class, args);
    }

}
