package resourceTest;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLReader {
	/**
	 * @param args
	 * @throws DocumentException
	 */

	public static void main(String[] args) throws DocumentException {
//		1.ͨ��Itrator��������ڵ�
//		f();		
//		2.ͨ��treeWalk()�����ڵ�
//		Document doc = createDoc();
//		treeWalk(doc);
//		3.xpath
		Document doc = createDoc();
		List<Node> list = doc.selectNodes("//@* ");
//		System.out.println(list);
		for (Iterator<Node> it = list.iterator(); it.hasNext();) {
			Node node = (Node) it.next();
//			System.out.println(node.valueOf("@name"));
//			System.out.println(node.getNodeTypeName());
			System.out.println(node.getStringValue());
		}
		
	}


	public static void f() throws DocumentException {
		Document doc = createDoc();
		// ѡ���ı��ڵ�
		// doc.selectNodes("//a/@href");
		Element root = doc.getRootElement();
		System.out.println();
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element ele = it.next();
			System.out.println(ele.getName());
		}
	}

	public static Document createDoc() throws DocumentException {
		File f = new File(Class.class.getResource("/").getPath() + "applicationContext.xml");
		System.out.println(f.toURI());
//		System.out.println(f.canExecute());
		return new SAXReader().read(f);
	}
	public static void treeWalk(Document doc){
		treeWalk(doc.getRootElement());
	}
	public static void treeWalk(Element element) {
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node node = element.node(i);
			if(node instanceof Element){
				treeWalk((Element)node);
				System.out.println(node.getName());
				System.out.println(node.getStringValue());
			}
			else{
//				System.out.println(node.getText());
			}
		}
	}
}
