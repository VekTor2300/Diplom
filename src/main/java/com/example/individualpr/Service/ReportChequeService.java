package com.example.individualpr.Service;

import com.example.individualpr.Models.Cheque;
import com.example.individualpr.Repos.ChequeRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReportChequeService {//метод для сортировке времени продажи в екселе
    @Autowired
    private ChequeRepos chequeRepos;

    public List<Cheque> listAll() {
        return chequeRepos.findAll(Sort.by("timesell").ascending());
    }
}
