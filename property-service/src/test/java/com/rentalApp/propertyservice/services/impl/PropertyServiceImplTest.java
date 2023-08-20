package com.rentalApp.propertyservice.services.impl;

import com.rentalApp.propertyservice.dao.PropertyDAO;
import com.rentalApp.propertyservice.exceptions.UserNotAllowedException;
import com.rentalApp.propertyservice.dto.PropertyDTO;
import com.rentalApp.propertyservice.models.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
class PropertyServiceImplTest {
    @Mock
    private PropertyDAO propertyDAO;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
    .withExposedPorts(27017);

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProperty() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setStreet("Test Street");
        propertyDTO.setCity("Test City");
        propertyDTO.setState("Test State");

        Property property = new Property();
        when(modelMapper.map(propertyDTO, Property.class)).thenReturn(property);
        propertyService.createProperty(propertyDTO);
        verify(propertyDAO,times(1)).save(property);
        verify(modelMapper, times(1)).map(property,propertyDTO);

    }

    @Test
    void fetchPropertyForUserId() throws UserNotAllowedException {
        String userId = "user1";
        String callerId = "user1";

        List<Property> properties = new ArrayList<>();
        properties.add(new Property());

        when(propertyDAO.findAllByUserIdOrderByUpdatedAtDesc(userId)).thenReturn(properties);
        when(modelMapper.map(any(Property.class), eq(PropertyDTO.class))).thenReturn(new PropertyDTO());

        List<PropertyDTO> propertyDTOList = propertyService.fetchPropertyForUserId(userId,callerId);
        assertNotNull(propertyDTOList);
        assertEquals(1,propertyDTOList.size());
    }

    @Test
    void fetchPropertyForUserNotAllowed(){
        String userId = "user1";
        String callerId = "user2";

        assertThrows(UserNotAllowedException.class, () -> {
           propertyService.fetchPropertyForUserId(userId,callerId);
        });
    }

    @Test
    void fetchProperty() throws UserNotAllowedException {
        String userId = "user1";
        String propertyId = "propertyId";

        Property property = new Property();
        property.setId(userId);

        when(propertyDAO.findById(propertyId)).thenReturn(Optional.of(property));
        when(modelMapper.map(property,PropertyDTO.class)).thenReturn(new PropertyDTO());
        PropertyDTO propertyDTO = propertyService.fetchProperty(propertyId,userId);
        assertNotNull(propertyDTO);
    }

    @Test
    void testFetchPropertNotAllowed(){
        String userId = "user1";
        String callerId = "user2";
        String propertyId = "propertyId";

        Property property = new Property();
        property.setId(userId);

        when(propertyDAO.findById(propertyId)).thenReturn(Optional.of(property));

        assertThrows(UserNotAllowedException.class, () -> {
            propertyService.fetchProperty(propertyId,callerId);
        });
    }

    @Test
    void fetchTopRecentProperties() {
        String userId = "user1";

        Page<Property> propertyPage = mock(Page.class);
        when(propertyDAO.findByUserId(eq(userId), any(PageRequest.class))).thenReturn(propertyPage);
        when(propertyPage.stream()).thenReturn(Arrays.stream(new Property[]{new Property()}));
        when(modelMapper.map(any(Property.class), eq(PropertyDTO.class))).thenReturn(new PropertyDTO());

        List<PropertyDTO> propertyDTOList = propertyService.fetchTopRecentProperties(userId);
        assertNotNull(propertyDTOList);
        assertEquals(1,propertyDTOList.size());
    }

    @Test
    void updateProperty() throws UserNotAllowedException {
        String userId = "user1";

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId("propertyId");
        propertyDTO.setState("123 Main St");
        propertyDTO.setCity("City");
        propertyDTO.setState("State");

        Property property = new Property();
        property.setId(userId);

        when(propertyDAO.findById(propertyDTO.getId())).thenReturn(Optional.of(property));

        propertyService.updateProperty(propertyDTO,userId);

        verify(propertyDAO, times(1)).findById(propertyDTO.getId());
        verify(modelMapper, times(1)).map(property,propertyDTO);
    }
}