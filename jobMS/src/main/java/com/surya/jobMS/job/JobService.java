package com.surya.jobMS.job;

import com.surya.jobMS.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    public List<JobDTO> findAll();
    public boolean createJobs(Job job);

    public JobDTO getJobById(Long id);

    public boolean deleteJobyId(Long id);

    public boolean updateJobById(Long id,Job updatedJob);

}
