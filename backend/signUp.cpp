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
	argv[12]:
		if (argv[0] == 1)
		:listOfFriend
		in Json Format:
			string seperated by "," "FID1,FID2,FID3"
	argv[13];
		fID
	argv[8];
		gender
	argv[9];
		interestedIn
	argv[10] =  salt
	argv[11] = isNumberVisible
	TASKS TO BE DONE

	1) Give a New ID(our ID) to the customer in both cases and Map it	DONE
	2) Create a node in graph with given ID  DONE
	3) Make an entry for name in trie
	4) If (fb login): DONE
		foreach friend of user in the graph:
			add directed edge in these two
*/
void writeInGraphFile(const string ID, std::vector<long long int> listOfFriend) {
	std::map<long long int, long long int> fbIDtoUserID;
	std::ifstream in;
	in.open("../fbIDtoUserID.txt", std::ifstream::in);
	for (long long int i = 0; i < listOfFriend.size(); ++i)
	{
		fbIDtoUserID[listOfFriend[i]] = -1;
	}
	string str;
	json j;
	while (in >> str)
	{
		j = json::parse(str.begin(), str.end());
		long long int currFbID;
		long long int currID;
		try{

			currFbID = stoll(j["fbID"].get<string>());
			currID = stoll(j["ID"].get<string>()); 
		}
		catch (std::exception &ex)
		{
		}

		

		if (fbIDtoUserID.find(currFbID) != fbIDtoUserID.end())
		{
			fbIDtoUserID[currFbID] = currID;
		}
	}
	in.close();
	// fbIDToUserID main sari list of friend ki apni walli ID h nd agar friend nhi h toh -1 ID h
	
	long long int currID;
	try {
		currID = stoll(ID);
	} 
	catch (exception &e)
	{
	}
	ofstream out("../graph/currentNodeInfo.txt");
	ofstream out2("../currentNodeInfo.txt");
	out << currID << "\n";

	out2 << currID << "\n";
	for (auto i = fbIDtoUserID.begin(); i != fbIDtoUserID.end(); ++i)
	{
		if (i->second != -1)
		{

			//addEdge between currID and i -> second
			out << i->second << "\n";
			out2 << i->second << "\n";
		}
	}
	out2.close();
	out.close();

}

string generateID() {
	long long int numberOfUsers;
	std::ifstream in;
	in.open("../numberOfUsers.txt", std::ifstream::in);
	in >> numberOfUsers;
	numberOfUsers++;
	in.close();
	ofstream out("../numberOfUsers.txt");
	out << numberOfUsers;
	out.close();
	return to_string(numberOfUsers);
}

void
writeUserData(string ID, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture,
			  string contactNumber, string gender, string interestedIn, string salt, string isNumberVisible) {
	std::ofstream out;
	out.open("../userData.txt", std::ios::app);
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
	j["salt"] = salt;
	j["isNumberVisible"] = isNumberVisible;
	std::vector<int> crushList;
	j["crushList"] = crushList;
	out << j << "\n";
	out.close();
	ofstream out3("../graph/currentPersonInfo.txt");
	ofstream out2("../currentPersonInfo.txt");
	out2 << interestedIn << "\n" << name << "\n" << gender << "\n";
	out3 << interestedIn << "\n" << name << "\n" << gender << "\n";
	out2.close();
	out3.close();
	ofstream out4("../ternarySearchInsert/insert.txt");
	ofstream out5("../tstInsert.txt");
	out5 << name << " " << id << " " << linkOfProfilePicture;
	out4 << name << " " << id << " " << linkOfProfilePicture;
	out5.close();
	out4.close();
}

//4591760000232955
void appSignUp(string ID, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture,
			   string contactNumber, string gender, string interestedIn, string salt, string isNumberVisible) {
	writeUserData(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, gender, interestedIn,
				  salt, isNumberVisible);
}

void fbSignUp(string ID, string emailID, string name, string dateOfBirth, string password, string linkOfProfilePicture,
			  string contactNumber, string fbID, vector<long long int> listOfFriend, string gender, string interestedIn,
			  string salt, string isNumberVisible) {
	std::ofstream out;
	out.open("../fbIDtoUserID.txt", std::ios::app);
	json j;
	j["ID"] = ID;
	j["fbID"] = fbID;
	j["listOfFriend"] = listOfFriend;
	out << j << "\n";
	out.close();
	writeUserData(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, gender, interestedIn,
				  salt, isNumberVisible);

	/*
		fbID wali m dal gayi h
		todo apply filewatcher
		inotify
		userData m dalni h
	*/
	writeInGraphFile(ID, listOfFriend);

}

std::vector<long long int> stringToVector(string listOfFriend) {
	std::vector<std::string> tokens;
	std::size_t start = 0, end = 0;
	while ((end = listOfFriend.find(',', start)) != std::string::npos)
	{
		tokens.push_back(listOfFriend.substr(start, end - start));
		start = end + 1;
	}
	tokens.push_back(listOfFriend.substr(start));
	std::vector<long long int> ret(tokens.size());
	for (long long int i = 0; i < tokens.size(); ++i)
	{
		try{
			ret[i] = stoll(tokens[i]);
		}
		catch (std::exception &ex)
		{
		}
	}

	return ret;
}

bool userAlreadyExist(string userEmail)
{
	std::ifstream in;
	in.open("../userData.txt", std::ifstream::in);
	string str, email;
	json j;
	while (in >> str)
	{
		j = json::parse(str.begin(), str.end());
		email = j["emailID"].get<string>();
		if (email == userEmail)
		{
			in.close();
			return true;
		}
	}
	in.close();
	return false;

}

int main(int argc, char const *argv[]) {

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
	string salt = string(argv[10]);
	string isNumberVisible = string(argv[11]);
	
	if (userAlreadyExist(emailID))
	{
		cout << "User Already Exist";
	}

	else
	{
		if (isFbSignUp == "true")
		{
			string fbID = string(argv[12]);
			std::vector<long long int> listOfFriend = stringToVector(string(argv[13]));
			fbSignUp(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, fbID, listOfFriend,
					 gender, interestedIn, salt, isNumberVisible);

		}
		else
		{
			appSignUp(ID, emailID, name, dateOfBirth, password, linkOfProfilePicture, contactNumber, gender, interestedIn,
					  salt, isNumberVisible);
		}
		cout << "New user Created";
	}
	return 0;
}