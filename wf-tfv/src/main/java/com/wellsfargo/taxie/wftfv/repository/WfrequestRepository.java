package com.wellsfargo.taxie.wftfv.repository;

import com.wellsfargo.taxie.wftfv.domain.Wfrequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wfrequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WfrequestRepository extends JpaRepository<Wfrequest, Long>, JpaSpecificationExecutor<Wfrequest> {

}
