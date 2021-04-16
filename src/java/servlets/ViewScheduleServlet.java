package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Team;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.GameService;
import services.TeamService;

/**
 * This servlet will be used by the user to view a previously uploaded
 * league schedule. A user is able to select multiple schedules from a list of 
 * buttons titled by the league name.
 * @author 819466
 */
public class ViewScheduleServlet extends HttpServlet {

    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processSelect(request);
        
        String selectFile = request.getParameter("selectFile");
        String realPath = request.getServletContext().getRealPath("") + "\\uploads\\" + selectFile;
        FileInputStream readFile = new FileInputStream(new File(realPath));
        Workbook workbook = new XSSFWorkbook(readFile);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        ArrayList<String> cells = new ArrayList<>();
        
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            Iterator<Cell> cellIter = row.iterator();
            Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, "Logging: New Row= " + row.getPhysicalNumberOfCells());
            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();
                switch (cell.getCellType()) {
                    case STRING:
                        Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, "Logging: " + cell.getStringCellValue());
                        cells.add(cell.getStringCellValue());
                        break;

                    case NUMERIC:
                        Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, "Logging: " + cell.getDateCellValue());
                        String newDate = dateFormat.format(cell.getDateCellValue());
                        newDate = newDate.substring(0, 8);
                        cells.add(newDate);
                        break;
                        
                    default:
                        //not yet supporting other data types please keep it simple
                        break;
                }
            }
            cells.add("/nl");
        }

        for (String read : cells) {
            Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, read);
        }
        boolean invalid = false;
        try {
            dateFormat = new SimpleDateFormat("dd/MM/yy");
            GameService gameService = new GameService();
            TeamService teamService = new TeamService();
            
            Team homeTeam;
            Team awayTeam;
            Date date;
        
            for (int i = 4; i < (cells.size() - 3) ; i = i + 4) {
               homeTeam = teamService.get(cells.get(i));
               awayTeam = teamService.get(cells.get(i + 1));
               date = dateFormat.parse(cells.get(i + 2));
               
               if (homeTeam != null && awayTeam != null && date != null) 
                   gameService.insert(homeTeam, awayTeam, date);
               else {
                   invalid = true;
                   break;
               }
               
               request.setAttribute("games", gameService.getAll());
            }
                    
        } catch (ParseException e) {
           invalid = true;
        }
            
        if (invalid)
            request.setAttribute("uploadMessage", "parseError");
        else 
            request.setAttribute("uploadMessage", "success");

        workbook.close();
        readFile.close();

        processSelect(request);
        request.getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method. (no content)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Sets the filenames attribute.
     * 
     * @param request servlet request
     */
    private void processSelect(HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("") + "\\uploads";
        ArrayList<String> fileNames = null;
        try {
            File folder = new File(realPath);
            File[] folderItems = folder.listFiles();
            fileNames = new ArrayList<>();

            for (File read : folderItems) {
                fileNames.add(read.getName());
            }
                    request.setAttribute("fileNames", fileNames);

        } catch (Exception e) {
            request.setAttribute("fileNames", null);
        }
    }
}
