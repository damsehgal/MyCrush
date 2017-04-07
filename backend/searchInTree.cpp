#include <bits/stdc++.h>
#include "TST.hh"
using namespace std;

/*
	This file would only get called if
	
	1) User is logged in without FbID

	2) If user has fbId but current search returns less than 5 objects

	3) User wants to know list of all users?
			get successors from
*/
/*
	functions
	getSuccessors(int number_of_successors)
	find(string name, int id)
	getSuccessor(node*, int id)
	insert(string name, int id)

	class node
	{
		string currentName
		set <person>
		char current
		bool isTerminal()
		{
			return set.size != 0;
		}
		void insert(person p)
		{
			set.insert(p);
		}
	};
	class person
	{
		int id
		string lopp
	};
*/
class Person
{

public:
	int id;
	string lopp;
	Person(){}
	Person(int id, string lopp)
	{
		this -> id = id;
		this -> lopp = lopp;
	}
	bool operator<(const Person & rhs) const 
	{
   		return id < rhs.id; 
	}
};

class Node
{

public:	
	Node* left;
	Node* right;
	Node* center;
	char curr;
	string currentName;
	set <Person> personWithSameNames;
	Node()
	{
		left = NULL;
		right = NULL;
		center = NULL;
	}

	Node(char curr, string previousName)
	{
		this -> curr = curr;
		currentName = previousName + curr;
		left = NULL;
		right = NULL;
		center = NULL;
	}

	bool isTerminal()
	{
		return personWithSameNames.size() != 0;
	}
};

void insert(Node* &root, Person p, string name, int &it)
{

	if (root == NULL)
	{
		root = new Node(name[it++], "");
	}

	if (name[it] < root -> curr)
	{
		insert(root -> left, p, name, ++it);
	}

	else if(name[it] > root -> curr)
	{
		insert(root -> right, p, name, ++it);
	}

	else 
	{
		if(it < name.length())
		{
			insert(root -> center, p, name, ++it);
		}

		else 
		{
			root -> personWithSameNames.insert(p);
		}
	}
}

void traverse(Node* &root, string &buffer)
{
	if(root != NULL)
	{
		traverse(root -> left, buffer);
		buffer += root -> curr; 
		
		if (root -> isTerminal())
		{
			buffer += '\0';
			for (int i = 0; buffer[i]; ++i)
			{
				cout << buffer[i];
			}
			cout << "\n";
		}

		traverse(root -> center, buffer);

		traverse(root -> right, buffer);
	}

}



int main(int argc, char const *argv[])
{

	Node* maleTree;
	string name = "bhaskar";
	Person p(12, "abc.vom");
	int it = 0;
	insert(maleTree, p, name, it);
	p.id = 123;
	p.lopp = "def.com";
	name = "dam";
	it = 0;
	insert (maleTree, p, name, it);
	string buffer = "";
	traverse(maleTree, buffer);
	return 0;
}