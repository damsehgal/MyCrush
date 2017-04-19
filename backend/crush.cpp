#include <bits/stdc++.h>
#include "inotify-cxx.h"

using namespace std;



std::vector<set<int> > graph(1);

void displayGraph()
{
	for (int i = 0; i < graph.size(); ++i)
	{
		for (auto it = graph[i].begin(); it != graph[i].end(); ++it)
		{
			cout << i  << " --> " << *it << "\n";
		}
	}
}

void addEdge(int start, int end)
{
	cerr  << start << " " << end << " " << graph.size() << " ";
	if(graph.size() <= max(start, end) + 1)
	{
		graph.resize(max(start, end) + 1);
	}
	graph[start].insert(end);
	displayGraph();
}

void removeEdge(int start, int end)
{
	graph[start].erase(graph[start].find(end));
	displayGraph();
}

void displayCrushList(int start)
{
	ofstream out1 ("./displayCrushList/displayCrushListOutput.txt");
	ofstream out2 ("./displayCrushListOutput.txt");
	for (auto it = graph[start].begin(); it != graph[start].end(); ++it)
	{
		out1 << *it << " ";
		out2 << *it << " ";
	}
	out2.close();
	out1.close();
}

void displayMatchList(int start)
{
	cerr << start;
	ofstream out1 ("./displayMatchList/displayMatchListOutput.txt");
	ofstream out2 ("./displayMatchListOutput.txt");
	for (auto it = graph[start].begin(); it != graph[start].end(); ++it)
	{
		if(graph[*it].find(start) != graph[*it].end())
		{
			out1 << *it << " ";
			out2 << *it << " ";
		}
	}
	out2.close();
	out1.close();
}

void watchDirectory() 
{
	string watch_dir = "./crushGraph";

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
					if (filename == "addEdge.txt" && mask_str == "IN_CLOSE_WRITE")
					{
						std::ifstream in;
						in.open("./addEdge.txt", std::ifstream::in);
						int start, end; 
						in >> start >> end;
						addEdge(start, end);
						in.close();
					}

					else if (filename == "removeEdge.txt" && mask_str == "IN_CLOSE_WRITE")
					{
						std::ifstream in;
						in.open("./removeEdge.txt", std::ifstream::in);
						int start, end; 
						in >> start >> end;
						removeEdge(start, end);
						in.close();
					}

					else if (filename == "displayCrushListInput.txt" && mask_str == "IN_CLOSE_WRITE")
					{
						std::ifstream in;
						in.open("./displayCrushListInput.txt", std::ifstream::in);
						int start; 
						in >> start;
						displayCrushList(start);
						in.close();
					}

					else if (filename == "displayMatchListInput.txt" && mask_str == "IN_CLOSE_WRITE")
					{
						std::ifstream in;
						in.open("./displayMatchListInput.txt", std::ifstream::in);
						int start; 
						in >> start;
						displayMatchList(start);
						in.close();
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
	watchDirectory();
	return 0;
}
