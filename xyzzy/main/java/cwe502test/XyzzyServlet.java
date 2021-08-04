package cwe502test;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name="XyzzyServlet", urlPatterns={"/*"})
public class XyzzyServlet extends HttpServlet {

    public String starString(String instr) {
        return "*" + instr + "*";
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setStatus(200);
        out.println("(XyzzyServlet) here is some xss: ");

        String myString = request.getParameter("foo");
        PrintWriter unicorn = out;
        String hamster = "hi" + myString;

        out.println(request.getParameter("foo")); 
        if(request.getParameter("bar") != null) {
            try {
                String b = request.getParameter("bar");
                byte[] bs = b.getBytes("UTF-8");
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));  
                Object o = ois.readObject();
                unicorn.println(myString);
                String coupon = starString(hamster);
                unicorn.println(coupon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        out.flush();
        out.close();
    }
}
