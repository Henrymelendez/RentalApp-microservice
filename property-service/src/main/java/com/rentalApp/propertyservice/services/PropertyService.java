package com.rentalApp.propertyservice.services;

import com.rentalApp.propertyservice.dto.PropertyDTO;
import com.rentalApp.propertyservice.dao.exceptions.UserNotAllowedException;

import java.util.List;

public interface PropertyService {

    void createProperty(PropertyDTO propertyDTO);
    List<PropertyDTO> fetchPropertyForUserId(String userId, String callerId) throws UserNotAllowedException;
    PropertyDTO fetchProperty(String propertyId, String userId) throws UserNotAllowedException;
    List<PropertyDTO> fetchTopRecentProperties(String userId);
    void updateProperty(PropertyDTO propertyDTO, String userId);
}
