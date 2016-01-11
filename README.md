# CSC_510_group_d
Academic Project - CSC 510 - Software Engineering

Problems Identified
===================
1. Desktop search application:
    This application will allow the user to index files in a repository based on certain criteria and perform search on them.
    For instance, one criteria could be to index only the files with a particular file extension. This would 
    tremendously increase the search speed. Currently in operating systems like windows even though indexing 
    of flies is possible, there are no options to customize it. Indexing is performed on all files in the repository. 
    This can be time consuming and unnecessary.

2. Static code analyzer:
    Static code analyzer would monitor the code and suggest modifications for making the code better.
    For example:
    a> if(expn)
        stmt1
    else                    =======================>   can be converted to a conditional operator
        stmt2
    
    b> a loop can be converted to a LINQ expn
    Or it can even make the code more original for example more pythonic in case of Python code.
    
    There are static code analyzers already but good ones are prohibitely expensive and sometimes 
    IDEs do not do a good job in these aspects.

3. Auto unit test generation:
    Provided a class this tool would generate templates for unit tests for all the methods which are visible from outside.
    Problems: a> most of the legacy code will not be having unit tests, This tool can encourage people to write tests.
    b> Test driven development is still not strictly followed in many projects. This tool can be a great help.
    
4. image to text:
    Students scan a lot of text books to pdf files. a general problem here is that the scanned to pdf files are not searcheable.
    A tool can be build which can convert images to text.
    
5. Parsing tool:
    a tool which can convert  between several different formats. Eventhough there are tools most of them are specific with
    conversions (generally support one type of conversion). example erb file to haml file.
