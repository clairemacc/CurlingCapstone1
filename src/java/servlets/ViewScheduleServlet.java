package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ViewScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processSelect(request);
        request.getServletContext().getRequestDispatcher("/WEB-INF/viewSchedules.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selectFile = request.getParameter("selectFile");
        String realPath = request.getServletContext().getRealPath("") + "\\uploads\\" + selectFile;
        FileInputStream readFile = new FileInputStream(new File(realPath));
        Workbook workbook = new XSSFWorkbook(readFile);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();

        ArrayList<String> cells = new ArrayList<>();
        ;
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
                        Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, "Logging: " + cell.getNumericCellValue());
                        cells.add(Double.toString(cell.getNumericCellValue()));
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

        request.setAttribute("cells", cells);

        workbook.close();
        readFile.close();

        processSelect(request);
        request.getServletContext().getRequestDispatcher("/WEB-INF/viewSchedules.jsp").forward(request, response);

    }

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
