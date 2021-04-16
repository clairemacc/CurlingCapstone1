package services;

import dataaccess.NewsPostDB;
import java.util.List;
import models.NewsPost;

/**
 * This class provides functionality for the Newsposts
 * 
 */
public class NewsPostService {
    /**
     * This method returns all the news posts in the system
     * @return list of news posts
     */
    public List<NewsPost> getAll() {
        NewsPostDB ndb = new NewsPostDB();
        return ndb.getAll();
    }
}
