package com.rentalApp.tenantservice.services.impl;

import com.rentalApp.tenantservice.daos.TenantDAO;
import com.rentalApp.tenantservice.dtos.TenantDTO;
import com.rentalApp.tenantservice.exceptions.UserNotAllowedException;
import com.rentalApp.tenantservice.model.Tenant;
import com.rentalApp.tenantservice.services.TenantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
class TenantServiceImplTest {
    @Mock
    private TenantDAO tenantDAO;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TenantServiceImpl tenantService;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(
            "mongo:latest"))
            .withExposedPorts(27017);

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTenant() {
        TenantDTO tenantDAO1 = new TenantDTO();
        tenantDAO1.setFirstName("Test first name");
        tenantDAO1.setLastName("Test last name");
        tenantDAO1.setJwtToken("123456789");
        tenantDAO1.setPropertyId("123");

        Tenant tenant = new Tenant();
        when(modelMapper.map(tenantDAO1, Tenant.class)).thenReturn(tenant);
        tenantService.createTenant(tenantDAO1);
        verify(tenantDAO , times(1)).save(tenant);
        verify(modelMapper, times(1)).map(tenantDAO1, Tenant.class);

    }

    @Test
    void fetchTenant() throws UserNotAllowedException {
        String tenantId = "TestUser";
        String propertyId = "TestPropertyId";

        Tenant tenant = new Tenant();
        tenant.setPropertyId(propertyId);
        tenant.setId(tenantId);

        when(tenantDAO.findById(tenantId)).thenReturn(Optional.of(tenant));
        when(modelMapper.map(tenant, TenantDTO.class)).thenReturn(new TenantDTO());
        TenantDTO tenantDTO = tenantService.fetchTenant(tenantId,propertyId);
        assertNotNull(tenantDTO);
        verify(tenantDAO, times(1)).findById(tenantId);
    }

    @Test
    void TestUserNotAllowed(){
        String tenantId = "testteanant";
        String propertyId = "propertyId";
        String callerPropertyId = "propertyTest";

        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setPropertyId(propertyId);

        when(tenantDAO.findById(tenantId)).thenReturn(Optional.of(tenant));
        assertThrows(UserNotAllowedException.class, () -> {
            tenantService.fetchTenant(tenantId, callerPropertyId);
        });
    }

    @Test
    void fetchTenantsForPropertyId() {
    }

    @Test
    void deleteAll() {
    }
}