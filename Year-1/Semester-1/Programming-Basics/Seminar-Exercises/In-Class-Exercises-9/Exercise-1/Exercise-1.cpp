#include <iostream>
#include <fstream>
#include <string>
using namespace std;

struct Book
{
    string name;
    double price;
};

const int NUMBER_OF_BOOKS = 5;

void input_books(Book books[]);
void save_books_to_file(Book books[]);
void save_information_from_file_to_array(string file_name, Book books[]);

int main()
{
    
    Book books[NUMBER_OF_BOOKS];
    input_books(books);
    save_books_to_file(books);
}

void input_books(Book books[])
{
    string book_name;
    double price;

    for (int i = 0; i < NUMBER_OF_BOOKS; i++)
    {
        getline(cin, book_name);
        cin >> price;
        cin.ignore();
        Book book = { book_name, price };
        books[i] = book;
    }
}

void save_books_to_file(Book books[])
{
    fstream book_file;
    book_file.open("book_data.txt", ios::out);
    if (book_file.is_open())
    {
        for (int i = 0; i < NUMBER_OF_BOOKS; i++)
        {
            book_file << books[i].name << " " << books[i].price << endl;
        }
    }
    book_file.close();
}

void save_information_from_file_to_array(string file_name, Book books[])
{
    fstream book_file;
    book_file.open(file_name, ios::in);
    if (book_file.is_open())
    {
        string book_name;
        double price;

        for (int i = 0; i < NUMBER_OF_BOOKS; i++)
        {
            getline(cin, book_name);
            cin >> price;
            cin.ignore();
            Book book = { book_name, price };
            books[i] = book;
        }
    }
}