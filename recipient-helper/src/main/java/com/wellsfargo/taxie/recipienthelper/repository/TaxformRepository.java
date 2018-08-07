package com.wellsfargo.taxie.recipienthelper.repository;

import com.wellsfargo.taxie.recipienthelper.domain.Taxform;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Taxform entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxformRepository extends JpaRepository<Taxform, Long> {

}
