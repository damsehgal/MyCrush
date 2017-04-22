#include <bits/stdc++.h>
#include "json.hpp"

using namespace std;
using json = nlohmann::json;

/*
	emailID:argv[1]
	return salt
*/
int main(int argc, char const *argv[]) {
    string emailID = string(argv[1]);
    string salt;
    std::ifstream in;
    in.open("../userData.txt", std::ifstream::in);
    string str;
    json j;
    while (in >> str)
    {
        j = json::parse(str.begin(), str.end());
        string currentEmail = j["emailID"].get<string>();
        if (currentEmail == emailID)
        {
            salt = j["salt"].get<string>();
            cout << salt;
            return 0;

        }

    }
    cout << "No user found";

    return 0;

}
