//> using scala "3.2.2"

val welcomeMessage = "Welcome to AI SEX CLASSIFIER"

/** Convert a number between 0 and 1 to a binary sex. */
def binaryClassifier(x: Num): String = 
  if x > 0.5 
  then "Female" 
  else "Male  "

val trainData = DataSet.fromFile("train-data.txt")
val testData  = DataSet.fromFile("test-data.txt")

val ai = new Network(inputSize = trainData.inputs(0).size, layerSizes = List(3,2,1))

/** Show any text in color in terminal using for example colorCode=Console.RED */
def showColor(s: String, colorCode: String): String = colorCode + s + Console.RESET

/** Use data to test our ai. A loss close to zero represents high certainty. **/
def test(data: DataSet): Unit =
  for i <- data.inputs.indices do
    val predicted = ai.predict(data.inputs(i))
    val correct = data.correctOutputs(i)
    val loss = meanSquareError(predicted, correct)

    val predictedSex = binaryClassifier(predicted(0))
    val correctSex   = binaryClassifier(correct(0))
    
    val showPredicted = 
      if predictedSex == correctSex 
      then showColor(predictedSex, Console.GREEN)
      else showColor(predictedSex, Console.RED)

    println(
      s"${data.inputs(i).mkString(",")} " +
      s"correct=${binaryClassifier(correct(0))} ${correct.mkString(",")}  " +
      s"predicted=$showPredicted  ${predicted.mkString(",")} loss=$loss") 

/** The main program. Run it in terminal with `scala-cli run .` */
@main def run = 
  println(s"\n--- $welcomeMessage\n")
  println(ai.show)
  val n = 600
  println(s"\n--- TRAINING in $n cycles")
  ai.train(cycles = n,  data = trainData)
  println(s"\n--- TESTING")
  test(testData)

