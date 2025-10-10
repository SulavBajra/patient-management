package com.sulav.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sulav.patient_service.dto.PatientRequestDTO;
import com.sulav.patient_service.dto.PatientResponseDTO;
import com.sulav.patient_service.exception.EmailAlreadyExistsException;
import com.sulav.patient_service.mapper.PatientMapper;
import com.sulav.patient_service.model.Patient;
import com.sulav.patient_service.repository.PatientRepository;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        // List<PatientResponseDTO> patientResponseDTOs = patients.stream()
        // .map(PatientMapper::toDTO).toList();
        //patient -> PatientMapper.toDTO(patient) logic behind ::
        return patients.stream()
        .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A Patient with this email already exists "+
            patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }





}
