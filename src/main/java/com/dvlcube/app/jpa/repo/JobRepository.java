package com.dvlcube.app.jpa.repo;

import com.dvlcube.app.manager.data.JobBean;

import org.springframework.stereotype.Repository;

import com.dvlcube.app.jpa.BasicRepository;
import com.dvlcube.app.jpa.DvlRepository;

@Repository
public interface JobRepository extends DvlRepository<JobBean, Long>, BasicRepository<JobBean, Long> {
    
}
