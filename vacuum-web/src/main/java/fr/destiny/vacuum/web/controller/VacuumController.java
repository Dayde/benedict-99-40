package fr.destiny.vacuum.web.controller;

import fr.destiny.vacuum.web.service.VacuumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacuumController {

    @Autowired
    private VacuumService vacuumService;

    @RequestMapping("/")
    public String userInfo(@RequestParam String username) {
        return vacuumService.userInfo(username).toString();
    }
}
