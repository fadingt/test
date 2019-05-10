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

	private static void printNode(Node node, int count) {
		if (node != null) {
			StringBuilder tmp = new StringBuilder();
			for (int i = 0; i < count; i++)
				tmp.append(" ");
			int nodeType = node.getNodeType();
			switch (nodeType) {
			case Node.ATTRIBUTE_NODE:
				tmp.append("ATTRIBUTE");
				break;
			case Node.CDATA_SECTION_NODE:
				tmp.append("CDATA_SECTION");
				break;
			case Node.COMMENT_NODE:
				tmp.append("COMMENT");
				break;
			case Node.DOCUMENT_FRAGMENT_NODE:
				tmp.append("DOCUMENT_FRAGMENT");
				break;
			case Node.DOCUMENT_NODE:
				tmp.append("DOCUMENT");
				break;
			case Node.DOCUMENT_TYPE_NODE:
				tmp.append("DOCUMENT_TYPE");
				break;
			case Node.ELEMENT_NODE:
				tmp.append("ELEMENT");
				break;
			case Node.ENTITY_NODE:
				tmp.append("ENTITY");
				break;
			case Node.ENTITY_REFERENCE_NODE:
				tmp.append("ENTITY_REFERENCE");
				break;
			case Node.NOTATION_NODE:
				tmp.append("NOTATION");
				break;
			case Node.PROCESSING_INSTRUCTION_NODE:
				tmp.append("PROCESSING_INSTRUCTION");
				break;
			case Node.TEXT_NODE:
				tmp.append("TEXT");
				break;
			default:
				return;// invalid node type.
			}

			System.out.println(tmp + " (" + node.getNodeName() + "," + node.getNodeValue() + ")");
			/*
			 * node.getAttributes()·½·¨·µ»Ø °üº¬node½ÚµãµÄÊôÐÔµÄ NamedNodeMap£¨Èç¹ûËüÊÇ Element£©
			 */
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null)
				for (int i = 0; i < attrs.getLength(); i++) {
					printNode(attrs.item(i), count + 1);
				}
			/*
			 * node.getChildNodes()·½·¨·µ»Ø °üº¬node½ÚµãµÄËùÓÐ×Ó½ÚµãµÄ NodeList¡£
			 */
			NodeList childNodes = node.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				printNode(childNodes.item(i), count + 1);
			}
		}
	}

}
