# CSC_510_group_d
Academic Project - CSC 510 - Software Engineering

Problem Statement:
As the spring semester has started, also the race of finding a good internship started with it. 
When I look at people around me I see everyone relentlessly applying for one company after other. 
And as everyone else I had this realization few days back how monotonous and painful this process can be. 
We are filling almost same data in every other form of different companies. 
Chrome auto-fill comes to the rescue in some situations but most of the times it makes the matter only worse. 
So, as everyone wants to apply to as many companies as possible in as less time as possible because we have 
three subjects to study as well. It stuck me why can’t we revamp this auto-fill thing with context knowledge.
 
P.S: It will be lot easier to observe people doing this task for 12 hrs. As this is the only thing everyone is doing anyways.
 
The Idea:
We can come up with a chrome plugin that takes user input beforehand or learn from the usage of browser 
how the forms need to be filled. We maintain information with some context like social profile, application 
profile etc. When I go to site and try to register or create an account my plug-in should be able to 
identify the category for sit and enumerate my information accordingly. It should work on some of the 
drawbacks of chrome auto-fill like giving the same primary email ID for every site or changing all the 
entries when I select one the very last in my form (you know the drill where you have to go back and change everything).
 
Extension:
Our tool can be extended further to fill feedback form and surveys automatically for a particular user. 
Continuously monitoring the user patterns and using a mining algorithm like ALS to come up with most 
probable answers can do this. I am not saying that we are going to do this as I already said we have two 
more subjects and applications to fill. But if the time allows we can work on this to implement some small use cases.

Some more ideas we pondered on:
Static code analyzer: 
Static code analyzer would monitor the code and suggest modifications for making the code better. 
For example: 
a> if(expn) 
    stmt1 
   else  
    stmt2
  can be converted to a conditional operator.
b> a loop can be converted to a LINQ expn!
c>It can even make the code more original for example more pythonic in case of Python code, concise in case of C code.
Somrtimes IDEs do not do a good job in these aspects.
Advantages: more readable code, less complexity, standard coding convention.
There are static code analyzers already but good ones are prohibitely expensive.

Auto unit test generation: 
Provided a class this tool would generate templates for unit tests for all the methods which are visible from outside.
Problems: 
a> most of the legacy code will not be having unit tests, This tool can encourage people to write tests. 
b> Test driven development is still not strictly followed in many projects. This tool can be a great help.

Desktop Search Application: 
This application will allow the user to index files in a repository based on certain criteria and perform search on them.
For instance, one criteria could be to index only the files with a particular file extension. 
The problem now is most OS would index the whole repository, when the user is not going to search for most of the files in the repository.
And this would lead to lot of time doing delta indexing when a mass file I/O operation happens.