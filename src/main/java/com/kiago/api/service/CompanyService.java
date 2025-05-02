package com.kiago.api.service;

import com.kiago.api.dtos.CompanyDTO;
import com.kiago.api.entities.Company;
import com.kiago.api.repositories.ICompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private ICompanyRepository companyRepository;

    private final ModelMapper modelMapper;

    public CompanyService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<?> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> listCompanyDTO = companies.stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listCompanyDTO);
    }

    public ResponseEntity<?> getCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            CompanyDTO companyDTO = modelMapper.map(companyOptional.get(), CompanyDTO.class);
            return ResponseEntity.ok(companyDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con el id " + id + " no encontrada");
        }
    }

    public ResponseEntity<?> createCompany(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        Company savedCompany = companyRepository.save(company);
        CompanyDTO savedCompanyDTO = modelMapper.map(savedCompany, CompanyDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompanyDTO);
    }

    public ResponseEntity<?> updateCompany(Long id, CompanyDTO companyDTO) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            existingCompany.setName(companyDTO.getName());
            existingCompany.setDescription(companyDTO.getDescription());
            Company updatedCompany = companyRepository.save(existingCompany);
            CompanyDTO updatedCompanyDTO = modelMapper.map(updatedCompany, CompanyDTO.class);
            return ResponseEntity.ok(updatedCompanyDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La compañia con el id " + id + "no existe");
        }
    }

    public ResponseEntity<?> deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return ResponseEntity.ok().body("Se ha eliminado la compañia correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La compañia con el id " + id + "no existe");
        }
    }
}
