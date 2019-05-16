package com.ibm.domParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlMerger {
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws XPathExpressionException 
	 * @throws TransformerException 
	 * 
	 * */
	
public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException  {

	File file1=new File("C:\\eclipse-workspace\\JavaSample\\conf\\books1.xml");//Input xml file 1
	File file2=new File("C:\\eclipse-workspace\\JavaSample\\conf\\books2.xml");//Input xml file 2
	FileInputStream filedoc1=new FileInputStream(file1);
	FileInputStream filedoc2=new FileInputStream(file2);
	Document doc = merge("bookstore", filedoc1, filedoc2);//Input the Expression :"bookstore" parent node Name.
	print(doc);

}

public static Document merge(String rootNodeName,FileInputStream... files) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException  {
	    XPathFactory xPathFactory = XPathFactory.newInstance();
	    XPath xpath = xPathFactory.newXPath();
	    XPathExpression compiledRootNodeName = xpath.compile(rootNodeName);
	    return merge(compiledRootNodeName, files);
	  }
/**
 * 
 * @param expression
 * @param files
 * @return
 * @throws ParserConfigurationException
 * @throws SAXException
 * @throws IOException
 * @throws XPathExpressionException
 */
	 public static Document merge(XPathExpression expression,FileInputStream... files) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    docBuilderFactory.setIgnoringElementContentWhitespace(true);
	    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    Document parsedDoc = docBuilder.parse(files[0]);

	    Node results = (Node) expression.evaluate(parsedDoc, XPathConstants.NODE);
	    if (results == null) {
	      throw new IOException(files[0]+ ": expression does not evaluate to node ");
	    }

	    for (int i = 1; i < files.length; i++) {
	      Document merge = docBuilder.parse(files[i]);
	      Node nextResults = (Node) expression.evaluate(merge,XPathConstants.NODE);
	      while (nextResults.hasChildNodes()) {
	        Node firstChildNode = nextResults.getFirstChild();
	        nextResults.removeChild(firstChildNode);
	        firstChildNode = parsedDoc.importNode(firstChildNode, true);
	        results.appendChild(firstChildNode);
	      }
	    }
	    return parsedDoc;
	  }
/**
 * 
 * @param doc
 * @throws TransformerException
 */
	  public static void print(Document doc) throws TransformerException {
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    //Output xml file :books.xml
	    Result result = new StreamResult("C:\\eclipse-workspace\\JavaSample\\conf\\books.xml");
	    transformer.transform(source, result);
	    System.out.println("Merged Xml file "+"'books.xml'"+"  is created Successfully!!!");
	  }
}
