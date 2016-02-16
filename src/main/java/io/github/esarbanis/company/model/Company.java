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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyId")
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @Email
    private String email;

    private String phone;

    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Owner> owners;
}
