#include <bits/stdc++.h>
#include "json.hpp"
using namespace std;
using json = nlohmann::json;
/*
	INPUT FORMAT


	argv[1]:
		true -> person has connected with fb
		false -> person has connected without fb
	argv[2]:
		emailID:
	argv[3]:
		name
	argv[4]:
		dateOfBirth
	argv[5]:
		password
	argv[6]:
		linkOfProfilePicture
		both small and large
	argv[7]:
		contactNumber
	argv[10]:
		if (argv[0] == 1)
		:listOfFriend
		in Json Format:
			string seperated by "," "FID1,FID2,FID3"
	argv[11];
		fID
	argv[8];
		gender
	argv[9];
		interestedIn
	TASKS TO BE DONE

	1) Give a New ID(our ID) to the customer in both cases and Map it	DONE
	2) Create a node in graph with given ID  DONE
	3) Make an entry for name in trie
	4) If (fb login): DONE
		foreach friend of user in the graph:
			add directed edge in these two
*/
void writeInGraphFile(const string ID, std::vector<int> listOfFriend)
{
	std::map<int, int> fbIDtoUserID;
	std::ifstream in;
	in.open ("/home/dam/AndroidStudioProjects/MyCrush/backend/fbIDtoUserID.txt", std::ifstream::in);
	for (int i = 0; i < listOfFriend.size(); ++i)
	{
		fbIDtoUserID[listOfFriend[i]] = -1;
	}
	string str;
	json j;
	while(in >> str)
	{
		j = json::parse(str.begin(), str.end());
		int currFbID = stoi(j["fbID"].get<string>());
		int currID = stoi(j["ID"].get<string>());

		if (fbIDtoUserID.find(currFbID) != fbIDtoUserID.end())
		{
			fbIDtoUserID[currFbID] = currID;
		}
	}
	in.close();
	// fbIDToUserID main sari list of friend ki apni walli ID h nd agar friend nhi h toh -1 ID h
	int currID = stoi(ID);
	ofstream out("/home/dam/AndroidStudioProjects/MyCrush/backend/graph/currentNodeInfo.txt");
	ofstream out2("/home/dam/AndroidStudioProjects/MyCrush/backend/currentNodeInfo.txt");
	out << currID << "\n";
	out2 << currID << "\n";
	for (auto i = fbIDtoUserID.begin(); i != fbIDtoUserID.end(); ++i)
	{
		if (i -> second != -1)
		{
			
			//addEdge between currID and i -> second
			out << i -> second << "\n";
			out2 << i -> second << "\n";
		}	
	}
	out2.close();
	out.close();

}
string generateID()
{
	int numberOfUsers;
	std::ifstream in;
	in.open ("/home/dam/AndroidStudioProjects/MyCrush/backend/numberOfUsers.txt", std::ifstream::in);
	in >> numberOfUsers;
	numberOfUsers++;
	in.close();
	ofstream out("/home/dam/AndroidStudioProjects/MyCrush/backend/numberOfUsers.txt");
	out << numberOfUsers;
	out.close();
	return to_string(numberOfUsers);
}

void writeUserData(string ID, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture, string contactNumber, string gender, string interestedIn)
{
	std::ofstream out;
	out.open("/home/dam/AndroidStudioProjects/MyCrush/backend/userData.txt", std::ios::app);
	json j;
	j["ID"] = ID;
	j["emailID"] = emailID;
	j["name"] = name;
	j["dateOfBirth"] = dateOfBirth;
	j["password"] = password;
	j["linkOfProfilePicture"] = linkOfProfilePicture;
 	j["contactNumber"] = contactNumber;
 	j["gender"] = gender;
 	j["interestedIn"] = interestedIn;
 	std::vector<int> crushList;
 	j["crushList"] = crushList;
 	out << j;
	out.close();
}
//4591760000232955
void appSignUp(string ID, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture, string contactNumber, string gender, string interestedIn)
{
	writeUserData(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, gender, interestedIn);
}
void fbSignUp(string ID, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture, string contactNumber, string fbID, vector<int> listOfFriend, string gender, string interestedIn)
{
	std::ofstream out;
	out.open("/home/dam/AndroidStudioProjects/MyCrush/backend/fbIDtoUserID.txt", std::ios::app);
	json j;
	j["ID"] = ID;
	j["fbID"] = fbID;
	j["listOfFriend"] = listOfFriend;
	out << j << "\n";	
	out.close();
	writeUserData(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, gender, interestedIn);

	/*
		fbID wali m dal gayi h
		todo apply filewatcher
		inotify
		userdata m dalni h
	*/
	writeInGraphFile(ID, listOfFriend);

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
	string contactNumber = string(argv[7]);
	string gender = string(argv[8]);
	string interestedIn = string(argv[9]);
	string ID = generateID();
	if (isFbSignUp == "true")
	{ 
		string fbID = string(argv[10]);
		std::vector<int> listOfFriend = stringToVector(string(argv[11]));
		fbSignUp(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, fbID, listOfFriend, gender, interestedIn);
		
	}	
	else 
	{
		appSignUp(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, gender, interestedIn);
	}
	return 0;
}