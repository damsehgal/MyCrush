#include <bits/stdc++.h>
#include "json.hpp"

using namespace std;
using json = nlohmann::json;

/*
	emailID // argv[1]
	password (salt encrypted) // argv[2]

*/
int main(int argc, char const *argv[]) {
    string emailID = string(argv[1]);
    string password = string(argv[2]);
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
            if (password == j["password"].get<string>())
            {
                cout << j["ID"].get<string>();

            }
            else
            {
                cout << "incorrect password";
            }
            return 0;
        }
    }
    cout << "user does not exist";
    return 0;
}