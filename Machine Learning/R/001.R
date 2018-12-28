# To combine to data-frames together - factors have to line up togeter.

titanic.train <- read.csv(file = "train.csv", stringsAsFactors =  FALSE, header = TRUE)
titanic.test <- read.csv(file = "test.csv", stringsAsFactors =  FALSE, header = TRUE)

head(titanic.train)
tail(titanic.train)

head(titanic.test)
tail(titanic.test)

median(titanic.train$Age, na.rm = TRUE)
median(titanic.test$Age, na.rm=TRUE)

ncol(titanic.train)
ncol(titanic.test)

titanic.train$isTrainSet <- TRUE
titanic.test$isTrainSet <- FALSE


names(titanic.train)
names(titanic.test)

titanic.test$Survived <- NA


#copying survived column
survived <- titanic.train$Survived

# Dropping survived column from titanic.train (note no quotes on "survived")
titanic.train.rearranged <- subset(titanic.train, select = -c(Survived))

# Checking perfect correspondence of column names for train and test data
names(titanic.train.rearranged)
names(titanic.test)

# Adding "survived" column back to titanic.train.rearranged
titanic.train.rearranged$survived <- survived 

# Checking correspondence
names(titanic.train.rearranged)
names(titanic.test)


#Now lets combine the two sets & check:
titanic.combined <- rbind(titanic.train, titanic.test)

str(titanic.combined)
names(titanic.combined)
head(titanic.combined)
tail(titanic.combined)
table(titanic.combined$isTrainSet)

# If need be to change name of a colum, e.g.
# check first:
# names(titanic.full)
# then:
# names(titanic.full)[1] <- "Survived"

# Missing Values

table(titanic.combined$Embarked)
str(titanic.combined[titanic.combined$Embarked == "", "Embarked"])

# or, using:
# titanic.full[titanic.full$embarked != "C" & titanic.full$embarked != "Q" 
# + & titanic.full$embarked != "S", "embarked"]

#table(titanic.combined$embarked)
# Mode value of "S"
# Replacing missing value with "S"

table(titanic.combined$Embarked)
titanic.combined[titanic.combined$Embarked == "", "Embarked"] <- "S"
table(titanic.combined$Embarked)

# Missing Values - Age (as NA); replacing with median values
head(titanic.combined$Age)
tail(titanic.combined$Age)

# Count of missing values:
table(is.na(titanic.combined$Age))
age.median <- median(titanic.combined$Age, na.rm = TRUE)
age.median
# replacing with this age.median:
titanic.combined[is.na(titanic.combined$Age), "Age"] <- age.median
table(is.na(titanic.combined$Age))

# Missing Values - Fare (as NA); replacing with median values
table(is.na(titanic.combined$Fare))
fare.median <- median(titanic.combined$Fare, na.rm = TRUE)
titanic.combined[is.na(titanic.combined$Fare), "Fare"] <- fare.median
table(is.na(titanic.combined$Fare))

# Summary on full set
summary(titanic.combined)
str(titanic.combined)

# Casting categorical variables as factors:
str(titanic.combined$Survived)
summary(titanic.combined$Survived)
#summary(titanic.train$Survived)
titanic.combined$Pclass <- as.factor(titanic.combined$Pclass)
titanic.combined$Sex <- as.factor(titanic.combined$Sex)
titanic.combined$Embarked <- as.factor(titanic.combined$Embarked)

# Splitting into train an test set again:
titanic.train <- titanic.combined[titanic.combined$isTrainSet == TRUE, ]
titanic.test <- titanic.combined[titanic.combined$isTrainSet == FALSE, ]

# Splitting the train set into train and test sets (80:20 split)
# to do:
set.seed(123456789)

train.nrow <- nrow(titanic.train) * 0.8
titanic.sample <- sample(1:nrow(titanic.train), train.nrow)
titanic.train2 <- titanic.train[titanic.sample,]
nrow(titanic.train2)
titanic.test2 <- titanic.train[-titanic.sample, ]
nrow(titanic.test2)

# Changing isTrainSet to FALSE for titanic.test2:
titanic.test2$isTrainSet <- FALSE

# Casting "survived" as a factor in the train sets:
titanic.train$Survived <- as.factor(titanic.train$Survived)
titanic.train2$Survived <- as.factor(titanic.train2$Survived)

# Check:
names(titanic.train2)
names(titanic.test2)
nrow(titanic.train2)
nrow(titanic.test2)

# randomForest
# randomForest(survived~. )

# Survived as a formula with variables: pclass, sex, age, sibsp, parch, fare, embarked\
survived.equaiton <- "Survived ~ Pclass + Sex + Age + SibSp + Parch + Fare + Embarked"
survived.formula <- as.formula(survived.equaiton)

# install randomForest from packages if not already
# install.packages("randomForest")
library(randomForest)

# mtry taken to be the square root of the number of features selected
# nodesize chosen to be a minimal of 10% of the total test rows
#titanic.model <- randomForest(formula = survived.formula, data = titanic.train2, 
#                ntree = 500, mtry = 3, nodesize = 0.01 * nrow(titanic.test))
titanic.model <- randomForest(formula = survived.formula, data = titanic.train2)

# features equation
features.equation <- "Pclass + Sex + Age + SibSp + Parch + Fare + Embarked"
survived <- predict(titanic.model, newdata = titanic.test2)

PassengerId <- 1:nrow(titanic.test2)
output.df <- as.data.frame(PassengerId)
output.df$Survived <- survived

# convert output.df to csv format
write.csv(output.df, file = "kaggle_submission.csv", row.names = FALSE)

# convert titanic.train2 and titanic.test2 to csv format
write.csv(titanic.train2, file = "titanic.train2.csv", row.names = FALSE)
write.csv(titanic.test2, file = "titanic.test2.csv", row.names = FALSE)

# convert titanic.train and titanic.test to csv format with the same names
write.csv(titanic.train, file = "titanic.train.csv", row.names = FALSE)
write.csv(titanic.test, file = "titanic.test.csv", row.names = FALSE)


#--------------
library (ROCR)


#y <- ... # logical array of positive / negative cases
#predictions <- ... # array of predictions
#titanic.test2$Survived


new = as.vector(survived)
new2 = as.numeric(new)
predictions5 <- as.data.frame(new2)

new3<- as.vector(titanic.test2$Survived)
y <- as.data.frame(new3)


pred <- prediction(predictions5, y)

# Recall-Precision curve             
RP.perf <- performance(pred, "prec", "rec");

plot (RP.perf);

# ROC curve
ROC.perf <- performance(pred, "tpr", "fpr");
plot (ROC.perf);

# ROC area under the curve
auc.tmp <- performance(pred,"auc");
auc <- as.numeric(auc.tmp@y.values)

#==============
library(caret)

a <- as.vector(survived)
b <- as.numeric(a)

p = as.vector(titanic.test2$Survived)

b <- survived

q <- (titanic.test2$Survived)


cm = confusionMatrix(titanic.test2$Survived, survived, negative=0)
cm
diagnosticErrors(cm)
cm = confusionMatrix(titanic.test2$Survived, survived)








