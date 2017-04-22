#include <bits/stdc++.h>
#include "inotify-cxx.h"

using namespace std;

//argv[1]	
//	ID
//  prefix
// interestedIn
//searchInGraph directory par addNodeInGraph write karega
//userReturnedFromGraph par addNodeInGraph write karega
//currentSearchInGraph ko addNodeInGraph read karega
void watchDirectory() {
    string watch_dir = "../searchInGraph";

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
                    if (filename == "usersReturnedFromGraph.txt" && mask_str == "IN_CLOSE_WRITE")
                    {
                        std::ifstream in;
                        in.open("../usersReturnedFromGraph.txt", std::ifstream::in);
                        string str;
                        while (getline(in, str))
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
}

int main(int argc, char const *argv[]) {
    string ID = string(argv[1]);
    string prefix = string(argv[2]);
    string interestedIn = string(argv[3]);
    std::ofstream out1("../graph/currentSearchInGraph.txt");
    std::ofstream out2("../currentSearchInGraph.txt");
    out1 << ID << " " << prefix << " " << interestedIn << " ";
    out2 << ID << " " << prefix << " " << interestedIn << " ";
    out2.close();
    out1.close();

    return 0;
}