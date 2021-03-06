package io.github.esarbanis.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Beneficial Owner.
 *
 * Should not exist out of the {@link Company}'s scope.
 *
 * @author Efthymios Sarmpanis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OWNER_")
@SequenceGenerator(name = "ownerId", sequenceName = "owner_id", allocationSize = 0, initialValue = 1)
public class Owner {

    /**
     * Owner ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ownerId")
    private Long id;

    /**
     * Owner name
     */
    @NotEmpty
    private String name;

    /**
     * The company which is the beneficial owner.
     */
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;
}
