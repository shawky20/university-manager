package com.university.manager.service;


import com.university.manager.dtos.AddDepartmentsRequest;
import com.university.manager.dtos.ImageDTO;
import com.university.manager.dtos.TransportationDTO;
import com.university.manager.dtos.UnitDTO;
import com.university.manager.entities.*;
import com.university.manager.repositories.*;
import com.university.manager.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UnitService {

    private final UserRepo userRepo;
    private final UnitRepo unitRepo;
    private final TransportationRepo transportationRepo;
    private final BookRepo bookRepo;
    private final ImageRepo imageRepo;

    public List<UnitDTO> getAllDepartments() {
        List<Unit> units = unitRepo.findAll();
        List<UnitDTO> response = new ArrayList<>();
        for (Unit unit : units) {
            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setId(unit.getId());
            unitDTO.setDetails(unit.getDetails());
            unitDTO.setPrice(unit.getPrice());
            unitDTO.setAddress(unit.getAddress());
            unitDTO.setUniversity(String.valueOf(unit.getUniversity()));
            List<Transportation> transportations = transportationRepo.findByUnit(unit);
            List<TransportationDTO> transportationDTOS = new ArrayList<>();
            if (transportations != null) {
                for (Transportation transportation : transportations) {
                    TransportationDTO transportationDTO = new TransportationDTO();
                    transportationDTO.setAddress(transportation.getAddress());
                    transportationDTO.setPrice(transportation.getPrice());
                    transportationDTO.setDate(transportation.getDate());
                    transportationDTOS.add(transportationDTO);
                }
            }
            unitDTO.setTransportations(transportationDTOS);
            List<Image> images = imageRepo.findByUnit(unit);
            List<ImageDTO> imageDTOS = new ArrayList<>();
            if (images != null) {
                for (Image image : images) {
                    ImageDTO imageDTO = new ImageDTO();
                    imageDTO.setUrl(image.getUrl());
                    imageDTOS.add(imageDTO);
                }
            }
            unitDTO.setImages(imageDTOS);
            response.add(unitDTO);
        }
        return response;
    }

    public List<Unit> addDepartments(AddDepartmentsRequest request, String token) {
        // check if the sender is an admin
        validateAdmin(token, "Only Admins can add departments");
        // map the departments
        List<Unit> departments = new ArrayList<>();
        for (UnitDTO department : request.getDepartments()) {
            Unit newDepartment = new Unit();
            newDepartment.setDetails(department.getDetails());
            newDepartment.setPrice(department.getPrice());
            newDepartment.setAddress(department.getAddress());
            newDepartment.setUniversity(department.getUniversity());
            newDepartment.setRegion(department.getRegion());
            List<Transportation> transportations = new ArrayList<>();
            List<TransportationDTO> transportationDTOS = department.getTransportations();
            if (transportationDTOS != null) {
                for (TransportationDTO transportationDTO : transportationDTOS) {
                    Transportation transportation = new Transportation();
                    transportation.setAddress(transportationDTO.getAddress());
                    transportation.setPrice(transportationDTO.getPrice());
                    transportation.setDate(transportationDTO.getDate());
                    transportation.setUnit(newDepartment);
                    transportations.add(transportation);
                }
            }
            List<Image> images = new ArrayList<>();
            List<ImageDTO> imageDTOS = department.getImages();
            if(imageDTOS != null){
                for(ImageDTO imageDTO : imageDTOS){
                    Image image = new Image();
                    image.setUrl(imageDTO.getUrl());
                    image.setUnit(newDepartment);
                    images.add(image);
                }
            }
            // Save the Unit entity
            newDepartment = unitRepo.save(newDepartment);
            for (Transportation transportation : transportations) {
                transportation.setUnit(newDepartment);
                transportationRepo.save(transportation);
            }
            for (Image image : images) {
                image.setUnit(newDepartment);
                imageRepo.save(image);
            }
        }
        return departments;
    }

    public List<UnitDTO> getDepartmentsNearUniversity(@RequestParam("university") String universityName){
        List<Unit> departments = unitRepo.findByUniversity(universityName);

        // mapping to DTO
        List<UnitDTO> response = new ArrayList<>();
        for(Unit department : departments){
            UnitDTO departmentDTO = new UnitDTO();
            departmentDTO.setId(department.getId());
            departmentDTO.setDetails(department.getDetails());
            departmentDTO.setPrice(department.getPrice());
            departmentDTO.setAddress(department.getAddress());
            departmentDTO.setUniversity(String.valueOf(department.getUniversity()));
            List<Transportation> transportations = transportationRepo.findByUnit(department);
            List<TransportationDTO> transportationDTOS = new ArrayList<>();
            if (transportations != null) {
                for (Transportation transportation : transportations) {
                    TransportationDTO transportationDTO = new TransportationDTO();
                    transportationDTO.setAddress(transportation.getAddress());
                    transportationDTO.setPrice(transportation.getPrice());
                    transportationDTO.setDate(transportation.getDate());
                    transportationDTOS.add(transportationDTO);
                }
            }
            departmentDTO.setTransportations(transportationDTOS);
            List<Image> images = imageRepo.findByUnit(department);
            List<ImageDTO> imageDTOS = new ArrayList<>();
            if (images != null) {
                for (Image image : images) {
                    ImageDTO imageDTO = new ImageDTO();
                    imageDTO.setUrl(image.getUrl());
                    imageDTOS.add(imageDTO);
                }
            }
            departmentDTO.setImages(imageDTOS);
            response.add(departmentDTO);
        }
        return response;
    }


    public void updateDepartment(UnitDTO unitDTO, String token, Long id) {
        // check if the sender is an admin
        validateAdmin(token, "Only Admins can update departments");
        // map the department
        Unit department = unitRepo.getById(id);
        if (department == null) {
            throw new RuntimeException("Department not found");
        }

        department.setDetails(unitDTO.getDetails());
        department.setPrice(unitDTO.getPrice());
        department.setAddress(unitDTO.getAddress());
        department.setRegion(unitDTO.getRegion());
        department.setUniversity(unitDTO.getUniversity());
        List<Transportation> transportations = new ArrayList<>();
        List<TransportationDTO> transportationDTOS = unitDTO.getTransportations();
        if (transportationDTOS != null) {
            for (TransportationDTO transportationDTO : transportationDTOS) {
                Transportation transportation = new Transportation();
                transportation.setAddress(transportationDTO.getAddress());
                transportation.setPrice(transportationDTO.getPrice());
                transportation.setDate(transportationDTO.getDate());
                transportations.add(transportation);
            }
        }
        List<Image> images = new ArrayList<>();
        List<ImageDTO> imageDTOS = unitDTO.getImages();
        if (imageDTOS != null) {
            for (ImageDTO imageDTO : imageDTOS) {
                Image image = new Image();
                image.setUrl(imageDTO.getUrl());
                image.setUnit(department);
                images.add(image);
            }
        }

        Unit updatedDepartment = unitRepo.save(department);
        for (Transportation transportation : transportations) {
            transportation.setUnit(updatedDepartment);
            transportationRepo.save(transportation);
        }
        for (Image image : images) {
            image.setUnit(updatedDepartment);
            imageRepo.save(image);
        }

    }

    public void deleteDepartment(Long id, String token) {
        // check if the sender is an admin
        validateAdmin(token, "Only Admins can delete departments");
        // delete the department
        unitRepo.deleteById(id);
    }

    private void validateAdmin(String token, String message) {
        String email = JwtProvider.getEmailFromJwtToken(token);
        if (email == null) {
            throw new RuntimeException("Invalid token");
        }
        UserDB user = userRepo.findByEmail(email);
        if (!user.getRole().equals("admin")) {
            throw new RuntimeException(message);
        }
    }

    public UnitDTO getDepartmentDetails(Long id) {
        Unit department = unitRepo.getById(id);
        if (department == null) {
            throw new RuntimeException("Department not found");
        }
        UnitDTO departmentDTO = new UnitDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setDetails(department.getDetails());
        departmentDTO.setPrice(department.getPrice());
        departmentDTO.setAddress(department.getAddress());
        departmentDTO.setUniversity(String.valueOf(department.getUniversity()));
        List<Transportation> transportations = transportationRepo.findByUnit(department);
        List<TransportationDTO> transportationDTOS = new ArrayList<>();
        if (transportations != null) {
            for (Transportation transportation : transportations) {
                TransportationDTO transportationDTO = new TransportationDTO();
                transportationDTO.setAddress(transportation.getAddress());
                transportationDTO.setPrice(transportation.getPrice());
                transportationDTO.setDate(transportation.getDate());
                transportationDTOS.add(transportationDTO);
            }
        }
        departmentDTO.setTransportations(transportationDTOS);
        List<Image> images = imageRepo.findByUnit(department);
        List<ImageDTO> imageDTOS = new ArrayList<>();
        if (images != null) {
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setUrl(image.getUrl());
                imageDTOS.add(imageDTO);
            }
        }
        departmentDTO.setImages(imageDTOS);
        return departmentDTO;
    }

    public void bookDepartment(Long departmentId, String userMail) {
        Unit department = unitRepo.getById(departmentId);
        if (department == null) {
            throw new RuntimeException("Department not found");
        }
        UserDB user = userRepo.findByEmail(userMail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Book book = new Book();
        book.setUser(user);
        book.setUnit(department);
        book.setCost(department.getPrice());
        book.setDate(LocalDateTime.now().toString());
        bookRepo.save(book);
    }
}
