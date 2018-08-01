package properties;

import org.apache.crimson.tree.DOMImplementationImpl;
import org.w3c.dom.*;
import view.ErrorDialog;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hammer on 10.07.2017.
 */
public final class AppSettings {

    private HashMap fHashMap;
    private static AppSettings SINGLETON;

    static {
        SINGLETON = new AppSettings();
    }

    private AppSettings() {
        fHashMap = new HashMap();
    }

    public static Object get(String key) {
        return SINGLETON.fHashMap.get(key);
    }

    public static Object get(String key, Object deflt) {
        Object obj = SINGLETON.fHashMap.get(key);
        if (obj == null) {
            return deflt;
        } else {
            return obj;
        }
    }

    public static int getInt(String key, int deflt) {
        Object obj = SINGLETON.fHashMap.get(key);
        if (obj == null) {
            return deflt;
        } else {
            return new Integer((String) obj).intValue();
        }
    }

    public static boolean save(File file) throws Exception {
        // Create new DOM tree
        DOMImplementation domImpl = new DOMImplementationImpl();
        Document doc = domImpl.createDocument(null, "app-settings", null);
        Element root = doc.getDocumentElement();
        Element propertiesElement = doc.createElement("properties");
        root.appendChild(propertiesElement);
        Set set = SINGLETON.fHashMap.keySet();
        if (set != null) {
            for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
                String key = iterator.next().toString();
                Element propertyElement = doc.createElement("property");
                propertyElement.setAttribute("key", key);
                Text nameText = doc.createTextNode(get(key).toString());
                propertyElement.appendChild((Node) nameText);
                propertiesElement.appendChild(propertyElement);
            }
        }
        // Serialize DOM tree into file
        DOMSerializer serializer = new DOMSerializer();
        serializer.serialize(doc, file);
        return true;
    }

    public static void clear() {
        SINGLETON.fHashMap.clear();
    }

    public static void put(String key, Object data) {
        //prevent null values. Hasmap allow them
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            SINGLETON.fHashMap.put(key, data);
        }
    }

    public static boolean load(File file) throws Exception {
//        System.out.println(file.getAbsolutePath().toString());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc;
        try {
            doc = builder.parse(file);

            if (doc == null) {
                throw new NullPointerException();
            }

            NodeList propertiesNL = doc.getDocumentElement().getChildNodes();
            if (propertiesNL != null) {
                for (int i = 0; (i < propertiesNL.getLength()); i++) {
                    if (propertiesNL.item(i).getNodeName().equals("properties")) {
                        NodeList propertyList = propertiesNL.item(i).getChildNodes();
                        for (int j = 0; j < propertyList.getLength(); j++) {
                            NamedNodeMap attributes = propertyList.item(j).getAttributes();
                            if (attributes != null) {
                                Node n = attributes.getNamedItem("key");
                                NodeList childs = propertyList.item(j).getChildNodes();
                                if (childs != null) {
                                    for (int k = 0; k < childs.getLength(); k++) {
                                        if (childs.item(k).getNodeType() == Node.TEXT_NODE) {
                                            put(n.getNodeValue(), childs.item(k).getNodeValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
//                printHash();
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException ex) {
            new ErrorDialog("Не найден файл конфигурации.");
            return false;
        }
    }

    private static void printHash(){
        System.out.println(SINGLETON.fHashMap.toString());
    }
}
