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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Company> create(@RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(company));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Company>> list(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(companyRepository.findAll(pageable));
    }

    @RequestMapping(value = "/{companyId}",method = RequestMethod.GET)
    public ResponseEntity<Company> get(@PathVariable("companyId") Long companyId) {
        Company company = companyRepository.findOne(companyId);
        return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(company);
    }

    @RequestMapping(value = "/{companyId}",method = RequestMethod.PUT)
    public ResponseEntity<Company> update(@PathVariable("companyId") @NotEmpty Long companyId, @RequestBody Company company) {
        return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(companyService.update(companyId, company));
    }

    @RequestMapping(value = "/{companyId}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> update(@PathVariable("companyId") @NotEmpty Long companyId) {
        companyRepository.delete(companyId);
        return ResponseEntity.noContent().build();
    }
}
