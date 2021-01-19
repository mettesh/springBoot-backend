package com.hornnes.bookdetails.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // IDENTITY: ID is AI in Database
    private Integer id;

    private String name;

    private Integer author;

    private Double userRating;

    private Integer reviews;

    private Integer price;

    private String year;

    private String genre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) { this.author = author; }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double user_rating) {
        this.userRating = user_rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
