package com.kiago.api.controller;

import com.kiago.api.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kiago.api.service.CompanyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        return companyService.updateCompany(id, companyDetails);
    }
}
