#include <bits/stdc++.h>
#include "json.hpp"
using namespace std;
using json = nlohmann::json;
/*
	INPUT FORMAT


	argv[0]:
		1 -> person has connected with fb
		0 -> person has connected without fb
	argv[1]:
		emailID:
	argv[2]:
		name
	argv[3]:
		dateOfBirth
	argv[4]:
		password
	argv[5]:
		linkOfProfilePicture
		both small and large
	argv[6]:
		if (argv[0] == 1)
		:listOfFriend
		in Json Format:
			string seperated by "id1,id2,id3"
	argv[7]:
		fid

	TASKS TO BE DONE

	1) Give a New ID(our ID) to the customer in both cases and Map it	
	2) Create a node in graph with given name  
	3) Make an entry for name in trie
	4) If (fb login):
		foreach friend of user in the graph:
			add directed edge in these two
	5)
*/

string generateID()
{
	int numberOfUsers;
	std::ifstream in;
	in.open ("numberOfUsers.txt", std::ifstream::in);
	in >> numberOfUsers;
	numberOfUsers++;
	in.close();
	ofstream out("numberOfUsers.txt");
	out << numberOfUsers;
	out.close();
	return to_string(numberOfUsers);
}

void writeUserData(string id, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture)
{
	std::ofstream out;
	out.open("userData.txt", std::ios::app);
	json j;
	j["id"] = id;
	j["emailID"] = emailID;
	j["name"] = name;
	j["dateOfBirth"] = dateOfBirth;
	j["password"] = password;
	j["linkOfProfilePicture"] = linkOfProfilePicture;
 	std::vector<int> crushList;
 	j["crushList"] = crushList;
 	out << j;
	out.close();
}
void appSignUp(string id, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture)
{
	writeUserData(id, emailID, name, dateOfBirth, password, linkOfProfilePicture);
}
void fbSignUp(string id, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture, vector<int> listOfFriend, string fbID)
{
	std::ofstream out;
	out.open("fbIDtoUserID.txt", std::ios::app);
	json j;
	j["id"] = id;
	j["fbID"] = fbID;
	j["listOfFriend"] = listOfFriend;
	out << j << "\n";	
	out.close();

	/*
		fbId wali m dal gayi h
		todo apply filewatcher
		inotify
		userdata m dalni h
	*/
	
}
std::vector<int> stringToVector(string listOfFriend)
{
	std::vector<std::string> tokens;
	std::size_t start = 0, end = 0;
	while ((end = listOfFriend.find(',', start)) != std::string::npos) 
	{
    	tokens.push_back(listOfFriend.substr(start, end - start));
    	start = end + 1;
  	}
  	tokens.push_back(listOfFriend.substr(start));
	std::vector<int> ret(tokens.size());
  	for (int i = 0; i < tokens.size(); ++i)
  	{
  		ret[i] = stoi(tokens[i]);
  	}

  	return ret;
}
int main(int argc, char const *argv[])
{
	string isFbSignUp = string(argv[1]);
	
	string emailID = string(argv[2]);
	string name = string(argv[3]);
	string dateOfBirth = string(argv[4]);
	string password = string(argv[5]);
	string linkOfProfilePicture = string(argv[6]);
	string id = generateID();
	if (isFbSignUp == "true")
	{ 
		std::vector<int> listOfFriend = stringToVector(string(argv[7]));
		string fbID = string(argv[8]);
		fbSignUp(id, emailID, name, dateOfBirth, password, linkOfProfilePicture, listOfFriend, fbID);
	}	
	else 
	{
		appSignUp(id, emailID, name, dateOfBirth, password, linkOfProfilePicture);
	}
	return 0;
}