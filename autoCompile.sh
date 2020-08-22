#Componenets
Components=CET-impl/src/Components/*.java

#utils
DagGenTool=CET-impl/src/util/dagGen/*.java
ArrayQueue=CET-impl/src/util/ArrayQueue.java
Processor=CET-impl/src/util/GraphProcessor.java
Generator=CET-impl/src/util/GraphGenerator.java
FileParser=CET-impl/src/util/FileGraphParser.java
GraphBuilder=CET-impl/src/util/GraphBuilder.java
RandomTimeGen=CET-impl/src/util/RandomTimeGenerator.java

Util="$DagGenTool $ArrayQueue $Processor $Generator $FileParser $GraphBuilder $RandomTimeGen"

#Algos
TraversalAlgos=CET-impl/src/Traversal/*.java

#Main executor
Executor=CET-impl/src/AlgoExecutor.java
Main=CET-impl/src/Main.java



javac $Components $Util $TraversalAlgos $Executor $Main -d out/