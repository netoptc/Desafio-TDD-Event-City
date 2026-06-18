package com.devsuperior.bds02.services;


import com.devsuperior.bds02.Exceptions.ResourceNotFoundException;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityService cityService;

    public EventDTO update(Long eventId, EventDTO dto) {
        Event event  = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event id does not exists"));

        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setUrl(dto.getUrl());
        event.setCity(cityService.getById(dto.getCityId()));

        eventRepository.save(event);

        return new EventDTO(event);
    }

}
