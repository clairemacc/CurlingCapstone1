package services;

import dataaccess.NewsPostDB;
import java.util.Date;
import java.util.List;
import models.NewsPost;
import models.User;

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
        NewsPostDB npdb = new NewsPostDB();
        return npdb.getAll();
    }
    
    public NewsPost get(String postID) {
        NewsPostDB npdb = new NewsPostDB();
        return npdb.get(postID);
    }
    
    public void insert(String title, String body, User user, Date postDate) {
        NewsPostDB npdb = new NewsPostDB();
        String postID = generatePostID();
        NewsPost news = new NewsPost(postID);
        
        body = body.replaceAll("(\\r\\n|\\n)", "<br />");
        
        news.setUserID(user);
        news.setTitle(title);
        news.setPostDate(postDate);
        news.setBody(body);
        
        npdb.insert(news);
    }
    
    public void update(String postID, String title, String body) {
        NewsPostDB npdb = new NewsPostDB();
        NewsPost news = get(postID);
        
        news.setTitle(title);
        news.setBody(body);
        
        npdb.update(news);
    }
    
    public void delete(String postID) {
        NewsPostDB npdb = new NewsPostDB();
        NewsPost news = get(postID);
        
        npdb.delete(news);
    }
    
    public String generatePostID() {
        List<NewsPost> posts = getAll();
        int idNum;
        
        if (posts == null || posts.isEmpty()) 
            idNum = 0;
        else {
            String idStr = posts.get(posts.size() - 1).getPostID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }
        
        String postID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            postID = "N_000" + newIdNum;
        else if (newIdNum <= 99) 
            postID = "N_00" + newIdNum;
        else if (newIdNum <= 999)
            postID = "N_0" + newIdNum;
        else 
            postID = "N_" + newIdNum;
        
        return postID;
    }
}
