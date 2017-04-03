/*
	Simple inotify-cxx example which will output
	all events for /tmp. Exit via CTRL+C.

	Author: Thomas Jarosch <thomas.jarosch@intra2net.com>
	Licensed under the same licenses as inotify-cxx.
	Steps for compilation -> g++ -c inotify-cxx.cpp
	ar rvs inotify-cxx.a inotify-cxx.o
	g++ addNodesInGraph.cpp inotify-cxx.o

*/

#include <bits/stdc++.h>
#include "inotify-cxx.h"

using namespace std;

std::vector <std::vector<int> > graph(1);

// graph is adjacency list 
// 1 based indexing
class Node {
public:    
    string interestedIn;
    string name;
    string gender;
};

std::vector <Node> persons(1);

void displayGraph() {

    for (int i = 1; i < graph.size(); ++i)
    {
        if (graph[i].size() > 0)
        {
            cout << "[" << i << "]";
            for (int j = 0; j < graph[i].size(); ++j)
            {
                cout << graph[i][j] << " ";
            }
            cout << "\n";
        }
    }
}

void addNodeInGraph() {
    // read file
    std::ifstream in;
    in.open("./currentNodeInfo.txt", std::ifstream::in);
    int start, end;
    in >> start;
    graph.resize(start + 1);
    while (in >> end)
    {
        graph[end].push_back(start);
        graph[start].push_back(end);
    }
    in.close();
    displayGraph();

}

void addPersonGender() {
    std::ifstream in;
    in.open("./currentPersonInfo.txt", std::ifstream::in);
    Node currentPerson;
    in >> currentPerson.interestedIn >> currentPerson.name >> currentPerson.gender;
    persons.push_back(currentPerson);
    in.close();
}

void watchDirectory() {
    string watch_dir = "./graph";

    try
    {
        Inotify notify;

        InotifyWatch watch(watch_dir, IN_ALL_EVENTS);
        notify.Add(watch);


        for (;;)
        {
            notify.WaitForEvents();
            size_t count = notify.GetEventCount();
            while (count > 0)
            {
                InotifyEvent event;
                bool got_event = notify.GetEvent(&event);

                if (got_event)
                {
                    string mask_str;
                    event.DumpTypes(mask_str);

                    string filename = event.GetName();
                    if (filename == "currentNodeInfo.txt" && mask_str == "IN_CLOSE_WRITE")
                    {
                        addNodeInGraph();
                    }
                    else if (filename == "currentPersonInfo.txt" && mask_str == "IN_CLOSE_WRITE")
                    {
                        addPersonGender();
                    }
                }

                count--;
            }
        }
    }
    catch(InotifyException & e)
    {
        cerr << "Inotify exception occured: " << e.GetMessage() << endl;
    }
    catch(exception & e)
    {
        cerr << "STL exception occured: " << e.what() << endl;
    }
    catch(...)
    {
        cerr << "unknown exception occured" << endl;
    }
}

int main(int argc, char const *argv[]) {
    watchDirectory();
    return 0;
}
