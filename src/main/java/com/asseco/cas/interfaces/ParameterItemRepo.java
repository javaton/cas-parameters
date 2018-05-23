package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterItemRepo extends JpaRepository<ParameterItem, Long> {

}
