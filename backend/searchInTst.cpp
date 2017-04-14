#include <bits/stdc++.h>
#include "inotify-cxx.h"
using namespace std;

/*
	argv[1]
		search all: true? false
	argv[2]:
		prefix
	argv[3]:
		if (!argv[1]) 
			numberOfUsers

*/

void watchDirectory(string inputFileName) {
    string watch_dir = "../outputSearchInTernarySearchTree";

    try
    {
        Inotify notify;

        InotifyWatch watch(watch_dir, IN_ALL_EVENTS);
        notify.Add(watch);


        cerr << "watching directory"
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
                    if (filename == inputFileName && mask_str == "IN_CLOSE_WRITE")
                    {
                     	std::ifstream in;
                     	in.open(("../" + inputFileName).c_str(), std::ifstream::in);
                     	string str;
                     	while(getline(in, str))
                     	{
                     		cout << str << "\n";
                     	}
                     	in.close();
                     	exit(0);
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

void searchAll(string name)
{
	ofstream out1("../inputSearchAllInTST.txt");
	ofstream out2("../ternarySearchInsert/inputSearchAllInTST.txt");
	out1 << name;
	out2 << name;
	out1.close();
	out2.close();
	
	watchDirectory("outputSearchAllInTST.txt");
}


void searchInTST(string prefix, string numberOfUsers)
{
	ofstream out1("../inputSearchInTST.txt");
	ofstream out2("../ternarySearchInsert/inputSearchInTST.txt");
	out1 << numberOfUsers << " " << prefix << " ";
	out2 << numberOfUsers << " " << prefix << " ";
	out1.close();
	out2.close();
	watchDirectory("outputSearchInTST.txt");
}

int main(int argc, char const *argv[])
{
	bool serachInAll = (string(argv[1]) == "true");
	if(!serachInAll)
	{
		searchInTST(argv[2], argv[3]);
	}
	else
	{
		searchAll(string(argv[2]));

	}
	return 0;
}