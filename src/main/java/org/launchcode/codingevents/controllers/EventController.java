package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.EventDetails;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            model.addAttribute("errorMsg", "Bad data!");
            model.addAttribute("categories", eventCategoryRepository.findAll());
            return "events/create";
        }
            eventRepository.save(newEvent);
        //System.out.println(newEvent.getType());
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:";
    }
    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId){
        Optional<Event> eventToEdit = eventRepository.findById(eventId);
        if (eventToEdit.isEmpty()){
        } else {
            Event event = eventToEdit.get();
            model.addAttribute("event", event);
            //System.out.println(eventRepository.findById(eventId));
//            model.addAttribute("event", eventToEdit);
            String title = "Edit Event " + eventToEdit.get();
            model.addAttribute("title", title);
            model.addAttribute("categories", eventCategoryRepository.findAll());
        }return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(@RequestParam int eventId, String name) {
        Optional<Event> eventToEdit = eventRepository.findById(eventId);
        Event event = eventToEdit.get();
        //event.setName(name);
        //event.setEventDetails(eventDetails);
        //event.setEventCategory(eventCategory);
        System.out.println(event);
        System.out.println(name);
        System.out.println(event.getName());
        System.out.println(event.getEventCategory().toString());
//        eventToEdit.setContactEmail(contactEmail);
//        eventToEdit.setEventCategory(eventCategory);
//        eventToEdit.setLocation(location);
//        eventToEdit.setNumOfAttendees(numOfAttendees);
//        eventToEdit.setShouldRegister(shouldRegister);
        return "redirect:";
    }

    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventId, Model model) {

        Optional<Event> result = eventRepository.findById(eventId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }

        return "events/detail";
    }

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title", "Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);
        return "events/add-tag.html";
    }

    @PostMapping("add-tag")
    public String processAddTadForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Errors errors,
                                    Model model){
        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)) {
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventId=" +event.getId();
        }
        return "events/add-tag.html";
    }

}
