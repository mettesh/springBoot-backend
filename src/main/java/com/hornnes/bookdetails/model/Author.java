package com.hornnes.bookdetails.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // IDENTITY: ID is AI in Database
    private Integer author_id;
    private String name;

    public Integer getId() { return author_id; }

    public void setId(Integer id) {
        this.author_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
