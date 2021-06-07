package be.pxl.auctions.servlet;

import be.pxl.auctions.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/rest/count")
public class AuctionServlet extends HttpServlet {
    @Autowired
    private AuctionService auctionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        long count = auctionService.findAuctions().size();
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Er zijn momenteel  " + count + " veilingen actief.</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
