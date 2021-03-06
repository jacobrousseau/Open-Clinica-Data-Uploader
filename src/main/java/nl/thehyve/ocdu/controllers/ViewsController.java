package nl.thehyve.ocdu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by piotrzakrzewski on 22/03/16.
 */

@Controller
@RequestMapping("/views")
public class ViewsController {

    private static final Logger log = LoggerFactory.getLogger(ViewsController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/final", method = RequestMethod.GET)
    public String finalView() {
        return "final";
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public String patients() {
        return "subjects";
    }

    @RequestMapping(value = "/mapping", method = RequestMethod.GET)
    public String mapping() {
        return "mapping";
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String events() {
        return "events";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String data() {
        return "data";
    }

    @RequestMapping(value = "/feedback-data", method = RequestMethod.GET)
    public String feedbackData() {
        return "feedback-data";
    }

    @RequestMapping(value = "/feedback-subjects", method = RequestMethod.GET)
    public String feedbackSubjects() {
        return "feedback-subjects";
    }

    @RequestMapping(value = "/feedback-events", method = RequestMethod.GET)
    public String feedbackEvents() {
        return "feedback-events";
    }

}
