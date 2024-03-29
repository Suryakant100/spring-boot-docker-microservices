package com.surya.jobMS.job.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Review {
    private Long id;
    private String title;

    private String description;

    private double rating;
}
