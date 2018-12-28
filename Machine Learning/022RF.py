#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 28 08:23:49 2017

@author: joy
"""
"""
This program implements "Random Forests" from scratch
on the Titanic Dataset (files: titanic.train2.csv and 
titanic.test2.csv)
"""

from random import seed
from csv import reader
from random import randrange

# Load a CSV file
def loadCsv(filename):
    dataset = []
    with open(filename, 'r') as file:
        csvReader = reader(file)
        for row in csvReader:
            if not row:
                continue
            dataset.append(row)
    return dataset            
                  
# Convert string column to float
def stringToFloat(dataset, column):
    for row in dataset:
        row[column] = float(row[column])
        
# Convert string column to int
def stringToInt(dataset, column):
    for row in dataset:
        row[column] = int(row[column])

# Calculate accuracy percentage
def accuracyMetric(actual, predicted):
    correct = 0
    for i in range(len(actual)):
        if actual[i] == predicted[i]:
            correct += 1
    return correct / float(len(actual)) * 100.0

def removeColumns(dataset, columnName):
    columnIndex = findColumnIndex(dataset, columnName)
    for row in dataset:
        del row[columnIndex]

def findColumnIndex(dataset, columnName):
    for row in dataset:
        for i in range(len(row)):
            if row[i] == columnName:
                return i

def removeManyColumns(dataset, columnsToRemove):
    for column in columnsToRemove:
        removeColumns(dataset, column)

def moveColumnToEnd(dataset, columnName):
    columnIndex = findColumnIndex(dataset, columnName)
    for row in dataset:
        value = row[columnIndex]
        del row[columnIndex]
        row.append(value)

# load and prepare data
seed(1)

trainFile = 'titanic.train2.csv'
trainSet = loadCsv(trainFile)

testFile = 'titanic.test2.csv'
testSet = loadCsv(testFile)

#-------------------------------------
#Train Set

columnsToRemove = ['PassengerId', 'Name', 'Ticket', 'Cabin', 'isTrainSet']
removeManyColumns(trainSet, columnsToRemove)
moveColumnToEnd(trainSet, "Survived")
headerTrain = trainSet.pop(0)

stringToFloat(trainSet, 2)
stringToFloat(trainSet, 5)
stringToInt(trainSet, 0)
stringToInt(trainSet, 3)
stringToInt(trainSet, 4)
stringToInt(trainSet, -1)

#-------------------------------------
#Test Set

columnsToRemove = ['PassengerId', 'Name', 'Ticket', 'Cabin', 'isTrainSet']
removeManyColumns(testSet, columnsToRemove)
moveColumnToEnd(testSet, "Survived")
headerTest = testSet.pop(0)

stringToFloat(testSet, 2)
stringToFloat(testSet, 5)
stringToInt(testSet, 0)
stringToInt(testSet, 3)
stringToInt(testSet, 4)
stringToInt(testSet, -1)

# Count no. of observations for the various outcome classes 
def countClasses(rows):
    
    counts = {}  
    for row in rows:
        outcome = row[-1]
        if outcome not in counts:
            counts[outcome] = 0
        counts[outcome] += 1
    return counts

def isNumeric(value):
    # int and float considered to be numeric
    if isinstance(value, int) or isinstance(value, float):
        return True
    else:
        return False

class SplitCondition:
    
    def __init__(self, column, value):
        self.column = column
        self.value = value

    def match(self, row):
        # Compare the feature value in a row to the
        # feature value in this split condition.
        val = row[self.column]
        if isNumeric(val):
            return val >= self.value
        else:
            return val == self.value
    
# splits given rows into trueChild and falseChild (s)
def partition(rows, splitCondition):
      
    trueRows = []
    falseRows = []
    for row in rows:
        if splitCondition.match(row):
            trueRows.append(row)
        else:
            falseRows.append(row)
    return trueRows, falseRows


def gini(rows):
    
    # receive dictionary, e.g. {0:10, 1:25}
    counts = countClasses(rows)
    impurity = 1
    
    for labelOfClass in counts:
        probabilityOfLabel = counts[labelOfClass] / float(len(rows))
        impurity -= probabilityOfLabel**2
    return impurity

def infoGain(left, right, currentUncertainty):
 
    p = float(len(left)) / (len(left) + len(right))
    return currentUncertainty - p * gini(left) - (1 - p) * gini(right)

# Best-Split-Condition and corresponding Best-Gain-Amount is returned
def findBestSplit(rows, columnsOfFeaturesUsed):
    
    bestGain = 0  
    bestSplitCondition = None  
    currentUncertainty = gini(rows)
    
    # only given column indexes used
    for column in columnsOfFeaturesUsed:  

        values = set([row[column] for row in rows])

        for val in values:  

            splitCondition = SplitCondition(column, val)

            trueRows, falseRows = partition(rows, splitCondition)

            if len(trueRows) == 0 or len(falseRows) == 0:
                continue

            gain = infoGain(trueRows, falseRows, currentUncertainty)

            
            if gain >= bestGain:
                bestGain, bestSplitCondition = gain, splitCondition

    return bestGain, bestSplitCondition


class Leaf:
    
    # self.predictions saves a dictionary, e.g. {0:7, 1:14} for the leaf
    
    def __init__(self, rows):
        self.predictions = countClasses(rows)

class Node:
   
    def __init__(self,
                 splitCondition,
                 trueChild,
                 falseChild):
        
        self.splitCondition = splitCondition
        self.trueChild = trueChild
        self.falseChild = falseChild

def buildTree(rows, columnsOfFeaturesUsed):
        
    gain, splitCondition = findBestSplit(rows, columnsOfFeaturesUsed)
    
    if gain == 0:
        return Leaf(rows)

    trueRows, falseRows = partition(rows, splitCondition)

    
    trueChild = buildTree(trueRows, columnsOfFeaturesUsed)
    falseChild = buildTree(falseRows, columnsOfFeaturesUsed)

    return Node(splitCondition, trueChild, falseChild)


def buildRandomForests(trainSet, mTry, noOfTrees):
    
    myTrees = []
    
    for count in range(noOfTrees):
    
        noOfFeatures = len(trainSet[0]) - 1
        columnsOfFeaturesUsed = []    
        featureIndices = [index for index in range(noOfFeatures)]
    
        while len(columnsOfFeaturesUsed) < mTry:
            index = randrange(len(featureIndices))
            columnsOfFeaturesUsed.append(featureIndices.pop(index))
         
        myTrees.append(buildTree(trainSet, columnsOfFeaturesUsed))
    return myTrees

#-------------------------------------
# Building different Random Forests (different no. of trees values)

rf = buildRandomForests(trainSet, 3, 20)
rf2 = buildRandomForests(trainSet, 3, 50)
rf3 = buildRandomForests(trainSet, 3, 100)
rf4 = buildRandomForests(trainSet, 3, 500)

def classify(row, node):
    
    # leaf node?
    if isinstance(node, Leaf):
        return node.predictions

    if node.splitCondition.match(row):
        return classify(row, node.trueChild)
    else:
        return classify(row, node.falseChild)

def survivedOrNot(counts):
    total = sum(counts.values())
    if 1 in counts.keys():
        survived = counts[1]/total
    elif 0 in counts.keys() and counts[0] > 0:
        survived = 0
        
    return round(survived)
    
def majorityVotes(predicted):
    
    count = 0
    for val in predicted:
        count += val
    averageCount = count/len(predicted)    
        
    if averageCount <= 0.5:
        return 0
    else:
        return 1
        
def classifyRandomForests(testSet, randomForests):
    predicted = []
    for row in testSet:
        outcome = []
        for tree in randomForests:
            outcome.append(survivedOrNot(classify(row, tree)))
    
        predicted.append(majorityVotes(outcome))
    return (predicted)   

#-------------------------------------    
# Classify
predicted = classifyRandomForests(testSet, rf)    
predicted2 = classifyRandomForests(testSet, rf2)    
predicted3 = classifyRandomForests(testSet, rf3)
predicted4 = classifyRandomForests(testSet, rf4)

#-------------------------------------  
# Evaluate
actual = [row[-1] for row in testSet]

accuracy = accuracyMetric(actual, predicted)
accuracy2 = accuracyMetric(actual, predicted2)
accuracy3 = accuracyMetric(actual, predicted3)
accuracy4 = accuracyMetric(actual, predicted4)

print("noOfTrees = %d, Accuracy = %.3f%%" % (20, accuracy)) 
print("noOfTrees = %d, Accuracy = %.3f%%" % (50, accuracy2))        
print("noOfTrees = %d, Accuracy = %.3f%%" % (100, accuracy3))
print("noOfTrees = %d, Accuracy = %.3f%%" % (500, accuracy4))