package localizationtool.parsers

import localizationtool.FileParser
import org.apache.commons.lang.StringEscapeUtils
import org.apache.juli.logging.Log

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 30/5/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
class GroovyParser implements FileParser {


    def getAllKeys(String fileText) {
        //ToDO: Need add regEx for finding all added keys
        return fileText.findAll('(?i)(.*)\\s*=')
    }

    def isKeyAssigned(String fileText, String key) {
        def result =fileText.findAll(key)
        if(result && result.size() > 0)
            return true
        return false
    }


    def getReportEntries(String fileText, type = 'positive') {

//        fileText = fileText.replaceAll("(/\\*\\*)(\\s*(.*?)\\s*)*(\\*/)"," ")             //remove comment lines
        fileText = fileText.replaceAll("(/\\*)(.*?)(\\s*(.*?)){0,99}(\\*/)"," ")             //remove comment lines
        fileText = fileText.replaceAll("//(.*)"," ")                //TO DO  Need to fix inside "" and '' removed comment lines
        def RegExCase1 ="(?i)(errorMessage|render|subject|message|msg|emailSubject)\\s*[:|=|(]\\s*[\"|\"\"\"](?!\\\$\\{message\\(code:)(?!\\w*\\.\\w*)([^\"|'])(.*?)[\"|\"\"\"]"
        def resultantList = fileText.findAll(RegExCase1)

        def RegExCase2 ="(?i)(errorMessage|render|subject|message|msg|emailSubject)\\s*[:|=|(]\\s*['|\"\"\"](?!\\\$\\{message\\(code:)(?!\\w*\\.\\w*)([^\"|'])(.*?)['|\"\"\"]"
        resultantList += fileText.findAll(RegExCase2)

        def RegExCase3 = "(?i)(rejectValue\\((['].*['],)|rejectValue\\(([\"].*[\"],))\\s*['|\"](?!\\\$\\{message\\(code:)(?!\\w*\\.\\w*)([^\"|'])(.*?)['|\"]"
        resultantList += fileText.findAll(RegExCase3)

        def RegExCase4 = "(?i)render\\s*\"\"\"(?!\\\$\\{message\\(code:)(?!\\w*\\.\\w*)([^\"|'])(.*?)\"\"\""
        resultantList += fileText.findAll(RegExCase4)

        if(resultantList && type.equals("positive")) {
            return resultantList
        }
                              /**/
        /**
         *  Negative test case which is not competed for this project.
         */
          /**
        def identifier ="(\\b([\\p{Ll}]\\p{Ll}*\\p{Lu})[a-z]*\\b)"
        def nonAlphabets="(?!(\\b([0-9])[0-9|\\.]*\\b))(.*?)[\"|']"
        def alphabetSpacer="((\\b([a-z|A-Z|])[a-z]*\\b)\\s*)*"
        def nonSpacedChar="(\\b([a-z|A-Z|0-9|])[a-z]*\\b)(?!((\\b([a-z|A-Z|0-9|])[a-z]*\\b)|((\\{|\\}|\\.|\\\$|/|-)))+)"
        def invalidStartWords="([^//|/*|message\\(code|default|message|log.info|log.debug|log.error|log.warn|serverLogFile])"
        def invalidStrings="\\\$\\{message\\(|code|"
        def invalidStringDef="((\\[(\\d|,|\\.|:)+\\])|true|class|null|offset|index|jquery|js|jqui|jqueryUi|min|minified[\"|'])"     //(\$(\w|\.)+)
        def invalidChars="(\\{|\\}|\\[|\\]|\\(|\\)|\\.|\\\$|\\?|\\:|\\+|!|#|/|-|\\*|&|=|%|^)"
        def invalidWords="(\\w*((\\{|\\}|\\[|\\]|\\(|\\)|\\.|\\\$|\\?|\\:|\\+|@|!|#|/|-|\\*|&|=|%|^)+\\w+)+(\\{|\\}|\\[|\\]|\\(|\\)|\\.|\\\$|\\?|\\:|\\+|@|!|#|/|-|\\*|&|=|%|^)*)[\"|']"//"(\\w*((\\.|:|/|\\\$|\\+|\\{|\\}|-)+\\w+)+)[\"|']"
        def invalidWords1="(\\w*((\\.)+\\w+)+(\\.)*)"

        def groovyRegExNegative="(?i)"+invalidStartWords+"\\s*([:|=|(])\\s*[\"|']([^\"|'|"+invalidStringDef+"])("+")([^"+invalidStrings+"])(?!"+identifier+"|"+invalidWords+")(.*?)[\"|']"

        //(\b([\p{Ll}]\p{Ll}*\p{Lu})[a-z]*\b)    --- a word starts with small letter and has a capital letter within the word
        //["|'](?!(\b([a-zA-Z])[a-z]*\b))(.*?)["|'] -- set of non alphabets with "3.0" or '3.0'
        //((\b([a-z|A-Z|])[a-z]*\b)\s*)*   --set of alphabets with spaces
        //"(\b([a-z|A-Z|0-9|])[a-z]*\b)(?!((\b([a-z|A-Z|0-9|])[a-z]*\b)|((\{|\}|\.|\$|/|-)))+)"   --non spaced set of char that doesn't have ((\{|\}|\.|\$|/|-))) at least once

        //def listOccurred3 = fileText.findAll("(?i)([^\\\\|\\\\*|message\\(code|default|message|log.info|log.debug|log.error|log.warn|serverLogFile])\\s*([:|=|(])\\s*[\"|'](\\b([a-zA-Z])[a-z]*\\b)([^\\\$\\{message\\(|code|([a-z|A-Z|0-9]*)\\.([a-z|A-Z|0-9]*)])(?!(\\b([\\p{Ll}]\\p{Ll}*\\p{Lu})[a-z]*\\b))(.*?)[\"|']")
        def listOccurred = fileText.findAll("['\"](?!"+invalidStrings+identifier+"|"+invalidWords+"|true|false|class|null|offset|index|jquery|js|jqui|jqueryUi|min|minified|\")(.*?)[\"]")
        listOccurred += fileText.findAll("['](?!"+invalidStrings+identifier+"|"+invalidWords+"|true|false|class|null|offset|index|jquery|js|jqui|jqueryUi|min|minified|')(.*?)[']")
        def listOccurred3 = fileText.findAll(groovyRegExNegative)

//        if(listOccurred3)
//            println("<br/><u>-ve case Occurred3:</u> "+StringEscapeUtils.escapeHtml(listOccurred3.toListString())+"<br/>")
//        return listOccurred3
        if(type.equals("negative")) {
            return listOccurred3
        }else if(type.equals("all")) {
            return listOccurred
        }
                  */
    }

}
