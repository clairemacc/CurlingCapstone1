package services;

import dataaccess.NewsPostDB;
import java.util.List;
import models.NewsPost;

public class NewsPostService {
    public List<NewsPost> getAll() {
        NewsPostDB ndb = new NewsPostDB();
        return ndb.getAll();
    }
}
