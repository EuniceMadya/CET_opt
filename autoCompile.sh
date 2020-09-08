#Componenets
Components=CET-impl/src/Components/*.java

#utils
DagGenTool=CET-impl/src/util/dagGen/*.java
ArrayQueue=CET-impl/src/util/ArrayQueue.java
ArrayStack=CET-impl/src/util/ArrayStack.java
Processor=CET-impl/src/util/GraphProcessor.java
Generator=CET-impl/src/util/GraphGenerator.java
FileParser=CET-impl/src/util/FileGraphParser.java
GraphBuilder=CET-impl/src/util/GraphBuilder.java
RandomTimeGen=CET-impl/src/util/RandomTimeGenerator.java
AnchorProcessor=CET-impl/src/util/AnchorProcessor.java
GraphType=CET-impl/src/util/GraphType.java

Util="$DagGenTool $ArrayQueue $ArrayStack $Generator $FileParser $GraphType $GraphBuilder $RandomTimeGen $AnchorProcessor"

#Algos
TraversalAlgos=CET-impl/src/Traversal/*.java

#Main executor
Executor=CET-impl/src/AlgoExecutor.java
Main=CET-impl/src/Main.java



javac $Components $Util $TraversalAlgos $Executor $Main -d out/

cd out/
java Main
