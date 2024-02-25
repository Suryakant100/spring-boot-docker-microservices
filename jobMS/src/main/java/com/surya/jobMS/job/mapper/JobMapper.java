package com.surya.jobMS.job.mapper;

import com.surya.jobMS.job.Job;
import com.surya.jobMS.job.dto.JobDTO;
import com.surya.jobMS.job.external.Company;
import com.surya.jobMS.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompany(Job job, Company company, List<Review> reviews){
        JobDTO jobWithCompanyDTO = new JobDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReviews(reviews);
        return jobWithCompanyDTO;
    }
}
