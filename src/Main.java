import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args){

        Document document = new Document();

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "Wendo2020";
        try {
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement statement = myConn.createStatement();
            String sql = "SELECT * from products";
            ResultSet rs = statement.executeQuery(sql);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Products.pdf"));
            document.open();
            var paragraph = new Paragraph("Products and their prices");

            var table = new PdfPTable(2);
            Stream.of("Name", "Price").forEach(table::addCell);




            while (rs.next()){
                var name = rs.getString("name");
                var price = String.format("%s",rs.getInt("price"));
                table.addCell(name);
                table.addCell(price);

                System.out.println(rs.getString("name") + " "+ rs.getInt("price"));
            }
            paragraph.add(table);
            document.add(paragraph);
            document.close();

        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
