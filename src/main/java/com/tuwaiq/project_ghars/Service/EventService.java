package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.EventDTOIn;
import com.tuwaiq.project_ghars.Model.Event;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.EventRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public void addEvent(Integer userId, EventDTOIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//       if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Only admin can add events");

        if (dto.getEndTime().isBefore(dto.getStartTime()))
            throw new ApiException("End time must be after start time");

        if (dto.getDate().isBefore(LocalDate.now()))
            throw new ApiException("Event date cannot be in the past");

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setUsers(new HashSet<>());

        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Integer eventId) {

        Event event = eventRepository.findEventById(eventId);
        if (event == null)
            throw new ApiException("Event not found");

        return event;
    }

    public void updateEvent(Integer userId, Integer eventId, EventDTOIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");
//
//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Only admin can update events");

        Event event = eventRepository.findEventById(eventId);
        if (event == null)
            throw new ApiException("Event not found");

        if (dto.getEndTime().isBefore(dto.getStartTime()))
            throw new ApiException("End time must be after start time");

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());

        eventRepository.save(event);
    }

    public void deleteEvent(Integer userId, Integer eventId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Only admin can delete events");

        Event event = eventRepository.findEventById(eventId);
        if (event == null)
            throw new ApiException("Event not found");

        eventRepository.delete(event);
    }


    public void joinEvent(Integer userId, Integer eventId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Event event = eventRepository.findEventById(eventId);
            if (event == null)
            throw new ApiException("Event not found");

        if (event.getUsers().contains(user))
            throw new ApiException("You already joined this event");

        for (Event userEvent : user.getEvents()) {

            if (!userEvent.getDate().equals(event.getDate()))
                continue;

            boolean isOverlapping =
                    event.getStartTime().isBefore(userEvent.getEndTime()) && event.getEndTime().isAfter(userEvent.getStartTime());
            if (isOverlapping) {
                throw new ApiException(
                        "You have another event that conflicts with this time"
                );
            }
        }
        event.getUsers().add(user);
        eventRepository.save(event);
        String subject = "تم تسجيلك في فعالية 🌿";
        String body = "مرحبًا " + user.getName() + "\n\n" +
                "تم تسجيلك في فعالية: " + event.getTitle() + "\n" +
                "الموقع: " + event.getLocation() + "\n" +
                "التاريخ: " + event.getDate() + "\n" +
                "الوقت: " + event.getStartTime() + " - " + event.getEndTime();
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    public void leaveEvent(Integer userId, Integer eventId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Event event = eventRepository.findEventById(eventId);
        if (event == null)
            throw new ApiException("Event not found");

        if (!event.getUsers().contains(user))
            throw new ApiException("You are not joined in this event");

        event.getUsers().remove(user);
        eventRepository.save(event);

        String subject = "تم إلغاء تسجيلك من الفعالية 🌱";
        String body = "مرحبًا " + user.getName() + "\n\n" +
                "تم تسجيل خروجك من الفعالية بنجاح:\n" +
                event.getTitle() + "\n\n" +
                "نتمنى أن نراك في فعالية أخرى قريبًا 🤍";

        emailService.sendEmail(user.getEmail(), subject, body);
    }


    public List<Event> getUpcomingEvents() {
        return eventRepository.findAllByDateGreaterThanEqualOrderByDateAscStartTimeAsc(LocalDate.now());
    }

    public List<Event> getUpcomingEventsByCity(String city) {
        return eventRepository.findAllByLocationAndDateGreaterThanEqualOrderByDateAscStartTimeAsc(city, LocalDate.now());
    }
}
