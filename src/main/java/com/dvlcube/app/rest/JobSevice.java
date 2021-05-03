package com.dvlcube.app.rest;

import java.util.List;

import javax.validation.Valid;

import com.dvlcube.app.jpa.repo.JobRepository;
import com.dvlcube.app.manager.data.JobBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${dvl.rest.prefix}/jobs")
public class JobSevice {
    @Autowired
    private JobRepository repository;

    @GetMapping
    public ResponseEntity<List<JobBean>> get() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobBean> post(@Valid @RequestBody JobBean jobBean) {
        return new ResponseEntity<>(repository.save(jobBean), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
