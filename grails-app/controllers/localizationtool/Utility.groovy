package localizationtool

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 3/6/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
class Utility {

    static def getExtensionFromFilename(filename) {
        def m = (filename =~ /(\.[^\.]*)$/)
        if (m.size() > 0)
            return ((m[0][0].size() > 0) ? m[0][0].substring(1).trim().toLowerCase() : "")
    }

    static def isValidFile(File file, String fileTypes) {
        if (!file.name.equalsIgnoreCase("_eula.gsp") && file.path.indexOf("\\affiserv\\plugins\\") == -1 && file.path.indexOf("\\tests\\") == -1) {
            def extension = getExtensionFromFilename(file.name)
            if (extension != null && !extension.isEmpty() && fileTypes.indexOf(extension.toLowerCase()) > -1) {
                if(!extension.equalsIgnoreCase("properties"))
                    return true
                if(file.name.equalsIgnoreCase("messages-nimble_de.properties") || file.name.equalsIgnoreCase("messages-nimble.properties") || file.name.equalsIgnoreCase("messages.properties") || file.name.equalsIgnoreCase("messages_de.properties"))
                    return true
            }
        }
        return false
    }

}
