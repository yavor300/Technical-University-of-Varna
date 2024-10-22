#include <iostream>
#include <string>
#include <cctype>

using namespace std;

enum TSymbolType {
    intconst,
    text,
    semicolon,
    period,
    quotas,
    othersy
};

TSymbolType Symbol; // Global symbol tracker
string Spelling;    // Holds the current string token
int Constant;       // Holds the current integer token
string input;       // Input text to parse
size_t currentCharIndex = 0; // Tracks the position in the input

// Function to get the next character from the input
char GetNextChar() {
    if (currentCharIndex < input.size()) {
        return input[currentCharIndex++];
    }
    return '\0'; // End of input
}

// Function to skip any non-valid characters and get the next valid symbol
void GetNextSymbol() {
    Spelling.clear();
    Constant = 0;
    char Char = GetNextChar();

    // Skip whitespace and invalid characters
    while (Char == ' ' || Char < 32 || Char > 126) {
        Char = GetNextChar();
    }

    if (isalpha(Char)) {  // Identifier (text)
        while (isalpha(Char) || isdigit(Char)) {
            Spelling += toupper(Char);
            Char = GetNextChar();
        }
        currentCharIndex--; // Step back one character for the next read
        Symbol = text;
        cout << "Parsed string: " << Spelling << endl;
    } else if (isdigit(Char)) {  // Integer constant
        while (isdigit(Char)) {
            Constant = Constant * 10 + (Char - '0');
            Char = GetNextChar();
        }
        currentCharIndex--; // Step back one character for the next read
        Symbol = intconst;
        cout << "Parsed integer constant: " << Constant << endl;
    } else {  // One-character symbols
        switch (Char) {
            case '.': Symbol = period; cout << "Parsed period\n"; break;
            case ';': Symbol = semicolon; cout << "Parsed semicolon\n"; break;
            case '"': Symbol = quotas; cout << "Parsed quotas\n"; break;
            default:
                Symbol = othersy;
                cout << "Error: unexpected symbol '" << Char << "' (ASCII code: " << int(Char) << ")" << endl;
                break;
        }
    }
}

// Field function: Handle an integer, a quoted string, or special characters
void Field() {
    if (Symbol == intconst) {
        GetNextSymbol(); // Move to the next symbol after integer constant
    } else if (Symbol == text) {
        GetNextSymbol(); // Move after parsed text
    } else if (Symbol == quotas) {
        GetNextSymbol();    // Skip opening quote
        if (Symbol == text) {   // Expect text inside the quotes
            GetNextSymbol();    // Move to next symbol after parsed text
        }
        if (Symbol == quotas) {  // Skip closing quote
            GetNextSymbol();
        } else {
            cout << "Error: expected closing quotes.\n";
        }
    } else {
        cout << "Error: field expects intconst, string, or quotes." << endl;
    }
}

// Record function: Handle a sequence of fields, ending with a period
void Record() {
    Field();
    while (Symbol == semicolon) {
        GetNextSymbol();
        Field();
    }
    // Expect the record to end with a period
    if (Symbol == period) {
        // Do not call GetNextSymbol() after the last period
        if (currentCharIndex < input.size()) {
            GetNextSymbol();  // Move after the period only if more input exists
        }
    } else {
        cout << "Error: expected period at the end of the record.\n";
    }
}

// DataFile function: Handle the entire data file structure
void DataFile() {
    while (currentCharIndex < input.size()) {
        Record();
        // Break the loop if no more characters are available after the period
        if (currentCharIndex >= input.size()) {
            break;  // End parsing when we reach the end of input
        }
    }
    printf("Finish\n");
}

// Main function demonstrating parsing
int main() {
    // Example input with multiple records
    input = "1234;631;\"Hello\".5678;\"course1\".\"end\".";

    // Start parsing by getting the first symbol
    GetNextSymbol();

    // Begin parsing according to the DataFile rule
    DataFile();

		getchar();

    return 0; // Program will now exit immediately after parsing
}
