package com.university.manager.controller;


import com.university.manager.dtos.UnitDTO;
import com.university.manager.entities.Unit;
import com.university.manager.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class StudentController {

    @Autowired
    private UnitService service;

    @GetMapping("/departments/all")
    public List<UnitDTO> getAllDepartments() {
        return service.getAllDepartments();
    }

    @GetMapping("/departments")
    public List<UnitDTO> getDepartmentsNearUniversity(@RequestParam("university") String universityName){
        List<UnitDTO> response = service.getDepartmentsNearUniversity(universityName);
        return response;
    }

    @GetMapping("/department/{id}")
    public UnitDTO getDepartmentDetails(@PathVariable("id") Long id){
        UnitDTO response = service.getDepartmentDetails(id);
        return response;
    }

    @PostMapping("/Book")
    public void bookDepartment(@RequestParam("departmentId") Long departmentId, @RequestParam("userMail") String userMail){
        service.bookDepartment(departmentId, userMail);
    }

}
