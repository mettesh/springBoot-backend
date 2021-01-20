package com.hornnes.bookdetails.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // IDENTITY: ID is AI in Database
    private Integer book_id;

    private String name;

    @ManyToOne()
    @JoinColumn(name = "author", referencedColumnName = "author_id")
    private Author author;

    private Double userRating;

    private Integer reviews;

    private Integer price;

    private String year;

    private String genre;

    public Integer getId() {
        return book_id;
    }

    public void setId(Integer id) {
        this.book_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) { this.author = author; }

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
