package localizationtool

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 30/5/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
interface FileParser {

    def getReportEntries(String fileText, type)

    def getAllKeys(String fileText)

    def isKeyAssigned(String fileText, String key)


}
