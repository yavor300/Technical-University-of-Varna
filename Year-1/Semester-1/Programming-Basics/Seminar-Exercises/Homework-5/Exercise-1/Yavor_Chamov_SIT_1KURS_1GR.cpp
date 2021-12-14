#include <iostream>
#include <time.h>
#include <iomanip>
using namespace std;

/*
* Условие на задача: https://drive.google.com/file/d/1nlOBDjPe7Xjmx_FDZHk9wCM_twEnWRx-/view?usp=sharing
*/

const int ARRAY_MAX_LENGTH = 20;
const int MATRIX_COLUMNS_SIZE = 2;

const int FILL_IN_ARRAY_CHOICE = 1;
const int FIND_NUMBERS_LARGER_THAN_THEIR_NEIGHBOURS_CHOICE = 2;
const int SORT_NUMBERS_DESCENDING = 3;
const int PRINT_ARRAY_CHOICE = 4;
const int PRINT_MATRIX_CHOICE = 5;
const int EXIT_FROM_MENU_CHOICE = 6;

const int DIGITS_AFTER_DECIMAL_POINT = 2;

const double RANDOM_MIN_VALUE = -100;
const double RANDOM_MAX_VALUE = 100;

void fill_in_array(double numbers[], int& length, bool& are_numbers_initialized);
void print_array(double numbers[], int& length);
void find_numbers_larger_than_their_neighbours(double numbers[], int& length, double numbers_larger_than_their_neighbours[][MATRIX_COLUMNS_SIZE], int& numbers_larger_than_their_neighbours_length, bool& is_matrix_created);
void sort_numbers_ascending(double numbers[], int& length, double sorted_numbers[], bool& are_numbers_sorted);
void print_matrix(double numbers_larger_than_their_neighbours[][MATRIX_COLUMNS_SIZE], int& numbers_larger_than_their_neighbours_length);


void main()
{
    setlocale(LC_ALL, "BG");
    cout.setf(ios::fixed);
    cout.precision(DIGITS_AFTER_DECIMAL_POINT);

    double numbers[ARRAY_MAX_LENGTH];
    double numbers_larger_than_their_neighbours[ARRAY_MAX_LENGTH][MATRIX_COLUMNS_SIZE];
    double sorted_array[ARRAY_MAX_LENGTH];

    int initial_numbers_length, choice;
    int found_numbers_length = 0;

    bool are_numbers_initialized = false;
    bool are_numbers_sorted = false;
    bool is_matrix_created = false;

    do
    {
        printf("Въведете %d, за да добавите до %d случайни числа в масива.\nВъведете %d, за да намерите числата, които са по-големи от техните съседни, и да ги разпределите в нов двумерен масив.\nВъведете %d, за да сортирате числата във възходящ ред.\nВъведете %d, за да отпечатате числата на екрана.\nВъведете %d, за да отпечатате двумерния масив.\nВъведете %d, за да спрете програмата.\n",
            FILL_IN_ARRAY_CHOICE, ARRAY_MAX_LENGTH, FIND_NUMBERS_LARGER_THAN_THEIR_NEIGHBOURS_CHOICE, SORT_NUMBERS_DESCENDING, PRINT_ARRAY_CHOICE, PRINT_MATRIX_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", FILL_IN_ARRAY_CHOICE, EXIT_FROM_MENU_CHOICE);
            cin >> choice;
        } while (choice < FILL_IN_ARRAY_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case FILL_IN_ARRAY_CHOICE:
            printf("\nВъведете брой случайни елементи, които да се добавят към масива, в интервала [1 - %d]: ", ARRAY_MAX_LENGTH);  
            cin >> initial_numbers_length;
            while (initial_numbers_length <= 0 || initial_numbers_length > 20)
            {
                printf("Въведете число в интервала [1 - %d]: ", ARRAY_MAX_LENGTH);
                cin >> initial_numbers_length;
            }

            fill_in_array(numbers, initial_numbers_length, are_numbers_initialized);
            cout << "Числата са въведени успешно." << endl;
            break;

        case FIND_NUMBERS_LARGER_THAN_THEIR_NEIGHBOURS_CHOICE:
            cout << endl;

            if (!are_numbers_initialized)
            {
                cout << "Все още не сте инициализирали първоначалния масив със случайни числа. Въведете необходимата меню опция за тази операция." << endl;
                break;
            }

            find_numbers_larger_than_their_neighbours(numbers, initial_numbers_length, numbers_larger_than_their_neighbours, found_numbers_length, is_matrix_created);
            cout << "Вие успешно намерихте числата, които са по-големи от техните съседни, и ги разпределихте в нов двумерен масив." << endl;
            break;

        case SORT_NUMBERS_DESCENDING:
            cout << endl;

            if (!are_numbers_initialized)
            {
                cout << "Все още не сте инициализирали първоначалния масив със случайни числа. Въведете необходимата меню опция за тази операция." << endl;
                break;
            }

            sort_numbers_ascending(numbers, initial_numbers_length, sorted_array, are_numbers_sorted);
            cout << "Числата са сортирани успешно." << endl;
            break;

        case PRINT_ARRAY_CHOICE:
            cout << endl;

            if (!are_numbers_initialized)
            {
                cout << "Все още не сте инициализирали първоначалния масив със случайни числа. Въведете необходимата меню опция за тази операция." << endl;
                break;
            }

            cout << "Първоначален масив: ";
            print_array(numbers, initial_numbers_length);
            cout << endl;

            if (are_numbers_sorted)
            {
                cout << "Сортиран масив: ";
                print_array(sorted_array, initial_numbers_length);
            }
            else cout << "За да изведете сортирани числата, въведете необходимата меню опция.";

            cout << endl;
            break;

        case PRINT_MATRIX_CHOICE:
            cout << endl;
            if (!is_matrix_created) cout << "Вие не сте открили числата, които са по-големи от техните съседни, и не сте ги разпределили в нов двумерен масив. Въведете необходимата меню опция за тази операция.";
            else
            {
                cout << "Двумерен масив:" << endl;
                print_matrix(numbers_larger_than_their_neighbours, found_numbers_length);
            }
            break;
        }
        cout << endl;
    } while (choice != EXIT_FROM_MENU_CHOICE);
}

void fill_in_array(double numbers[], int& length, bool& are_numbers_initialized)
{
	srand(time(NULL));
	double min = RANDOM_MIN_VALUE;
	double max = RANDOM_MAX_VALUE;
	for (int i = 0; i < length; i++)
	{
		numbers[i] = min + (((double)rand() / RAND_MAX) * (max - min));
	}
    are_numbers_initialized = true;
}

void print_array(double numbers[], int& length)
{
    for (int i = 0; i < length; i++) cout << numbers[i] << " ";
}

void find_numbers_larger_than_their_neighbours(double numbers[], int& length, double numbers_larger_than_their_neighbours[][MATRIX_COLUMNS_SIZE], int& numbers_larger_than_their_neighbours_length, bool& is_matrix_created)
{
    numbers_larger_than_their_neighbours_length = 0;
    for (int i = 0; i < length; i++)
    {
        if (i - 1 < 0 || i + 1 >= length) continue;

        if (numbers[i] > numbers[i - 1] && numbers[i] > numbers[i + 1])
        {
            numbers_larger_than_their_neighbours[numbers_larger_than_their_neighbours_length][0] = i;
            numbers_larger_than_their_neighbours[numbers_larger_than_their_neighbours_length][1] = numbers[i];
            numbers_larger_than_their_neighbours_length++;
        }
    }
    is_matrix_created = true;
}

void sort_numbers_ascending(double numbers[], int& length, double sorted_numbers[], bool& are_numbers_sorted)
{
    for (int i = 0; i < length; i++) sorted_numbers[i] = numbers[i];

    for (int i = 0; i < length - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < length - 1 - i; j++)
        {
            if (sorted_numbers[j] > sorted_numbers[j + 1])
            {
                double temp = sorted_numbers[j];
                sorted_numbers[j] = sorted_numbers[j + 1];
                sorted_numbers[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }

    are_numbers_sorted = true;
}

void print_matrix(double numbers_larger_than_their_neighbours[][MATRIX_COLUMNS_SIZE], int& numbers_larger_than_their_neighbours_length)
{
    cout << setw(8) << setiosflags(ios::left) << "Индекс" << setw(10) << setiosflags(ios::left) << "Стойност" << endl;
    for (int i = 0; i < numbers_larger_than_their_neighbours_length; i++)
    {
        for (int j = 0; j < MATRIX_COLUMNS_SIZE; j++)
        {
            if (j == 0) cout << setw(8) << setprecision(0) << numbers_larger_than_their_neighbours[i][j] << " ";
            else cout << setw(10) << setprecision(2) << numbers_larger_than_their_neighbours[i][j];
        }
        cout << endl;
    }
}
