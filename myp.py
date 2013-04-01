import commands
import random
from random import choice





for i in range(100):
	hiddenLayer = choice(["a","i","o","t"])
	com = "java -Xmx1024m -classpath weka.jar weka.classifiers.functions.MultilayerPerceptron -L {2} -M {3} -N 500 -V 0 -S {1} -E 20 -H {4} -R -D\
	-t data/mammo.arff -split-percentage 66 2>&1 > results/nn-orig/nn/mammo.arff-n$n-i{0}\.txt"\
	.format(i, random.randint(0,1000), random.random(), random.random(), hiddenLayer )
	print commands.getstatusoutput(com)

