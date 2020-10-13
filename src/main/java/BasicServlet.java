import entity.Message;
import entity.MessageList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;


public class BasicServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
//        if (request.getHeader("referer") == null) {
//            response.setStatus(400);
//            out.println("referer is not present!");
//            return;
//        }
        //response.setContentType("text/html;charset=UTF-8");


        String fromStr = request.getParameter("from");
        String toStr = request.getParameter("to");
        String format = request.getParameter("format");
        LocalDateTime from = null, to = null;
        if (fromStr != null && !fromStr.trim().isEmpty()) {
            from = LocalDateTime.parse(fromStr);
        }
        if (toStr != null && !toStr.trim().isEmpty()) {
            to = LocalDateTime.parse(toStr);
        }
        List<Message> messageList = ChatManager.listMessage(from, to);
        if (format == null || format.equals("plain-text")) {
            response.setHeader("Content-Disposition", "attachment;filename=messageLog.txt");
            for (Message message : messageList) {
                out.println(message);
            }
        } else if (format.equals("xml")) {
            response.setHeader("Content-Disposition", "attachment;filename=messageLog.xml");
            MessageList messages = new MessageList();
            messages.setMessageList(messageList);
            out.println(XMLUtils.toXMLString(messages));
        } else {
            response.setStatus(400);
            out.println("format: [" + format + "] is invalid");
            return;
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String message = request.getParameter("message");
        PrintWriter out = response.getWriter();
        if (user == null) {
            user = "anonymous";
        }
        Message msg = ChatManager.postMessage(user, message);
        out.println(msg);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromStr = request.getParameter("from");
        String toStr = request.getParameter("to");
        LocalDateTime from = null, to = null;
        if (fromStr != null && !fromStr.trim().isEmpty()) {
            from = LocalDateTime.parse(fromStr);
        }
        if (toStr != null && !toStr.trim().isEmpty()) {
            to = LocalDateTime.parse(toStr);
        }
        ChatManager.clearChat(from, to);
    }
}