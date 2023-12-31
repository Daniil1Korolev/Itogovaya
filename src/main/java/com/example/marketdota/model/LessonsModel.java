package com.example.marketdota.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "lesson")
public class LessonsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "categ_id") 
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private CoachesModel coaches;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name="eventmark",
            joinColumns=@JoinColumn (name="lesson_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private List<UserModel> user;

    public LessonsModel() {
    }

    public LessonsModel(long id, String name, CategoryModel category, CoachesModel coaches, List<UserModel> user) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.coaches = coaches;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public CoachesModel getCoaches() {
        return coaches;
    }

    public void setCoaches(CoachesModel coaches) {
        this.coaches = coaches;
    }

    public List<UserModel> getUser() {
        return user;
    }

    public void setUser(List<UserModel> user) {
        this.user = user;
    }
}
