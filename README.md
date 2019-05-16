# JavaxmlParserCode
The Dom parser code can be used to merge two or more xml files having same root/parent Node irrespective of the child Nodes.
The component can be reused by specifying the the filenames of the xmls which needs to be merged .The code includes a Xpath expression which creates a tree structure of the xml’s and the dom parser code to append the contents of the xml’s . 
The output is created based upon the expression specified and stored as an Xml file.

#setting up the Project in local workspace
Create a Java Project and Import the files or import as new Project from GIT hub 
next places the Xml files to be merged in Conf folder in the project 

# Changes to be made to XmlMerger.java code 
Add new  xml file 
	File file3=new File("C:\\eclipse-workspace\\JavaSample\\conf\\books3.xml");//Input xml file 3
pass the new file to parse as input to merge method
	FileInputStream filedoc3=new FileInputStream(file3);
	Document doc = merge("bookstore", filedoc1, filedoc2, fileDoc3);
  
  Run the project and refresh the conf folder to see the final merged xml final
  
  can also specify file path for input and o/p as per requirements.
