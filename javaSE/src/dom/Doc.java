package dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Doc {
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse("src/dom/book.xml");
		printNode(doc, 0);
	}

	public static void printNode(Node node, int count) {
		if (node != null) {
			String tmp = "";
			for (int i = 0; i < count; i++)
				tmp += "  ";
			// 获取node节点的节点类型，赋值给nodeType变量
			int nodeType = node.getNodeType();
			switch (nodeType) {
			case Node.ATTRIBUTE_NODE:
				tmp += "ATTRIBUTE";
				break;
			case Node.CDATA_SECTION_NODE:
				tmp += "CDATA_SECTION";
				break;
			case Node.COMMENT_NODE:
				tmp += "COMMENT";
				break;
			case Node.DOCUMENT_FRAGMENT_NODE:
				tmp += "DOCUMENT_FRAGMENT";
				break;
			case Node.DOCUMENT_NODE:
				tmp += "DOCUMENT";
				break;
			case Node.DOCUMENT_TYPE_NODE:
				tmp += "DOCUMENT_TYPE";
				break;
			case Node.ELEMENT_NODE:
				tmp += "ELEMENT";
				break;
			case Node.ENTITY_NODE:
				tmp += "ENTITY";
				break;
			case Node.ENTITY_REFERENCE_NODE:
				tmp += "ENTITY_REFERENCE";
				break;
			case Node.NOTATION_NODE:
				tmp += "NOTATION";
				break;
			case Node.PROCESSING_INSTRUCTION_NODE:
				tmp += "PROCESSING_INSTRUCTION";
				break;
			case Node.TEXT_NODE:
				tmp += "TEXT";
				break;
			default:
				return;// invalid node type.
			}

			System.out.println(tmp + " (" + node.getNodeName() + "," + node.getNodeValue() + ")");
			/*
			 * node.getAttributes()方法返回 包含node节点的属性的 NamedNodeMap（如果它是 Element）
			 */
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null)
				for (int i = 0; i < attrs.getLength(); i++) {
					printNode(attrs.item(i), count + 1);
				}
			/*
			 * node.getChildNodes()方法返回 包含node节点的所有子节点的 NodeList。
			 */
			NodeList childNodes = node.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				printNode(childNodes.item(i), count + 1);
			}
		}
	}

}
