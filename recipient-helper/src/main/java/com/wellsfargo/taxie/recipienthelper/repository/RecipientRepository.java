package com.wellsfargo.taxie.recipienthelper.repository;

import com.wellsfargo.taxie.recipienthelper.domain.Recipient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recipient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

}
