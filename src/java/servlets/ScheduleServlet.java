package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@MultipartConfig
public class ScheduleServlet extends HttpServlet {
    public final String DIR = "uploads\\";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/schedules.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String realPath = request.getServletContext().getRealPath("");
        String filePath = realPath + DIR;
        File file;

        DiskFileItemFactory dfif = new DiskFileItemFactory(5000 * 1024, new File(filePath));
        ServletFileUpload sfu = new ServletFileUpload(dfif);
        sfu.setFileSizeMax(5000 * 1024);

        try {
            List partsList = sfu.parseRequest(request);
            Iterator iter = partsList.iterator();

            while (iter.hasNext()) {
                FileItem fi = (FileItem) iter.next();
                if (!fi.isFormField()) {
                    
                    Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, filePath+fi.getName());

                    file = new File(filePath + fi.getName());
                    fi.write(file);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, "Catch exception");
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/schedules.jsp").forward(request, response);

    }
}