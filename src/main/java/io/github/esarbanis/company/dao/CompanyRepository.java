package io.github.esarbanis.company.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.github.esarbanis.company.model.Company;

/**
 * Data Access Object for Companies.
 *
 * @author Efthymios Sarmpanis
 */
@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

}
