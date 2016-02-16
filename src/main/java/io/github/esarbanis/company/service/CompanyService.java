package io.github.esarbanis.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import io.github.esarbanis.company.dao.CompanyRepository;
import io.github.esarbanis.company.model.Company;

/**
 * @author Efthymios Sarmpanis
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final Validator validator;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, Validator validator) {
        this.companyRepository = companyRepository;
        this.validator = validator;
    }

    @Transactional(rollbackFor = ConstraintViolationException.class)
    public Company create(Company company) {
        validate(company);
        return companyRepository.save(company);
    }

    @Transactional
    public Company update(Long companyId, Company company) {
        Optional<Company> existing = Optional.ofNullable(companyRepository.findOne(companyId));
        if(!existing.isPresent()) {
            throw new EntityNotFoundException("Could not find company with id: "+companyId);
        }
        if (company.getId() == null) {
            company.setId(companyId);
        }
        validate(company);
        return companyRepository.save(company);
    }

    private void validate(Company company) {
        Set<ConstraintViolation<Company>> constraintViolations = validator.validate(company);
        if(!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
