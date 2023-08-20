package com.rentalApp.propertyservice.services.impl;

import com.rentalApp.propertyservice.dao.PropertyDAO;
import com.rentalApp.propertyservice.dto.PropertyDTO;
import com.rentalApp.propertyservice.services.PropertyService;
import com.rentalApp.propertyservice.exceptions.UserNotAllowedException;
import com.rentalApp.propertyservice.models.Property;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDAO propertyDAO;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public void createProperty(PropertyDTO propertyDTO) {
        checkNotNull(propertyDTO.getStreet());
        checkNotNull(propertyDTO.getCity());
        checkNotNull(propertyDTO.getState());
        Property property = modelMapper.map(propertyDTO, Property.class);
        propertyDAO.save(property);

        modelMapper.map(property, propertyDTO);
    }

    @Override
    public List<PropertyDTO> fetchPropertyForUserId(String userId, String callerId) throws UserNotAllowedException {
        final List<Property> allByUserId = propertyDAO.findAllByUserIdOrderByUpdatedAtDesc(userId);

        if(userId.equals(callerId)){
            return allByUserId.stream()
                    .filter(property -> property.getId().equals(callerId))
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .collect(Collectors.toList());
        }else {
            ///TODO: Throw user not allowed Error
            throw new UserNotAllowedException("your not allowed to perform this action");
        }
    }


    @Override
    public PropertyDTO fetchProperty(String propertyId, String userId) throws UserNotAllowedException {
        final Optional<Property> propertyOptional = propertyDAO.findById(propertyId);

        if(propertyOptional.isPresent()){
            final Property property = propertyOptional.get();
            if(property.getUserId().equals(userId)){
                return modelMapper.map(propertyOptional.get(), PropertyDTO.class);
            }
            else {
                throw new UserNotAllowedException("You are not allowed to modify this property");
            }
        }
        else {
            throw new NoSuchElementException("No Property with id " + propertyId + " was not found");
        }

    }
    @Override
    public List<PropertyDTO> fetchTopRecentProperties(String userid) {
        final Page<Property> propertyPage = propertyDAO.findByUserId(userid, PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"updatedAt")));
        return propertyPage.stream()
                .map(property -> modelMapper.map(property,PropertyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateProperty(PropertyDTO propertyDTO, String userId) throws UserNotAllowedException {
        checkNotNull(propertyDTO.getStreet());
        checkNotNull(propertyDTO.getCity());
        checkNotNull(propertyDTO.getState());
        final Optional<Property> propertyOptional = propertyDAO.findById(propertyDTO.getId());
        if(propertyOptional.isPresent()){
            final Property property = propertyOptional.get();
            if(property.getUserId().equals(userId)){
                modelMapper.map(property, propertyDTO);
                return;
            }
            else {
                // TODO: throw USER not allowed exception
               throw new UserNotAllowedException("You Are Not Allowed to Perform that Action");
            }
        }
        //TODO: non such element exception
        throw new NoSuchElementException("No Such Property Exists");
    }
}
