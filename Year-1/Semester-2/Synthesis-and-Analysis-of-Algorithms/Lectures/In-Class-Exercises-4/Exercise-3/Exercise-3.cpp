#include <iostream>
#include <stack>
#include <string>
using namespace std;

/*
* Задача за пресмятане на математически израз съставен от
* едноцифрени числа със Stack.
*/

int get_precedence_of_operators(char c)
{
    if (c == '^') return 3;
    else if (c == '/' || c == '*') return 2;
    else if (c == '+' || c == '-') return 1;
    else return -1;
}

string covert_infix_to_postifx(string expression)
{
    stack<char> st;
    string result;

    for (int i = 0; i < expression.length(); i++)
    {
        char symbol = expression[i];
        if (symbol >= '0' && symbol <= '9') result += symbol;
        else if (symbol == '(') st.push(symbol);
        else if (symbol == ')')
        {
            while (st.top() != '(')
            {
                result += st.top();
                st.pop();
            }
            st.pop();
        }
        else
        {
            while (!st.empty() && get_precedence_of_operators(expression[i]) <= get_precedence_of_operators(st.top()))
            {
                result += st.top();
                st.pop();
            }
            st.push(symbol);
        }
    }

    while (!st.empty())
    {
        result += st.top();
        st.pop();
    }

    return result;
}

int calculate_postfix(string postfix)
{
    stack<int> st;
    int length = postfix.length();

    for (int i = 0; i < length; i++)
    {
        if (postfix[i] >= '0' && postfix[i] <= '9') st.push(postfix[i] - '0');
        else
        {
            int a = st.top();
            st.pop();
            int b = st.top();
            st.pop();

            switch (postfix[i])
            {
            case '+':
                st.push(b + a);
                break;
            case '-':
                st.push(b - a);
                break;
            case '*':
                st.push(b * a);
                break;
            case '/':
                st.push(b / a);
                break;
            }
        }
    }
    return st.top();
}

int main()
{
    string expression, postfix;
    getline(cin, expression);
    postfix = covert_infix_to_postifx(expression);
    int result = calculate_postfix(postfix);
    cout << result;
}
