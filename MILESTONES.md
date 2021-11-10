# Milestones

## Milestone 2

### Summary Update

After discussions with Alex and our TA we further refined our proposed program analysis. We realised that tracking variables values and types were orthogonal
ideas and decided to only focus on the former. Tracking values was also the one more strongly supported by our use case—program debugging. The team also settled
on Java as the language we’d support. We were originally considering supporting JavaScript so that we could meaningfully analyze dynamic types. Given that we’re
no longer interested in this idea we think Java’s superior class functionality makes it the better choice.

### Refined Project Scope

- Allow the user to add code annotations to indicate which variables they are interested in analysing.
- Handle logging of primitive types (int, int array, String, boolean) and user defined objects.
- Handle logging of fields within user defined objects (nested structures).
- Support variable referencing and aliasing.
- Generate a visualization of logged variables, differentiating between variables with the same name but different scopes.
- If time permits, potentially include a static analysis portion to the project which would generate a Program Slice with the variables of interest

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

This week, the team brainstormed several possible project ideas such as tools for code coverage, generating call graphs, code
smell identifier, UML diagram generator, etc. After discussions with our TA, we settled on the idea of creating a tool which dynamically
tracks the values and types of JavaScript variables over the course of a program's execution and display this information in a dynamic way.
One use-case for this program analysis would be to help developers debug their programs. Additionally, this could be an educational tool
to teach new programmers about type coercion in JavaScript. This would be a dynamic analysis project with a visualization component.
The team is also continuing to brainstorm possible static analysis components that we could incorporate into this idea.

### Next Steps

- Describe what type of programs the project will handle (eg. string, ints, objects) and features we want to target (eg. control flow)
- Describe what the visualization will look like
- Begin investigating JavaScript AST and frameworks we may want to utilize
- Planned timeline of implementation and division of labour
