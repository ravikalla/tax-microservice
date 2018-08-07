package com.wellsfargo.taxie.wftfv.repository;

import com.wellsfargo.taxie.wftfv.domain.Wfexecution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wfexecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WfexecutionRepository extends JpaRepository<Wfexecution, Long>, JpaSpecificationExecutor<Wfexecution> {

}
