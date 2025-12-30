package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.DTOIn.EventDTOIn;
import com.tuwaiq.project_ghars.Model.Event;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.EventService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EventController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class EventControllerTest {

    @MockitoBean
    EventService eventService;

    @Autowired
    EventController eventController;

    @Autowired
    MockMvc mockMvc;

    User user1, user2;
    Event event1, event2;
    List<Event> events = new ArrayList<>();
    List<Event> eventsCity=new ArrayList<>();
    EventDTOIn eventDTOIn1, eventDTOIn2;

    @BeforeEach
    public void setUp() {
        user1 = new User(null, "osama", "osama@gmail.com", "osama", "osamadad768", "0500000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        user2 = new User(null, "mohammed", "mohammed@gmail.com", "mohammed", "mohammeddad768", "0550000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        event1 = new Event(1, "Green Jeddah", "Green Jeddah", "Jeddah", LocalDate.now(), LocalTime.now(), LocalTime.now(), null);
        event2 = new Event(2, "Flower festival", "Flower festival", "Medina", LocalDate.now(), LocalTime.now(), LocalTime.now(), null);
        events.add(event1);
        events.add(event2);
        eventsCity.add(event2);
        eventDTOIn1 = new EventDTOIn("Green Riyadh", "Green Riyadh", "Riyadh", LocalDate.now(), LocalTime.now(), LocalTime.now());
        eventDTOIn2 = new EventDTOIn("Flower festival", "Flower festival", "Medina", LocalDate.now(), LocalTime.now(), LocalTime.now());
    }

    @BeforeEach
    void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(eventController)
                .setCustomArgumentResolvers(
                        new AuthenticationPrincipalArgumentResolver()
                )
                .build();
    }

    @Test
    public void addEvent() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user1,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform(post("/api/v1/event/add",user1).contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(eventDTOIn1))).andExpect(status().isOk());
    }

    @Test
    public void getAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(events);
        mockMvc.perform(get("/api/v1/event/get")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Green Jeddah"));
    }

    @Test
    public void getEvent() throws Exception {
        when(eventService.getEventById(event2.getId())).thenReturn(event2);
        mockMvc.perform(get("/api/v1/event/get/{eventId}",event2.getId())).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2)).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Flower festival"));
    }

    @Test
    public void updateEvent() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user1,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform(put("/api/v1/event/update/{eventId}", event2.getId()).contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(eventDTOIn2))).andExpect(status().isOk());
    }

    @Test
    public void deleteEvent() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user1,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform(delete("/api/v1/event/delete/{eventId}", event2.getId())).andExpect(status().isOk());
    }

    @Test
    public void joinEvent() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user1,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform(post("/api/v1/event/join/{eventId}", event1.getId())).andExpect(status().isOk());
    }

    @Test
    public void leaveEvent() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user1,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform(post("/api/v1/event/leave/{eventId}", event1.getId())).andExpect(status().isOk());

    }

    @Test
    public void getUpcomingEvents() throws Exception {
        when(eventService.getUpcomingEvents()).thenReturn(events);
        mockMvc.perform(get("/api/v1/event/upcoming")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Green Jeddah"));
    }

    @Test
    public void getUpcomingEventsByCity() throws Exception {
        when(eventService.getUpcomingEventsByCity(event2.getLocation())).thenReturn(eventsCity);
        mockMvc.perform(get("/api/v1/event/upcoming/city/{city}", event2.getLocation())).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))).andExpect(MockMvcResultMatchers.jsonPath("$[0].location").value("Medina"));
    }
}
