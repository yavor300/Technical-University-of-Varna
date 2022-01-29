#include <iostream>
using namespace std;

double calculate_book_price(int pages_count, double sheet_price, double plaka_price);
double calculate_black_and_white_order(int books_count, int pages_count);
double calculate_colorful_order(int books_count, int pages_count);

void main()
{
    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);

    cout << "Total price for:" << "\n\t"
        << "1000 colorful books with 32 pages each: BGN"
        << calculate_colorful_order(1000, 32)
        << "\n\t"
        << "2000 black and white books with 40 pages each: BGN"
        << calculate_black_and_white_order(2000, 40)
        << "\n\t"
        << "400 black and white books with 160 pages each: BGN"
        << calculate_black_and_white_order(400, 160)
        << "\n*Prices include VAT.";
}

double ave(double n1, double n2)
{
    return ((n1 + n2) / 2);
}

double ave(double n1, double n2, double n3)
{
    return ((n1 + n2 + n3) / 3);
}

double calculate_book_price(int pages_count, double sheet_price, double plaka_price)
{
    return (pages_count * plaka_price + ((pages_count / 2) * sheet_price)) + 2;
}

double calculate_black_and_white_order(int books_count, int pages_count)
{
    return (books_count * calculate_book_price(pages_count, 0.01, 7.00)) * 1.2;
}

double calculate_colorful_order(int books_count, int pages_count)
{
    return (books_count * calculate_book_price(pages_count, 0.08, 28.00)) * 1.2;
}
