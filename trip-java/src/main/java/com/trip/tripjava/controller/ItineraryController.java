package com.trip.tripjava.controller;

import com.trip.tripjava.dto.ItineraryDTO;
import com.trip.tripjava.entity.ItineraryEntity;
import com.trip.tripjava.service.ItineraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itinerary")
@Slf4j
public class ItineraryController {
    private final ItineraryService itineraryService;

    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }
    
    // itinerary 추가하기
    @PostMapping("/add")
    public ResponseEntity<ItineraryEntity> addItinerary(@RequestBody ItineraryEntity itinerary) {
        ItineraryEntity savedItinerary = itineraryService.saveItinerary(itinerary);
        return new ResponseEntity<>(savedItinerary, HttpStatus.CREATED);
    }

    // 전체 itinerary 불러오기 
    @GetMapping("/select/{plannerNo}")
    public ResponseEntity<?> getAllItineraries(@PathVariable Long plannerNo) {
        try {
            List<ItineraryEntity> itineraries = itineraryService.getAllItinerariesWithNativeQuery(plannerNo);
            return new ResponseEntity<>(itineraries, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // 해당 itinerary 불러오기
    @GetMapping("/{id}")
    public ResponseEntity<ItineraryEntity> getItineraryById(@PathVariable Long id) {
        ItineraryEntity itinerary = itineraryService.getItineraryById(id);
        return new ResponseEntity<>(itinerary, HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteItineraryById(@PathVariable Long id) {
        itineraryService.deleteItineraryById(id);
        return new ResponseEntity<>("Itinerary deleted successfully", HttpStatus.OK);
    }

}
