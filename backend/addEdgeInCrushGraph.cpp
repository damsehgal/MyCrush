#include <bits/stdc++.h>

using namespace std;

/*
	argv[0]:
		start
	argv[1]:
		end
*/

int main(int argc, char const *argv[]) {
    ofstream out1("../addEdge.txt");
    ofstream out2("../crushGraph/addEdge.txt");
    out1 << argv[1] << " " << argv[2];
    out2 << argv[1] << " " << argv[2];
    out1.close();
    out2.close();
    return 0;
}
 
