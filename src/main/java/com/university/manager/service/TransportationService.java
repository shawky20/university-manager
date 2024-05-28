package com.university.manager.service;

import com.university.manager.entities.Transportation;
import com.university.manager.repositories.TransportationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportationService {

    @Autowired
    private TransportationRepo transportationRepo;


    public Transportation saveTransportation(Transportation transportation){
        return transportationRepo.save(transportation);
    }
}
