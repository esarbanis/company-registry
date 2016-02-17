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
 * Coordinates a combination of actions in a single transaction.
 *
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

    /**
     * Validates the given {@link Company} object and then persist is.
     * <p>
     *     In case of invalid object a {@link ConstraintViolationException} will be thrown.
     * </p>
     * @param company the given {@link Company} object
     * @return the serialized {@link Company} object. Should contain ids
     */
    @Transactional(rollbackFor = ConstraintViolationException.class)
    public Company create(Company company) {
        validate(company);
        return companyRepository.save(company);
    }

    /**
     * Checks if the given company id exists in the store, if not an {@link EntityNotFoundException}
     * will be thrown.
     * <p>
     *     In the case that the company id exists, it will be replaced in the provided {@link Company}
     *     object, to prevent updating the wrong entity.
     * </p>
     * @param companyId the company's, to be updated, id
     * @param company the given {@link Company} object
     * @return the serialized {@link Company} object
     */
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
