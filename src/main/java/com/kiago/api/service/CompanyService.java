package com.kiago.api.service;

import com.kiago.api.entities.Company;
import com.kiago.api.repositories.ICompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private ICompanyRepository companyRepository;

    private final ModelMapper modelMapper;

    public CompanyService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            companyDetails.setId(id);
            return companyRepository.save(companyDetails);
        }
        return null;
    }
}
