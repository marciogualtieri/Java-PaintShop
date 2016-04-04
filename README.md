## TABLE OF CONTENTS
### [TEST & BUILD THE APPLICATION](#test-and-build-the-application)
### [EXECUTE THE JAR IN THE COMMAND LINE](#execute-the-jar-in-the-command-line)
### [ON THE DESIGN CHOICES](#on-the-design-choices)
#### [ALGORITHM](#algorithm)
###### [TL;DR](#tldr)
###### [ALGORITHM ANALYSIS](#algorithm-analysis)
#### [ARCHITECTURE](#architecture)
### [PERFORMANCE](#performance)
#### [RANDOM INPUT GENERATOR](#random-input-generator)
#### [PERFORMANCE RESULTS](#performance-results)

### <a name="test-and-build-the-application"></a> TEST & BUILD THE APPLICATION

     $ maven clean test package

JavaDoc generated documentation will be available in the following file:

     ./target/apidocs/index.html

This application requires Maven 3 and Java 8 (due to the use of streams and method references).

### <a name="execute-the-jar-in-the-command-line"></a> EXECUTE THE JAR IN THE COMMAND LINE

    java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar
        {--input-file <input file with test cases>}
        [--output-file <output file with batches solutions>]

Note that ```--input-file``` is mandatory. If ```--output-file``` isn't provided, the output will be printed to the console.

Examples:

    java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar --input-file ./src/test/resources/inputs/success_from_specification.txt
    
    java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar --input-file ./src/test/resources/inputs/performance/large_dataset.txt --output-file large_dataset_output.txt
    
    java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar --input-file ./src/test/resources/inputs/performance/small_dataset.txt --output-file small_dataset_output.txt

For help execute the following command:

    java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar --help

### <a name="on-the-design-choices"></a> ON THE DESIGN CHOICES

#### <a name="algorithm"></a> ALGORITHM

##### <a name="tldr"></a> TL;DR;

The algorithm used is the following:

* Iterate through all customers. If a customer is only satisfied by swapping a batches color to matte do it, otherwise do nothing and go to the next customer.

* Iterate through all customers once again and check if solution satisfies all customers (swaps might have made batches unsatisfactory to some other customer).

* If solution batches satisfies all customers return solution, otherwise return impossible.

In the section that follows, the algorithm analysis is available in full detail. In short there are three options:

* Iterate through all possible solution batches, which has a time complexity of ```O(M * 2^N)``` (not advisable).

* Iterate through all customers. If customer is only satisfied by swapping a color to matte, check all customers affected by the change in that particular color. If any previous customer is broken return impossible, otherwise go to the next customer. The time complexity here is ```O(M * N)``` (check next section for full analysis).

* Algorithm first described and actually implemented, which has time complexity ```O(M)```.

##### <a name="algorithm-analysis"></a> ALGORITHM ANALYSIS

A naive solution would be iterating through all possible solution batches, filter the ones that satisfy all batches and then return the one with the fewer number of matte finishes.

That would not be advisable though, given that the time complexity of this approach would be:

    O(2^N * M)
    
    Where N: Number of Colors.
          M: Number of Customers.

From the requirements, N and M could be both as large as 2000. This would require iterating the following number of times:

    NI = M * 2^N = 2000 * 2^2000 = 229626139054850904846566640235536396804463540417739040095528547365153252278474062771331897263301253983689192927797492554689423792172611066285186271233330637078259978290624560001377558296480089742857853980126972489563230927292776727894634052080932707941809993116324797617889259211246623299072328443940665362688337817968917011204758969615828117801869553000858005433413251661044016264472562583522535766634413197990792836254043559716808084319706366503081778867804183841109915567179344078320163914433261165510760851167452031056697572838864109017830551567765250350871057601645685541635930907524369702298058752000

My initial approach consisted of iterating through all customers and, if swapping a bit to matte is strictly required for a particular customer, check all customers that are affected by that particular swap to matte (we would need to keep track of the customers affected by changing that color). If the swap breaks any of the previous customers (by making the batches not satisfactory to any of them), return "impossible", otherwise keep going to the last customer.

This algorithm has time complexity ```O(N * M)```. Given that only one matte is allowed per customer, the worst case scenario would the following if M > N:

               COLOR_1 COLOR_2 COLOR_3 COLOR_4 COLOR_5 COLOR_6 COLOR_7 COLOR_8 COLOR_9  ... COLOR_N
    CUSTOMER_1     M       G       G       G       G       G       G       G       G    ...     G
    CUSTOMER_2     G       M       G       G       G       G       G       G       G    ...     G
    CUSTOMER_3     G       G       M       G       G       G       G       G       G    ...     G
    CUSTOMER_4     G       G       G       M       G       G       G       G       G    ...     G
    CUSTOMER_5     G       G       G       G       M       G       G       G       G    ...     G
    CUSTOMER_6     G       G       G       G       G       M       G       G       G    ...     G
    CUSTOMER_7     G       G       G       G       G       G       M       G       G    ...     G
    CUSTOMER_8     G       G       G       G       G       G       G       M       G    ...     G
    CUSTOMER_9     G       G       G       G       G       G       G       G       M    ...     G
    ..........    ...     ...     ...     ...     ...     ...     ...     ...     ...   ...     G
    CUSTOMER_N    ...     ...     ...     ...     ...     ...     ...     ...     ...   ...     M
    ..........    ...     ...     ...     ...     ...     ...     ...     ...     ...   ...     G
    CUSTOMER_M     G       G       G       G       G       G       G       G       G    ...     G

In this particular case, for every customer ```M``` would require to backtrack ```(M - i)``` customers.

Therefore the total number of iterations would be:

    NI = (M - 1) + (M - 2) + (M - 3)  + ... + (M - N)

This is an arithmetic series. Using Gauss formula:

    NI = ( (M - 1 + M - N) * N ) / 2
    NI = M * N - N^2 / 2 - N / 2

Where the dominant term is ```M*N```, resulting in the time complexity ```O(M*N)```.

On the other hand, if M <= N:

               COLOR_1 COLOR_2 COLOR_3 COLOR_4 COLOR_5 COLOR_6 COLOR_7 COLOR_8 COLOR_9  ... COLOR_M  ... COLOR__N
    CUSTOMER_1     M       G       G       G       G       G       G       G       G    ...     G    ...     G
    CUSTOMER_2     G       M       G       G       G       G       G       G       G    ...     G    ...     G
    CUSTOMER_3     G       G       M       G       G       G       G       G       G    ...     G    ...     G
    CUSTOMER_4     G       G       G       M       G       G       G       G       G    ...     G    ...     G
    CUSTOMER_5     G       G       G       G       M       G       G       G       G    ...     G    ...     G
    CUSTOMER_6     G       G       G       G       G       M       G       G       G    ...     G    ...     G
    CUSTOMER_7     G       G       G       G       G       G       M       G       G    ...     G    ...     G
    CUSTOMER_8     G       G       G       G       G       G       G       M       G    ...     G    ...     G
    CUSTOMER_9     G       G       G       G       G       G       G       G       M    ...     G    ...     G
    ..........    ...     ...     ...     ...     ...     ...     ...     ...     ...   ...     G    ...     G
    CUSTOMER_M     G       G       G       G       G       G       G       G       G    ...     M    ...     G

The number of iterations is:

    NI = (M - 1) + (M - 2) + (M - 3)  + ... + 1

This is also an arithmetic series:

    NI = (M - 1 + 1) * N / 2
    NI = M * N / 2

Where we get the same time complexity we got for M > N, i.e., ```O(M*N)```.

Let's say that we are lazy and decided to not do the backtracking. What we are going to do is swap the color to matte if required for a current customer without caring about the consequences.

After all customer are processed, we iterate through all customers again to check if the sequence satisfies all customers, otherwise we know it's impossible to find a solution.

The number of iterations in this case would be equal to ```(2 * M)``` and therefore the time complexity is ```O(M)```, which is better than ```O(M * N)```.

Even though seems wasteful to iterate through all customers twice, this algorithm is less complex than the backtracking one and has better time complexity.

#### <a name="architecture"></a> ARCHITECTURE

As a coding test is supposed to serve as portfolio of the candidate's software engineering skills, I'm adopting the mindset that would be fitting for production code.

Production code changes all the time, as well as requirements. Coding tests are usually not representative of production code's demands.

Before anyone accuses me of over-engineering, that's the reason why I'm being strict about following S.O.L.I.D. principles.

Could this application be simpler (less lines of code)? Yes. Would it be open-closed? No.

In order to achieve an open-closed design, the application has been broken into the following basic components:

![Alt text](http://g.gravizo.com/g?

/**
 * @has 1 assignedTo 1 InputParser
 */
interface InputIterator {}

interface InputParser {}

interface OutputFormatter {}

class PlainTextFileInputIterator implements InputIterator {}

class PlainTextInputParser implements InputParser {}

class TestCaseProcessor {}

class SimpleOutputFormatter implements OutputFormatter {}

/**
 * @composed 1 Has 1 InputParser
 * @composed 1 Has 1 TestCaseProcessor
 * @composed 1 Has 1 OutputFormatter
 */
class PaintShop {}
)

```PaintShop``` uses ```InputIterator```, ```InputParser```, ```TestCaseProcessor``` and ```OutputFormatter``` to perform the task of iterating through the input, parsing the input, processing the test cases and outputting the batches solutions respectively.


```InputIterator```, ```InputParser``` and ```OutputFormatter``` are abstract given that these are open to changes.

The current concrete implementation of ```InputIterator```, ```PlainTextFileInputIterator```, is used by the parser to iterate through a plain text file, but we could implement a concrete version that iterates through the standard input for instance if we want an interactive application.

The current concrete implementation of ```InputParser```, ```PlainTextInputParser```, parses inputs with the syntax defined in the specification, but we could implement another concrete version that parses an input with a different syntax.

The current concrete implementation of ```OutputFormatter```, ```SimpleOutputFormatter```, formats solution batches according with the syntax in the specification, e.g.:

    Case #1: 1 0 0 0 0
    Case #2: IMPOSSIBLE

But we could create another concrete implementation that formats the output differently.

The ```TestCaseProcessor``` has no interface on the other hand, as the rule to generate batches doesn't seem to require multiple implementations in any instance. This simplifies the design.


I also would like to be emphatic about the fact that I follow clean code principles:

>“Every time you write a comment, you should grimace and feel the failure of your ability of expression.” 
>- Robert C. Martin

Sometimes I got complaints about "not having comments in my code". I try to make my code expressive. If you don't understand what a class does by it's name (the same goes for methods and variables) I accept the criticism, but I'll stick to my guns and rename my classes, methods or variable to better communicate the intention of the code. I avoid using comments, unless they are meant for JavaDoc.

### <a name="performance"></a> PERFORMANCE
###### <a name="random-input-generator"></a> RANDOM INPUT GENERATOR

To be able to check performance, we need to create the "large data set" and "small data set" mentioned in the specification.

For that a Python script is available under ```scripts/input-generator```. For details refer to the source code. Note that this Python code has unit tests.

The usage is the following:

    ./input-generator.py <number of test cases> <number of colors> <number of customers> <max number of pairs> --output-file <name of the output file>

Examples:

    ./input-generator.py 100 10 10 2 --output-file ../../small_dataset.txt
    ./input-generator.py 5 2000 2000 3 --output-file ../../large_dataset.txt

The colors and finishes are chosen at random. Every time you run it, you will get a different file.

For reference, the ones used for the performance tests are available under the following directory:

    src/test/resources/input/performance

###### <a name="performance-results"></a> PERFORMANCE RESULTS

Running on my computer, a Lenovo Yoga 2 laptop/tablet running Ubuntu 12.04, I got the following results:

Large data set:

    03:01:40 {master} ~/workspace/IdeaProjects/Zalando/Java/Paintshop$ java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar --input-file ./src/test/resources/inputs/performance/large_dataset.txt --output-file large_dataset_output.txt
    
    Total processing time: 28 ms

Small data set:

    03:01:56 {master} ~/workspace/IdeaProjects/Zalando/Java/Paintshop$ java -jar ./target/paintshop-1.0-SNAPSHOT-jar-with-dependencies.jar --input-file ./src/test/resources/inputs/performance/small_dataset.txt --output-file small_dataset_output.txt
    
    Total processing time: 5 ms




