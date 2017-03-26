/*
    Simple inotify-cxx example which will output
    all events for /tmp. Exit via CTRL+C.

    Author: Thomas Jarosch <thomas.jarosch@intra2net.com>
    Licensed under the same licenses as inotify-cxx.
	Steps for compilation -> g++ -c inotify-cxx.cpp
	ar rvs inotify-cxx.a inotify-cxx.o
	g++ addNodesInGraph.cpp inotify-cxx.o

*/

#include <exception>
#include <iostream>

#include "inotify-cxx.h"

using namespace std;

int main(void)
{
    string watch_dir = "./graph";

    try 
    {
        Inotify notify;

        InotifyWatch watch(watch_dir, IN_ALL_EVENTS);
        notify.Add(watch);

        cout << "Watching directory " << watch_dir << endl << endl;
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

                    cout << "[watch " << watch_dir << "] ";
                    cout << "event mask: \"" << mask_str << "\", ";
                    cout << "filename: \"" << filename << "\"" << endl;
                }

                count--;
            }
        }
    } 
    catch (InotifyException &e) 
    {
        cerr << "Inotify exception occured: " << e.GetMessage() << endl;
    } 
    catch (exception &e) 
    {
        cerr << "STL exception occured: " << e.what() << endl;
    } 
    catch (...) 
    {
        cerr << "unknown exception occured" << endl;
    }

    return 0;
}