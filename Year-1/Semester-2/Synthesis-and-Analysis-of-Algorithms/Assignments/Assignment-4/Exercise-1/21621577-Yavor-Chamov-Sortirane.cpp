#include <iostream>
#include <fstream>
#include <string>
using namespace std;

const int NUMBERS_COUNT = 100;
int not_sorted[NUMBERS_COUNT];
int sorted[NUMBERS_COUNT];

bool create_file();
bool read_file(int not_sorted[]);
void merge(int a[], int na, int b[], int nb, int c[]);
void merge_sort(int sorted[], int n);
void shakesort(int a[], int n);
void shellsort(int a[], int i);

int main()
{
    if (!create_file()) cout << "Error saving " << NUMBERS_COUNT << " numbers in a file.\n\n";
    else
    {
        cout << "Successfully saved " << NUMBERS_COUNT << " numbers in a file.\n\n";

        if (!read_file(not_sorted)) cout << "Error reading numbers file.\n\n";
        else
        {   
            cout << "Successfully stored all numbers in an array.\n\n";
            for (int i = 0; i < NUMBERS_COUNT; i++)
            {
                sorted[i] = not_sorted[i];
            }
            merge_sort(sorted, NUMBERS_COUNT);
            cout << "MERGE SORT: ";
            for (int i = 0; i < NUMBERS_COUNT; i++)
            {
                cout << sorted[i] << " ";
            }
            cout << "\n\n";
            for (int i = 0; i < NUMBERS_COUNT; i++)
            {
                sorted[i] = not_sorted[i];
            }
            shakesort(sorted, NUMBERS_COUNT);
            cout << "SHAKE SORT: ";
            for (int i = 0; i < NUMBERS_COUNT; i++)
            {
                cout << sorted[i] << " ";
            }
            cout << "\n\n";
            for (int i = 0; i < NUMBERS_COUNT; i++)
            {
                sorted[i] = not_sorted[i];
            }
            shellsort(sorted, 0);
            cout << "SHELL SORT: ";
            for (int i = 0; i < NUMBERS_COUNT; i++)
            {
                cout << sorted[i] << " ";
            }
        }
    }
}

bool create_file()
{
    fstream numbers;
    numbers.open("numbers.dat", ios::out);

    if (numbers.fail())
    {
        cout << "Error in creating the numbers file!";
        return false;
    }

    srand(time(NULL));
    int min = 1;
    int max = 100;
    for (int i = 0; i < NUMBERS_COUNT; i++)
    {
        int random_number = (min + (rand() % (max - min + 1)));
        numbers << random_number << endl;
    }

    numbers.close();
    return true;
}

bool read_file(int not_sorted[])
{
    fstream numbers;
    numbers.open("numbers.dat", ios::in);

    if (numbers.fail())
    {
        cout << "Error reading the numbers file!";
        return false;
    }

    int index = 0;
    while (!numbers.eof())
    {
        string number;
        getline(numbers, number);
        if (number == "") continue;
        not_sorted[index] = stoi(number);
        index++;
    }

    return true;
}

void merge(int a[], int na, int b[], int nb, int c[])
{
    int ia = 0, ib = 0, ic = 0;
    while (ia < na && ib < nb)
        c[ic++] = ((a[ia] < b[ib]) ? a[ia++] : b[ib++]);
    while (ia < na) c[ic++] = a[ia++];
    while (ib < nb) c[ic++] = b[ib++];
}

void merge_sort(int sorted[], int n)
{
    if (n < 2) return;
    int nleft = n / 2, nright = n - nleft;
    merge_sort(sorted, nleft);
    merge_sort(sorted + nleft, nright);
    int* p = new int[n];
    merge(sorted, nleft, sorted + nleft, nright, p);
    for (int i = 0; i < n; i++)
        sorted[i] = p[i];
    delete[]p;
}

void shakesort(int a[], int n)
{
    unsigned k = n, R = n - 1;
    unsigned L = 1, j, rab;
    do
    {
        for (j = R; j >= L; j--)
        {
            if (a[j - 1] > a[j])
            {
                rab = a[j];
                a[j] = a[j - 1];
                a[j - 1] = rab;
                k = j;
            }
        }
        L = k + 1;
        for (j=L; j<=R; j++)
        {
            if (a[j - 1] > a[j])
            {
                rab = a[j];
                a[j] = a[j - 1];
                a[j - 1] = rab;
                k = j;
            }
        }
        R = k - 1;
    } while (L<=R);
}

void shellsort(int a[], int i)
{
    int rab, j;
    j = i - 1;
    while ((j >= 0) && (a[j] > a[j + 1]))
    {
        rab = a[j];
        a[j] = a[j + 1];
        a[j + 1] = rab;
        j--;
    }
    if (i < NUMBERS_COUNT - 1)
        shellsort(a, i + 1);
}
