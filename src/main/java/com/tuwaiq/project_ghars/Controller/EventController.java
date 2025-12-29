package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.EventDTOIn;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@AuthenticationPrincipal User user, @RequestBody @Valid EventDTOIn dto) {
        eventService.addEvent(user.getId(), dto);
        return ResponseEntity.status(200).body(new ApiResponse("Event added"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllEvents() {
        return ResponseEntity.status(200).body(eventService.getAllEvents());
    }

    @GetMapping("/get/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable Integer eventId) {
        return ResponseEntity.status(200).body(eventService.getEventById(eventId));
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<?> updateEvent(@AuthenticationPrincipal User user, @PathVariable Integer eventId, @RequestBody @Valid EventDTOIn dto) {
        eventService.updateEvent(user.getId(), eventId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Event updated"));
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@AuthenticationPrincipal User user, @PathVariable Integer eventId) {
        eventService.deleteEvent(user.getId(), eventId);
        return ResponseEntity.status(200).body(new ApiResponse("Event deleted"));
    }

    @PostMapping("/join/{eventId}")
    public ResponseEntity<?> joinEvent(@AuthenticationPrincipal User user, @PathVariable Integer eventId) {
        eventService.joinEvent(user.getId(), eventId);
        return ResponseEntity.status(200).body(new ApiResponse("Joined event"));
    }

    @PostMapping("/leave/{eventId}")
    public ResponseEntity<?> leaveEvent(@AuthenticationPrincipal User user, @PathVariable Integer eventId) {
        eventService.leaveEvent(user.getId(), eventId);
        return ResponseEntity.status(200).body(new ApiResponse("Left event"));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingEvents() {
        return ResponseEntity.status(200).body(eventService.getUpcomingEvents());
    }

    @GetMapping("/upcoming/city/{city}")
    public ResponseEntity<?> getUpcomingEventsByCity(@PathVariable String city) {
        return ResponseEntity.ok(eventService.getUpcomingEventsByCity(city));
    }
}
