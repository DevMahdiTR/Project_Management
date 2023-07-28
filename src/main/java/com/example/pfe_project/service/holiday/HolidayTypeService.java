package com.example.pfe_project.service.holiday;


import com.example.pfe_project.dto.holiday.HolidayTypeDto;
import com.example.pfe_project.dto.holiday.HolidayTypeUpdateRequestDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.holiday.HolidayType;
import com.example.pfe_project.repository.holiday.HolidayTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayTypeService {

    private final HolidayTypeRepository holidayTypeRepository;

    @Autowired
    public HolidayTypeService(HolidayTypeRepository holidayTypeRepository)
    {
        this.holidayTypeRepository = holidayTypeRepository;
    }
    public ResponseEntity<String> createHolidayType(HolidayType holidayType)
    {
        holidayTypeRepository.save(holidayType);
        final String responseMessage  = String.format("Holiday with type %s is successfully created.",holidayType.getHolidayName());
        return new ResponseEntity<String>(responseMessage,HttpStatus.CREATED);
    }
    public ResponseEntity<List<HolidayTypeDto>> getAllHolidayTypes()
    {
        List<HolidayTypeDto> holidayTypeDtoList = holidayTypeRepository.fetchAllHolidayTypes().stream().map(HolidayTypeDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(holidayTypeDtoList);
    }

    public ResponseEntity<String> modifyHolidayTypeById(long id, HolidayTypeUpdateRequestDto holidayTypeUpdateRequestDto)
    {
        HolidayType holidayTypeToModify = getHolidayTypeById(id);
        holidayTypeToModify.setHolidayName(holidayTypeUpdateRequestDto.getNewHolidayName());
        holidayTypeRepository.save(holidayTypeToModify);
        final String response = String.format("Holiday type has been modified successfully.");
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<String> modifyHolidayTypeByName(String currentHolidayName, HolidayTypeUpdateRequestDto holidayTypeUpdateRequestDto)
    {
        HolidayType holidayTypeToModify = getHolidayTypeByName(currentHolidayName);
        holidayTypeToModify.setHolidayName(holidayTypeUpdateRequestDto.getNewHolidayName());
        holidayTypeRepository.save(holidayTypeToModify);
        final String response = String.format("Holiday type has been modified successfully.");
        return ResponseEntity.ok(response);
    }
    @Transactional
    public ResponseEntity<String> deleteHolidayTypeById(long id)
    {
        HolidayType holidayType = getHolidayTypeById(id);
        holidayTypeRepository.deleteHolidayTypeById(id);
        final String response = String.format("Holiday with type '%s' has been deleted successfully.",holidayType.getHolidayName());
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<String> deleteHolidayTypeByName(String holidayName)
    {
        HolidayType holidayType = getHolidayTypeByName(holidayName);
        holidayTypeRepository.deleteHolidayTypeByName(holidayName);
        final String response = String.format("Holiday with type '%s' has been deleted successfully.",holidayType.getHolidayName());
        return ResponseEntity.ok(response);
    }



    public HolidayType getHolidayTypeById(long holidayId) {
        return holidayTypeRepository.fetchHolidayTypeById(holidayId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The holiday type with ID %d could not be found.", holidayId)));
    }

    public HolidayType getHolidayTypeByName(String holidayName) {
        return holidayTypeRepository.fetchHolidayTypeByName(holidayName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The holiday type with name %s could not be found.", holidayName)));
    }
}
