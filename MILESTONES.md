# Milestones

## Milestone 4

### Summary Update
This week the team focused on frontend and backend implementation. The backend currently supports tracking primitive variables and arrays. Additionally, aliasing is supported. After discussions with our TA, we plan to update our current implementation of statement/line tracking to include surrounding control flow/methods when appropriate. Furthermore, rather than supporting non-unique variables, we will change our implementation such that locally defined variables are only tracked within the scope of that method and also add support for globally tracked variables. A basic frontend was also implemented as per our mock up. In terms of frontend inmprovements, our TA also suggested that our original mock up may be too cluttered if the tracked variables undergo many mutations so instead we plan on implementing clickable slices which open into a new window and is expanded.

### Final User Study Plans
Now that we have implemented the variable tracking, we can get feedback on the functionality and visual representations from users. In the study, we will ask 2-3 users to write a simple program that assigns and reassign variables and then use the program analysis tool. The users will give feedback on how easy and intuitive it was to use and how useful they found it. Additionally, we will write a program with intentional bugs and ask our users to attempt to debug the program using our analysis tool.

### Planned Timeline For Remaining Days
**Backend TODOs:**
* Support user-defined objects
* support the rest of the control flow blocks we haven't done yet (loops/ifs without parentheses, variables declared inside a loop header, unary/assignexprs inside a loop header, etc.)
* Support tracking of fields
* Update the statement that we put in the output so that it's not just the actual assignment/declaration, it also contains the surrounding control flow and method (TA asked for this)
    * Add support so that locally defined variables we track are restricted to the scope of that method (deals with the case where 2 methods have the same locally declared variable)
    * Add support so that global variables we track can be distinguished from local variables (deals with the case where a local variable in class A has the same name as a global variable in class B
    * If we do the above this only leaves the case where 2 classes each have a global variable with the same name
*Possible support tracking method arguments. (Currently our implementation assumes that we'll see a variable get declared)  

**FrontEnd TODOs:**  
* Add the backend and frontend connection.
* Make the frontend code editor be something that dynamically calls the backEnd
* Implement the slider visualization with different  HTML elements
* Make the the graphs clickable so you can see them on a new window expanded
* Support user defined object visualization
* Start testing after MVP done

## Milestone 3

### Summary Update

* Conducted three user studies with project mockup.
* Set-up project repo and began implementation.
* Backend:
    * Implemented basic tracking of primitive variables and arrays. Finalizing implementation to handle
      referencing/aliasing.
* Frontend:
    * Found all the libraries, based on our mockup and wireframe. Implemented most of the variable viewing page based on
      the mockup image, the toggle button for variables is working, also the program slices are shown like in the mock
      up image below. Moreover, the line chart feature is implemented.

### User Study

The team conducted 3 user studies. We showed the participants a mockup prototype of the analysis tool and described how
the tool would work. We presented an example program and the resulting visualization/analysis. For example, if the user
wrote something like this:

```
@Track(var="x", nickname="x")
@Track(var="y", nickname="y")
@Track(var="a", nickname="a")
public void foo() {
    int x =0;
    for (int i = 0; i < 5; i++) {
     // increment x;
     x++
    }
    int[] y = new int[5];
    for (int i = 0; i < 5; i++) {
     y[i] = i*2;
    }
    ObjectA a = new ObjectA(5, 10);
    a.setB(20);
   }
```

The tool would output:
<img width="1120" alt="mockup1" src="https://media.github.students.cs.ubc.ca/user/3391/files/0dae5280-3e40-11ec-96ac-03d809f44f94">

Following, we asked the participant to think about a situation when they had a bug in some code they were working on and
how they would apply our tool to assist with debugging. Since our actual tool implementation is still in progress, we
informed the participant about what our tool would produce in the steps along the way.

One thing that all the participants wanted to see was the errors related to the tracked variables (e.g. if doing
division, a divide by zero error). The group agreed that including error handling is a necessary feature and plans to
include some type of error handling/ visualization in the UI. Overall, the participants found the tool easy and
intuitive to use.

Some specific comments from study participants:

**User 1:**

Pros:

* Stats on variable usage (can prevent the use of blocking un-used memory)
* Help debug programs by variable value tracing (determine expected and unexpected results on each stage of program)
* Would help in cleaning code (i.e. get rid of debugging variables)
* Can be used for optimizing code

Cons:

* It would be hard to model variable tracing in complex data structure such as trees, hash maps
* No information about memory address provided
* Lack of JSON/XML support
* Does not provide the overall result/value of functions

**User 2:**

Pros:

* Keeps track of variable changes in case of complicated loops where values are constantly changing.
* Can choose specific variables on “Watchlist” to track only certain ones rather than manually track each of them.
* Keeps track of the types of variables.

Cons:

* Errors are not being tracked.
* Loss of data in terms of upper or lower casting is not visible.
* No visual representation of what happens when multiple variables are selected in the “Watchlist”.
* Handling switch case or if conditions are not shown.
* How to display inheritance or polymorphism.
* A user guide to explain how to read the data presented is absent.
* Program slice should have a way to navigate in case of numerous variables present.

**User 3:**
Pros:

* Good representation of how variables change though the course of a loop.
* Easy to check at each part of the loop.
* Good way to maintain loop invariants.

Cons:

* May be difficult when variable types change
* May be hard to represent if variables are in double or float.
* Errors may arise when value is divided by 0, array out of bounds or null values.
* Can't display data types like trees.

### Changes to Original Design:

Based on the user studies, some additional features that the team plans to implement include:

* Include in the visualization the final value of tracked variables (e.g. the result at the end of program execution)
* Prepare a user guide/documentation on how to interpret the visualization
* Present program runtime errors in the front end

### Next Steps:

* Backend Implementation:
    * Implement tracking of user defined objects and nested objects
    * Implement ability to track variables with the same names in different scopes
* Frontend Implementation:
    * Have to connect the JSON object to the line chart and slider based displays. Have to create the slider displays
      with HTML elements and finalize the homepage. After that will try to make it more user appealing and friendly.
* Connect frontend/backend, allowing user to easily run program analysis tool, including logging program runtime errors

## Milestone 2

### Summary Update

After discussions with Alex and our TA we further refined our proposed program analysis. We realised that tracking
variables values and types were orthogonal ideas and decided to only focus on the former. Tracking values was also the
one more strongly supported by our use case—program debugging. The team also settled on Java as the language we’d
support. We were originally considering supporting JavaScript so that we could meaningfully analyze dynamic types. Given
that we’re no longer interested in this idea we think Java’s superior class functionality makes it the better choice.

### Refined Project Scope

- Allow the user to add code annotations to indicate which variables they are interested in analysing.
- Handle logging of primitive types (int, int array, String, boolean) and user defined objects.
- Handle logging of fields within user defined objects (nested structures).
- Support variable referencing and aliasing.
- Generate a visualization of logged variables, differentiating between variables with the same name but different
  scopes.
- If time permits, potentially include a static analysis portion to the project which would generate a Program Slice
  with the variables of interest

A mock-up of our visualization is shown below:
<img width="1120" alt="mockup1" src="https://media.github.students.cs.ubc.ca/user/3391/files/0dae5280-3e40-11ec-96ac-03d809f44f94">

### Roadmap and Division of Responsibilities:

**(M3) Week of November 8th:**

- Begin program analysis implementation
- Backend (Ben, Andre, Mohammed)
- Frontend (Mikayla, Meg)
- First user study (Andre, Mohammed)
- Implementation changes given user study results (All)

**(M4) Week of November 15th:**

- Continue implementation (All)
- Draft final user study (Meg)

**(M5) Week of November 22nd:**

- Conduct final user study (Meg)
- Implementation changes based on final user study results (All)
- Finalize implementation and testing (All)
- Create final presentation and video (All)

### Next Steps

- Investigate frameworks for working with Java AST
- Set up repo for backend/frontend work
- Investigate further interactive components we could add to our visualization, including relevant libraries
- Begin designing first user study

## Milestone 1

### Summary Update

This week, the team brainstormed several possible project ideas such as tools for code coverage, generating call graphs,
code smell identifier, UML diagram generator, etc. After discussions with our TA, we settled on the idea of creating a
tool which dynamically tracks the values and types of JavaScript variables over the course of a program's execution and
display this information in a dynamic way. One use-case for this program analysis would be to help developers debug
their programs. Additionally, this could be an educational tool to teach new programmers about type coercion in
JavaScript. This would be a dynamic analysis project with a visualization component. The team is also continuing to
brainstorm possible static analysis components that we could incorporate into this idea.

### Next Steps

- Describe what type of programs the project will handle (eg. string, ints, objects) and features we want to target (eg.
  control flow)
- Describe what the visualization will look like
- Begin investigating JavaScript AST and frameworks we may want to utilize
- Planned timeline of implementation and division of labour
