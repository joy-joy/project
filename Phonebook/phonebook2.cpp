//*************Phone Book program***************************************
//
// Input file: PB.txt 
//
// The program expects the input file to be present in the same 
// directory as the program source code is in.
//
// Limitation: Program fails when a non-integer value is inputed by user
// when asked for 
//
//      "Enter number of records you want to add: " 
//
// from choice 1 in the menu. (caution to be exercised and integer 
// values input only)
//
// Program fails and exits for values under "menu" other than 1-8 
// entered  by user as well. Caution is to be exercised here as well.

#include <stdlib.h>
#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <iomanip>

using namespace std;
struct Record
{
    string contactName, 
	homePhoneNo, 
	cellPhoneNo, 
	contactAddress, 	
	contactEmail;
};

void readFile(vector <Record>& myPhoneBookVector);

void writeToFile(vector <Record> myPhoneBookVector);

void printRecordToDisplay(Record myRecord);

void printAllRecords(vector <Record> myPhoneBookVector);

int searchEntryByName(vector <Record> myPhoneBookVector, string contactName);

void addRecordToPhoneBook(vector <Record>& myPhoneBookVector);

void deleteContactEntry(vector <Record>& myPhoneBookVector, string contactNameToDelete);

void editContactEntry(vector <Record>& myPhoneBookVector, string contactName);

bool sortBit(Record a, Record b);

void menu();

void phoneBook(vector <Record>& myPhoneBookVector);

int main()
{
	system ("clear");

	vector<Record> myPhoneBookVector;

	readFile(myPhoneBookVector);	

	phoneBook(myPhoneBookVector);

	return 0;
}

void readFile(vector <Record>& myPhoneBookVector)
{
	ifstream inFile;
	inFile.open("PB.txt");

	if(inFile.is_open())
	{
		string garbage;
		int numberOfRecords;

		inFile >> numberOfRecords;
		getline(inFile,garbage);

		for (int i=0;i<numberOfRecords;i++)
		{
			Record myRecord;
			getline(inFile, myRecord.contactName);
			getline(inFile, myRecord.homePhoneNo);
			getline(inFile, myRecord.cellPhoneNo);
			getline(inFile, myRecord.contactAddress);
			getline(inFile, myRecord.contactEmail);
			myPhoneBookVector.push_back(myRecord);
		}
	}

	inFile.close();
}

void writeToFile(vector <Record> myPhoneBookVector)
{
	int  totalRecordsToWrite = myPhoneBookVector.size();

	ofstream outFile;
	outFile.open("PB.txt");

	if(outFile.is_open())
	{
	    outFile << totalRecordsToWrite << endl;
		for(int i=0; i < totalRecordsToWrite; i++)
		{
			outFile << myPhoneBookVector[i].contactName << endl;
			outFile << myPhoneBookVector[i].homePhoneNo << endl;
			outFile << myPhoneBookVector[i].cellPhoneNo << endl;
			outFile << myPhoneBookVector[i].contactAddress << endl;
			outFile << myPhoneBookVector[i].contactEmail << endl;
        }
	outFile.close();
	}

	else cout << "Unable to Open File" << endl;
}

void printRecordToDisplay(Record myrecord)
{
		cout << "\nContact Name: " << myrecord.contactName << endl;
		cout << "Home Phone Number: " << myrecord.homePhoneNo << endl;
		cout << "Cell Phone Number: " << myrecord.cellPhoneNo << endl;
		cout << "Contact Address: " << myrecord.contactAddress << endl;
		cout << "Contact Email: " << myrecord.contactEmail << endl << endl;
}

void printAllRecords(vector <Record> myPhoneBookVector)
{
	int  totalRecordsToWrite = myPhoneBookVector.size();
	cout << "\nTotal Number of Contact entries: " << totalRecordsToWrite << endl;

	for(int i = 0; i < totalRecordsToWrite; i++)
	{
		printRecordToDisplay(myPhoneBookVector[i]);
	}
}

int searchEntryByName(vector <Record> myPhoneBookVector, string contactName)
{
	int  totalRecordsToWrite = myPhoneBookVector.size();

	for(int i = 0; i < totalRecordsToWrite; i++)
	{
		if(myPhoneBookVector[i].contactName == contactName)
			return i;
	}

	return -1;
}

void addRecordToPhoneBook(vector <Record>& myPhoneBookVector)
{
	string garbage;
	int numberOfRecords;
	
	cout << endl << "Enter number of records you want to add: ";
	cin >> numberOfRecords;
	getline(cin,garbage);
	
	for (int i=0;i<numberOfRecords;i++)
	{
		Record myRecord;		
		cout << endl << "Enter Contact Name: ";
		getline(cin, myRecord.contactName);				
		cout << endl << "Enter Contact Home Phone Number: ";
		getline(cin, myRecord.homePhoneNo);		
		cout << endl << "Enter Contact Cell Phone Number: ";
		getline(cin, myRecord.cellPhoneNo);		
		cout << endl << "Enter Contact Address: ";
		getline(cin, myRecord.contactAddress);		
		cout << endl << "Enter Contact Email Id: ";
		getline(cin, myRecord.contactEmail);		

		myPhoneBookVector.push_back(myRecord);
	}
	cout << endl << "Contact(s) Added" << endl << endl;
	
}

void deleteContactEntry(vector <Record>& myPhoneBookVector, string contactNameToDelete)
{
	int index = searchEntryByName(myPhoneBookVector, contactNameToDelete);

	if(index == -1)
		cout << endl << "Contact Name not found in Phone Book." << endl << endl;

	else
	{
		myPhoneBookVector.erase(myPhoneBookVector.begin()+index);
		cout << endl << endl << "Contact Entry deleted successfully" << endl;
	}
}

void editContactEntry(vector <Record>& myPhoneBookVector, string contactName)
{
    string garbage;
    int index = searchEntryByName(myPhoneBookVector, contactName);

	if(index == -1)
		cout << endl << "No Record exists with the Contact Name entered." << endl << endl;

	else
    {
        cout << endl << "Enter New Contact name: ";
        getline(cin, myPhoneBookVector[index].contactName);
        cout << endl << "Enter New Home Phone Number: ";
		getline(cin, myPhoneBookVector[index].homePhoneNo);
		cout << endl << "Enter New Cell Phone Number: ";
		getline(cin, myPhoneBookVector[index].cellPhoneNo);
		cout << endl << "Enter New Contact Address: ";
		getline (cin, myPhoneBookVector[index].contactAddress);
		cout << endl << "Enter New Contact Email Id: ";
		getline(cin, myPhoneBookVector[index].contactEmail);
        cout << endl << "Record edit successfull." << endl << endl;
    }
}

bool sortBit(Record a, Record b)
{
	return (a.contactName < b.contactName);
}

void menu()
{
	cout << "\t******* Phone Book ******* " << endl << endl
		<< "\t 1) Add new Record " << endl
		<< "\t 2) Display all Records" << endl
		<< "\t 3) Edit/change a Record" << endl 
		<< "\t 4) Search a Record" << endl
		<< "\t 5) Delete a Record" << endl
		<< "\t 6) Sort all Records" << endl
		<< "\t 7) Save changes to file" << endl
		<< "\t 8) Exit" << endl;
}

void phoneBook(vector <Record>& myPhoneBookVector)
{
	menu();
	string garbage;
	bool execute = false;
	
	while(!execute)
	{
		int choice;
		cout << endl << "Enter your choice from Menu: ";
		cin >> choice;

		if(choice == 1)
		addRecordToPhoneBook(myPhoneBookVector);

		else if (choice == 2)
		printAllRecords(myPhoneBookVector);

		else if (choice == 3)
		{
			string contactName;
	        getline(cin,garbage);
			cout << endl << "Enter Existing Contact Name: ";
			getline(cin,contactName);
			editContactEntry(myPhoneBookVector,contactName);
		}

		else if (choice == 4)
		{
			string contactName;
			getline(cin, garbage);
			cout << endl << "Enter Contact Name to search for: ";
			getline(cin, contactName);

			int index = searchEntryByName(myPhoneBookVector, contactName);
			if(index == -1)
				cout << endl << "No matching entry found in phone book." << endl << endl;
			
			else	
			{				
				cout << endl;
				printRecordToDisplay(myPhoneBookVector[index]);

			}
		}

		else if (choice == 5)
		{
			string contactNameToDelete;
			getline(cin, garbage);
			cout << endl << "Enter Name: ";
			getline(cin, contactNameToDelete);
			deleteContactEntry(myPhoneBookVector, contactNameToDelete);

		}

		else if(choice==6)
	    {
	        sort(myPhoneBookVector.begin(),myPhoneBookVector.end(),sortBit);
			cout << endl << "Phone book sorted successfully." << endl << endl;
	    }
		
		else if(choice == 7)
		{
			writeToFile(myPhoneBookVector);
			cout << endl << "Phone Book saved successfully." << endl << endl;
		}

		else if(choice == 8) 
		{
			cout << endl << "Exiting..." << endl << endl;			

			break;
		}

		else 
		{
			cout << endl << "Invalid choice" << endl;
			cout << endl << "Quitting..." << endl << endl;
			break;
		}
	}
}
