#include <iostream>
using namespace std;

const int ARRAY_LENGTH = 5;

void main()
{
    int i, score[ARRAY_LENGTH], max;
    
    cout << "Enter " << ARRAY_LENGTH << " scores:\n";
    cin >> score[0];
    max = score[0];

    for (i = 1; i < ARRAY_LENGTH; i++)
    {
        cin >> score[i];
        if (score[i] > max) max = score[i];
    }

    cout << "The highest score is " << max << endl
        << "The scores and their\n"
        << "differences from the highest are:\n";

    for (i = 0; i < ARRAY_LENGTH; i++)
    {
        cout << score[i] << " off by " << (max - score[i]) << endl;
    }
}
