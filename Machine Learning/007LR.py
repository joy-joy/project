#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 28 04:19:50 2017

@author: joy
"""
"""
This program implements "Logistic Regression" from scratch
on the Titanic Dataset (files: titanic.train2.csv and 
titanic.test2.csv)
"""

from random import seed
from csv import reader
from math import exp

# Load CSV file
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

# Find the min and max values for each column
def Minmax(dataset):
    minmax = []
    for i in range(len(dataset[0])):
        columnValues = [row[i] for row in dataset]
        minValue = min(columnValues)
        maxValue = max(columnValues)
        minmax.append([minValue, maxValue])
    return minmax

# normalize to range 0-1
def normalizeDataset(dataset, minmax):
    for row in dataset:
        for i in range(len(row)):
            if minmax[i][1] != minmax[i][0]:
                row[i] = (row[i] - minmax[i][0]) / (minmax[i][1] - minmax[i][0])
            elif minmax != 0:
                row[i] == row[i]/row[i]
            else:
                continue  
            
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

def categoricalToNumeric1(dataset, column=1):
    categoryValues2 = ['male', 'female']
    
        
    for row in dataset:
        for i in range(len(categoryValues2)):
            if row[column] == categoryValues2[i]:
                row[column] = i                

def categoricalToNumeric2(dataset, column=-2):
    categoryValues2 = ['Q', 'C', 'S']
    
        
    for row in dataset:
        for i in range(len(categoryValues2)):
            if row[column] == categoryValues2[i]:
                row[column] = i

# Test the logistic regression algorithm on the Titanic dataset
seed(1)


# load and prepare data
trainFile = 'titanic.train2.csv'
trainSet = loadCsv(trainFile)

testFile = 'titanic.test2.csv'
testSet = loadCsv(testFile)


columnsToRemove = ['PassengerId', 'Name', 'Ticket', 'Cabin', 'isTrainSet']
removeManyColumns(trainSet, columnsToRemove)
moveColumnToEnd(trainSet, "Survived")
headerTrain = trainSet.pop(0)


categoricalToNumeric2(trainSet)
categoricalToNumeric1(trainSet)


for i in range(len(trainSet[0])):
    stringToFloat(trainSet, i)

# normalize
minMaxTrain = Minmax(trainSet)
normalizeDataset(trainSet, minMaxTrain)


#-------------------------------------
#Test Set

columnsToRemove = ['PassengerId', 'Name', 'Ticket', 'Cabin', 'isTrainSet']
removeManyColumns(testSet, columnsToRemove)
moveColumnToEnd(testSet, "Survived")
headerTest = testSet.pop(0)


categoricalToNumeric2(testSet)
categoricalToNumeric1(testSet)

for i in range(len(testSet[0])):
    stringToFloat(testSet, i)

# normalize
minMaxTest = Minmax(testSet)
normalizeDataset(testSet, minMaxTest)
    

#-----------------------------------
# Predict the outcome of an example, row, given weights, w (returns hwOfX)
def predict(row, w):
    linearPredictor = w[0]
    for i in range(len(row)-2):
        linearPredictor += w[i + 1] * row[i]
        hwOfX = 1.0 / (1.0 + exp(-linearPredictor))
    return hwOfX

# Estimate the logistic regression weights using gradient descent
def weights(trainSet, learningRate, noOfEpochs):
    w = [0.0 for i in range(len(trainSet[0])-1)]
    squaredErrorCount = []
    for epoch in range(noOfEpochs):
        sumOfErrorSquared = 0
        for row in trainSet:
            hwOfX = predict(row, w)
            error = row[-1] - hwOfX
            w[0] = w[0] + learningRate * error * hwOfX * (1.0 - hwOfX)
            for i in range(len(row)-2):
                w[i + 1] = w[i + 1] + learningRate * error * hwOfX * (1.0 - hwOfX) * row[i]
        squaredErrorCount.append(sumOfErrorSquared)                   
    return w

        

# Linear Regression algorithm using gradient descent
def logisticRegression(trainSet, testSet, learningRate, noOfEpochs):
    predicted = []
    w = weights(trainSet, learningRate, noOfEpochs)
    for row in testSet:
        hwOfX = predict(row, w)
        hwOfX = round(hwOfX)
        predicted.append(hwOfX)
    return predicted

def accuracyMetric(actual, predicted):
    correct = 0
    for i in range(len(actual)):
        if actual[i] == predicted[i]:
            correct += 1
    return correct / float(len(actual)) * 100.0

#-------------------------------------  
# evaluate algorithm

learningRate = 0.1
noOfEpochs = 100
w = weights(trainSet, learningRate, noOfEpochs)
predicted = logisticRegression(trainSet, testSet, learningRate, noOfEpochs)
actual = [row[-1] for row in testSet]
accuracy = accuracyMetric(actual, predicted)
print("Learning Rate = %.2f, Number of Epochs = %d, Accuracy = %.3f%%" % (learningRate, noOfEpochs, accuracy)) 


#-----------------------------------
# More evaluations (with different learning rates and no. of epochs)

learningRate = 0.05
noOfEpochs = 1000
predicted = logisticRegression(trainSet, testSet, learningRate, noOfEpochs)
actual = [row[-1] for row in testSet]
accuracy = accuracyMetric(actual, predicted)
print("Learning Rate = %.2f, Number of Epochs = %d, Accuracy = %.3f%%" % (learningRate, noOfEpochs, accuracy)) 

learningRate = 0.01
noOfEpochs = 100
predicted = logisticRegression(trainSet, testSet, learningRate, noOfEpochs)
actual = [row[-1] for row in testSet]
accuracy = accuracyMetric(actual, predicted)
print("Learning Rate = %.2f, Number of Epochs = %d, Accuracy = %.3f%%" % (learningRate, noOfEpochs, accuracy)) 


learningRate = 0.01
noOfEpochs = 50
predicted = logisticRegression(trainSet, testSet, learningRate, noOfEpochs)
actual = [row[-1] for row in testSet]
accuracy = accuracyMetric(actual, predicted)
print("Learning Rate = %.2f, Number of Epochs = %d, Accuracy = %.3f%%" % (learningRate, noOfEpochs, accuracy)) 

learningRate = 0.01
noOfEpochs = 1000
predicted = logisticRegression(trainSet, testSet, learningRate, noOfEpochs)
actual = [row[-1] for row in testSet]
accuracy = accuracyMetric(actual, predicted)
print("Learning Rate = %.2f, Number of Epochs = %d, Accuracy = %.3f%%" % (learningRate, noOfEpochs, accuracy)) 
