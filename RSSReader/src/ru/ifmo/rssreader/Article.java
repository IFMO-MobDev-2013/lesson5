package ru.ifmo.rssreader;

public class Article {
    private String title, article;

    public Article(String title, String article) {
        this.title = title;
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public String getArticle() {
        return article;
    }
}
