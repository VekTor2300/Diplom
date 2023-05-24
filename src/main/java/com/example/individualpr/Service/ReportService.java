package com.example.individualpr.Service;

import com.example.individualpr.Models.RequestSupport;
import com.example.individualpr.Repos.RequestSupportRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReportService {
    @Autowired
    private RequestSupportRepos requestSupportRepos;

    public List<RequestSupport> listAll() {
        return requestSupportRepos.findAll(Sort.by("dateApp").ascending());
    }
}
