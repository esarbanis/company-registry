package io.github.esarbanis.company.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Company.
 *
 * @author Efthymios Sarmpanis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMPANY_")
@SequenceGenerator(name = "companyId", sequenceName = "company_id", allocationSize = 0, initialValue = 1)
public class Company {

    /**
     * Company ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyId")
    private Long id;

    /**
     * Company name
     */
    @NotEmpty
    private String name;

    /**
     * Company address
     */
    @NotEmpty
    private String address;

    /**
     * Company city
     */
    @NotEmpty
    private String city;

    /**
     * Company country
     */
    @NotEmpty
    private String country;

    /**
     * Company contact email
     */
    @Email
    private String email;

    /**
     * Company contact phone number
     */
    private String phone;

    /**
     * The company's beneficial owners
     */
    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Owner> owners;
}
