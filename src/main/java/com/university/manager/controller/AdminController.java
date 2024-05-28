package com.university.manager.controller;

import com.university.manager.dtos.AddDepartmentsRequest;
import com.university.manager.dtos.UnitDTO;
import com.university.manager.entities.Unit;
import com.university.manager.entities.UserDB;
import com.university.manager.repositories.UnitRepo;
import com.university.manager.repositories.UserRepo;
import com.university.manager.security.JwtProvider;
import com.university.manager.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@AllArgsConstructor
public class AdminController {

    private final UnitService unitService;


    @PostMapping("/department/add")
    public ResponseEntity<List<UnitDTO>> addDepartments(@RequestBody(required = true) AddDepartmentsRequest request,
                                         @RequestHeader("Authorization") String token){
        List<Unit> departments = unitService.addDepartments(request, token);
        List<UnitDTO> response = new ArrayList<>();
        for(Unit department : departments){
            UnitDTO departmentDTO = new UnitDTO();
            departmentDTO.setDetails(department.getDetails());
            departmentDTO.setPrice(department.getPrice());
            departmentDTO.setAddress(department.getAddress());
            departmentDTO.setUniversity(String.valueOf(department.getUniversity()));

            response.add(departmentDTO);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/department/update/{id}")
    public UnitDTO updateDepartment(@RequestBody(required = true) UnitDTO unitDTO,@PathVariable Long id,
                                         @RequestHeader("Authorization") String token){
        unitService.updateDepartment(unitDTO, token, id);
        return unitDTO;
    }

    @DeleteMapping("/department/delete/{id}")
    public void deleteDepartment(@PathVariable Long id, @RequestHeader("Authorization") String token){
        unitService.deleteDepartment(id, token);
    }

}
