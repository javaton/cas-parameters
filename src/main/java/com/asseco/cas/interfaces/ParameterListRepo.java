package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterListRepo extends JpaRepository<ParameterList, Long> {
}

