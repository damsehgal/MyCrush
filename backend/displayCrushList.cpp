#include <bits/stdc++.h>
#include "inotify-cxx.h"

using namespace std;

void sendInput(string start)
{
	ofstream out("../crushGraph/displayCrushListInput.txt");
	ofstream out2("../displayCrushListInput.txt");
	out2 << start;
	out << start;
	out2.close();
	out.close();
}

void receiveOutput()
{
	std::ifstream in;
    in.open("../displayCrushListOutput.txt", std::ifstream::in);
	string str;
    while (in >> str)
    {
    	cout << str << "\n";
    }
    in.close();
}

void watchDirectory() 
{
	string watch_dir = "../displayCrushList";

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
					if (filename == "displayCrushListOutput.txt" && mask_str == "IN_CLOSE_WRITE")
					{
						receiveOutput();
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

int main(int argc, char const *argv[])
{
	sendInput(string(argv[1])); // bande ki id
	watchDirectory();
	return 0;
}