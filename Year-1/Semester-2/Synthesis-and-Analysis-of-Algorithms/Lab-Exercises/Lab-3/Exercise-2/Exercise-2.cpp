#include <iostream>
using namespace std;

/*
* Задача 2
*
* Задача за Ханойските кули
*/
void hanoj(char start_tower, char destination_tower, char help_tower, int disk_count)
{
    if (disk_count == 1) cout << "Move the disk 1 from " << start_tower << " to " << destination_tower << ".\n";
    else
    {
        hanoj(start_tower, help_tower, destination_tower, disk_count - 1);
        cout << "Move the disk " << disk_count << " from " << start_tower << " to " << destination_tower << ".\n";
        hanoj(help_tower, destination_tower, start_tower, disk_count - 1);
    }
}

void main()
{
    char start_tower = 'A';
    char help_tower = 'B';
    char destination_tower = 'C';
    int disk_count;
    cout << "Enter disk count: ";
    cin >> disk_count;
    hanoj(start_tower, destination_tower, help_tower, disk_count);
}
