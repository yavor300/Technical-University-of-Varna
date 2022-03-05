#include <iostream>
#include <math.h>
using namespace std;

int main()
{
    long long C, last;
    long long x;
    long long y;
    long long n;
    cin >> C;

    for (long long i = 0; i < C; i++)
    {
        char binary[64];
        long powers_of_two[64];
        long long binary_digits = 0;
        cin >> x >> y >> n;
        long long zeroes[64];
        long long zeroes_counter = 0;
        long y_for_binary_conversion = y;

        for (long long j = 0; y_for_binary_conversion > 0; j++)
        {
            binary[j] = (y_for_binary_conversion % 2 == 1 ? '1' : '0');
            if (binary[j] == '0') zeroes[zeroes_counter++] = j;
            y_for_binary_conversion /= 2;
            binary_digits++;
        }

        long long powers_of_two_counter = 0;
        for (long long j = 0; j < binary_digits; j++)
        {
            if (binary[j] == '1') powers_of_two[powers_of_two_counter++] = pow(2, j);
        }
        if (powers_of_two_counter == 0) return 0;


        long long buffer[64];
        buffer[0] = x % n;
        long long buffer_index = 1;

        for (long long k = 2; k <= powers_of_two[powers_of_two_counter - 1]; k *= 2)
        {
            buffer[buffer_index] = ((buffer[buffer_index - 1] * buffer[buffer_index - 1]) % n);
            buffer_index++;
        }

        long long result = 1;
        for (long long j = 0; j < buffer_index; j++)
        {
            bool to_miss = false;
            for (long long l = 0; l < zeroes_counter; l++)
            {
                if (zeroes[l] == j)
                {
                    to_miss = true;
                    break;
                }
            }

            if (!to_miss)
            {
                result *= buffer[j];
            }
        }
        cout << result % n;
    }

    cin >> last;
    return 0;
}
