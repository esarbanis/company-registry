package io.github.esarbanis.company.controller;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.github.esarbanis.company.dao.CompanyRepository;
import io.github.esarbanis.company.model.Company;
import io.github.esarbanis.company.service.CompanyService;

/**
 * REST Endpoint for Company Registration management.
 *
 * @author Efthymios Sarmpanis
 */
@RestController
@RequestMapping(path = "company")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, CompanyService companyService) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    /**
     * Adds the given company to the registry.
     * <p>
     *     In case of invalid {@link Company} object a 400 http status will be returned.
     * </p>
     * @param company the given company
     * @return 201 for success, 400 for validation errors, 500 for system errors
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Company> create(@RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(company));
    }

    /**
     * Retrieves a pages list of the companies currently in the registry.
     *
     * @param pageable the paging information. Default is to fetch all.
     * @return 200 for success, 500 for system errors
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Company>> list(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(companyRepository.findAll(pageable));
    }

    /**
     * Retrieves a single company from the registry by its id.
     *
     * @param companyId the id of the company to fetch
     * @return 200 for success, 404 for no company found, 500 for system errors
     */
    @RequestMapping(value = "/{companyId}",method = RequestMethod.GET)
    public ResponseEntity<Company> get(@PathVariable("companyId") Long companyId) {
        Company company = companyRepository.findOne(companyId);
        return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(company);
    }

    /**
     * Updates a single company by its id and the values provided by the {@link Company} object.
     *
     * @param companyId the id of the company to update
     * @param company the new values in a form of a {@link Company} object
     * @return 200 for success, 404 for no company found, 400 for validation errors, 500 for system errors
     */
    @RequestMapping(value = "/{companyId}",method = RequestMethod.PUT)
    public ResponseEntity<Company> update(@PathVariable("companyId") @NotEmpty Long companyId, @RequestBody Company company) {
        return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(companyService.update(companyId, company));
    }

    /**
     * Deletes a single company by its id.
     *
     * @param companyId the id of the company to delete
     * @return 204 for success, 404 for no company found, 500 for system errors
     */
    @RequestMapping(value = "/{companyId}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("companyId") @NotEmpty Long companyId) {
        companyRepository.delete(companyId);
        return ResponseEntity.noContent().build();
    }
}
