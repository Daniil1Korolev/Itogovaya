package com.example.marketdota.controller;

import com.example.marketdota.model.CoachesModel;
import com.example.marketdota.repo.CoachesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/coaches")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class CoachesController {
    private final CoachesRepo Repo;

    @Autowired
    public CoachesController(CoachesRepo Repo) {
        this.Repo = Repo;
    }

    @GetMapping()
    public String listCoaches(Model model) {
        Iterable<CoachesModel> coachesModels = Repo.findAll();
        model.addAttribute("coachess", coachesModels);
        return "coaches/all";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        CoachesModel coachesModel = new CoachesModel();
        //clas.setPublicationYear(0);
        model.addAttribute("coaches", coachesModel);
        return "coaches/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("coaches") CoachesModel coachesModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "coaches/add";
        }
        Repo.save(coachesModel);
        return "redirect:/coaches";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        CoachesModel coachesModel = Repo.findById(id).orElse(null);
        if (coachesModel == null) {
            return "redirect:/coaches";
        }
        model.addAttribute("coaches", coachesModel);
        return "coaches/update";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, @Valid @ModelAttribute("coaches") CoachesModel coachesModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "coaches/update"; // Останется на странице редактирования с отображением ошибок
        }
        coachesModel.setId(id);
        Repo.save(coachesModel);
        return "redirect:/coaches"; // Перенаправление на страницу со всеми героями
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        Repo.deleteById(id);
        return "redirect:/coaches";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "name") String name, Model model) {
        Iterable<CoachesModel> coachesModels = Repo.findByNameContainingIgnoreCase(name);
        model.addAttribute("coachess", coachesModels);
        return "coaches/all";
    }
}
