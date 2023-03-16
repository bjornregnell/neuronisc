package scai

class Neuron(layer: Int, index: Int, val inputs: Array[Float], val outputs: Array[Float]):
  def size = inputs.size

  var bias: Float = gauss()

  var weights: Array[Float] = Array.fill(size)(gauss())

  /** Initiate this neuron to a random state. */
  def reset(): Unit =
    bias = gauss()
    var i = 0
    while i < weights.length do 
      weights(i) = gauss()
      i += 1
  
  /** Adjust the state according to delta. */
  def adjust(deltaBias: Float, deltaWeights: Array[Float]): Unit =
    bias += deltaBias
    var i = 0
    while i < size do
      weights(i) += deltaWeights(i)
      i += 1

  /** Randomly adjust the state, scaled by learnFactor. */
  def mutate(learnFactor: Float = 1.0): Unit = 
    bias +=  learnFactor * gauss()
    var i = 0
    while i < size do 
      weights(i) += learnFactor * gauss()
      i += 1

  /** Compute output value. The sigmoid call constrains output in [0..1] */ 
  def output(): Float = sigmoid(weights * inputs + bias)

  def forwardFeed(): Unit = outputs(index) = output()
  
  var savedBias: Float = bias

  var savedWeights: Array[Float] = weights.clone()
  
  /** Forget current state and restore saved state */
  def backtrack(): Unit = 
    bias = savedBias
    var i = 0
    while i < size do 
      weights(i) = savedWeights(i)
      i += 1
  
  /** Remember current state. */
  def save(): Unit =
    savedBias = bias
    var i = 0
    while i < size do 
      savedWeights(i) = weights(i)
      i += 1
