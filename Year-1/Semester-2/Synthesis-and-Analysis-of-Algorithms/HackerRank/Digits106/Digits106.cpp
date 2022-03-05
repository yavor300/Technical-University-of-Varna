#include <iostream>
#include <string>
#include <math.h>
using namespace std;

/*
* Напишете програма, която въвежда низ S, съставен от ненулеви десетични цифри,
* т.е знаци от множеството{'1', '2', ... , '9'}. Дължината на низа не надминава 10^6
* (= 1,000,000) знака и е по-голяма от 2. Програмата трябва да изведе сумата от всички
* цифри във всички низове, които се получават от S чрез премахване на точно един знак.
*/
int main()
{
    int T;
    do
    {
        cin >> T;
    } while (T >= 12); 
    cin.ignore();

    long answers[11];

    for (int i = 0; i < T; i++)
    {
        string S;
        do
        {
            getline(cin, S);
        } while (S.length() > pow(10, 6) || S.length() <= 2);     
        int length = S.length();

        long sum = 0;
        for (int j = 0; j < length; j++)
        { 
            sum += S[j] - '0';
        }

        answers[i] = sum * (length - 1);
    }

    for (int i = 0; i < T; i++)
    {
        cout << answers[i];
        if (i != T - 1) cout << endl;
    }

    return 0;
}
