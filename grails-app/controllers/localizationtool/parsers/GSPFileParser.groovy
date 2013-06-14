package localizationtool.parsers

import localizationtool.FileParser
import org.apache.commons.lang.StringEscapeUtils

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 30/5/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
class GSPFileParser implements FileParser{
    def getReportEntries(String fileText, type = 'positive'){
        def outputStr
        fileText = fileText.replaceAll("%\\{--.*?(\\s.*?)*--\\}%"," ") // remove commented lines
//        fileText = fileText.replaceAll("(<!--)(.*?)(\\s*(.*?)){0,99}(-->)"," ") // remove commented lines
        def listOccurred = fileText.findAll("(?i)<* (value|alt|title)\\s*=\\s*\"\\s*(?![\"|'])(?!\\\$\\{(.*?)\\})(.*?)\"")
        // def listOccurred2 = fileText.findAll(gspRegExp2)
        //if(listOccurred2)
        //   render("<br/>  <u>Occurred2:</u> "+StringEscapeUtils.escapeHtml(listOccurred2.toListString()))
        def listOccurred3 = fileText.findAll("(?i)<*>(?!--}%)(?!\\s*<)(?!<g:)(?!:<)(?!&nbsp;<)(?!\\s*\\*\\s*<)(?!\\\$\\{(.*?)\\})(.*?)<")
        /*
        if(listOccurred)
            println("<br/> <u>Occurred1:</u> "+StringEscapeUtils.escapeHtml(listOccurred.toListString())+"<br/>")
        if(listOccurred3)
            println("<br/>  <u>Occurred3:</u> "+StringEscapeUtils.escapeHtml(listOccurred3.toListString())+"<br/>")
        */
//        return outputStr
        return listOccurred+listOccurred3

    }

    def getAllKeys(String fileText){
        //ToDO: Need add regEx for finding all added keys
        return fileText.findAll('(?i)(.*)\\s*=')
    }

    def isKeyAssigned(String fileText, String key){
        def result =fileText.findAll(key)
        if(result && result.size() > 0)
            return true
        return false
    }
}
