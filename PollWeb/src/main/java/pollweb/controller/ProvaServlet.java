package pollweb.controller;

import framework.data.dao.PollDataLayer;
import framework.result.FailureResult;
import framework.result.StreamResult;
import framework.result.TemplateResult;
import pollweb.data.model.Answer;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Question;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProvaServlet extends PollBaseController {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "key";
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("page_title", "expection");

        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        StreamResult res = new StreamResult(getServletContext());
        ServletContext sc = getServletContext();
        try {
            File temp = File.createTempFile("Statistichs", ".csv");
            Writer fstream = null;
            fstream = new OutputStreamWriter(new FileOutputStream(temp), StandardCharsets.ISO_8859_1);
            List<Question> questions = ((PollDataLayer) request.getAttribute("datalayer")).getQuestionDAO().getQuestionsByPollId(13);
            fstream.write(";");
            for(Question q: questions){
                fstream.write(q.getTextq()+ ";");
            }
            fstream.write("\n");
            List<Partecipant> participants =((PollDataLayer) request.getAttribute("datalayer")).getPartecipantDAO().getPartecipantsByPollId(13);

            for(Partecipant partecipant: participants){
             fstream.write("user: "+partecipant.getKey()+";");
             for (Question question: questions){
                fstream.write(((PollDataLayer) request.getAttribute("datalayer")).getAnswerDAO().getAnswerByQuestionIdParticipantId(partecipant.getKey(), question.getKey()).getTextA().toString()+";");

             }
                fstream.write("\n");

            }
            fstream.close();
            res.activate(temp, request, response);
    //        FileWriter ip = new FileWriter;


         //   res.activate("error.ftl.html", request, response);
         //   File f = new File(fileWriter);
          //  InputStream is = new FileInputStream(fileWriter);
               // request.setAttribute("contentType", fileWriter.);


            sc.log("CSV file was created successfully !!!");
        //    res.activate(is, 3000, fileName, request, response);


        } catch (Exception e) {
            sc.log("Error in CsvFileWriter !!!");
            request.setAttribute("message", e);
            action_error(request, response);
    }

}}
