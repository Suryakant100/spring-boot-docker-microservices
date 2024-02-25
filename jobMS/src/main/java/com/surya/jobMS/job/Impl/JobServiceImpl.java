package com.surya.jobMS.job.Impl;


import com.surya.jobMS.job.Job;
import com.surya.jobMS.job.JobRepository;
import com.surya.jobMS.job.JobService;
import com.surya.jobMS.job.clients.CompanyClient;
import com.surya.jobMS.job.clients.ReviewClient;
import com.surya.jobMS.job.dto.JobDTO;
import com.surya.jobMS.job.external.Company;
import com.surya.jobMS.job.external.Review;
import com.surya.jobMS.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    int attempt = 0;
    @Override
//    @CircuitBreaker(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
//    @RateLimiter(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempts"+ ++attempt);
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> message = new ArrayList<>();
        message.add("Something Wrong, We are working on it, Please try after some time...");
        return message;
    }

    private JobDTO convertToDTO(Job job){
         /*
        * Instead of using RestTemplate we use openfeign which is provided by Spring Cloud only which
        * is alternative of RestTemplate and use for intercommunication for microservices. openfeign also
        * provide in-build LoadBalanced mechanism, so we don't have to take care of Load Balancing.

        Company company=restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(), Company.class);

        try {
            ResponseEntity<List<Review>> reviewsResponse= restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId="+job.getCompanyId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    });
            List<Review> reviews = reviewsResponse.getBody();
            JobDTO jobDTO = JobMapper.mapToJobWithCompany(job,company,reviews);
            return jobDTO;
        }catch (Exception e){
        JobDTO jobDTO = JobMapper.mapToJobWithCompany(job,company,null);

        return jobDTO;
        }

         */
        Company company = companyClient.getCompany(job.getCompanyId());
        try {
            List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
            return  JobMapper.mapToJobWithCompany(job,company,reviews);
        }catch (Exception e){
            return  JobMapper.mapToJobWithCompany(job,company,null);
        }
    }

    @Override
    public boolean createJobs(Job job) {
        try{
            Company company = companyClient.getCompany(job.getCompanyId());
            jobRepository.save(job);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public JobDTO getJobById(Long id) {
       Job job = jobRepository.findById(id).orElse(null);
       if(job==null){
           return null;
       }
       JobDTO jobDTO = convertToDTO(job);

        return jobDTO;
    }

    @Override
    public boolean deleteJobyId(Long id) {
        Optional<Job> jobs = jobRepository.findById(id);
        if (jobs.isPresent()){
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
