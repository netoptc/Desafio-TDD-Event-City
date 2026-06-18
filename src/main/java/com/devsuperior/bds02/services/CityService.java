package com.devsuperior.bds02.services;


import com.devsuperior.bds02.Exceptions.DatabaseException;
import com.devsuperior.bds02.Exceptions.ResourceNotFoundException;
import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City getById(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new ResourceNotFoundException("city id does not exists"));
    }

    public List<CityDTO> findAll() {
        List<City> cities = cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return cities.stream().map(CityDTO::new).collect(Collectors.toList());
    }

    public CityDTO insert(CityDTO dto) {
        City city = new City();
        city.setName(dto.getName());
        cityRepository.save(city);
        return new CityDTO(city);
    }

    public void  delete(Long cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw  new ResourceNotFoundException("city id does not exists");
        }

        try {
            cityRepository.deleteById(cityId);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
