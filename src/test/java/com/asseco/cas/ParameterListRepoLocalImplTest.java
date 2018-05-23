package com.asseco.cas;

import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cas.parameters.resource.local.ParameterListRepoLocalImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@DataJpaTest
@EnableTransactionManagement
@Transactional
@ActiveProfiles("resource-local")
public class ParameterListRepoLocalImplTest {


}
